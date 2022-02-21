package Protocol;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PacketProtocol {
    private DatagramSocket sendSocket; // socket para enviar pacotes
    private DatagramSocket rcvSocket; // socket para receber pacotes
    private ArrayDeque<Packet> messages; // lista de pacotes recebidos e o ip do ultimo nodo que os enviou
    private ReentrantLock lock; // lock para acesso a fila 'messages'
    private Condition cond; // condition para acesso a fila 'messages'
    private Map<String,Interface> neighbours; // Map de vizinhos de um nó



    public PacketProtocol() throws SocketException {
        this.sendSocket = new DatagramSocket();
        this.rcvSocket = new DatagramSocket(9090);
        this.neighbours = null;
        this.messages = new ArrayDeque<>();
        this.lock = new ReentrantLock();
        this.cond = lock.newCondition();
    }

    // Construtor usado pelo streamer que ja sabe os seus vizinhos
    public PacketProtocol(List<Interface> neighbours) throws SocketException {
        this.sendSocket = new DatagramSocket();
        this.rcvSocket = new DatagramSocket(9090);
        this.messages = new ArrayDeque<>();
        this.lock = new ReentrantLock();
        this.cond = lock.newCondition();
        updateNeighbours(neighbours);
    }

    // Store received messages in a queue
    public void startRcv() {
        (new Thread() {
            @Override
            public void run() {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                
                while (true) {
                    try {
                        rcvSocket.receive(packet);
                        byte[] packetData = packet.getData();
                        Packet p = new Packet(packet.getAddress(),packetData);
                        System.out.println("Message received from " + p.source);
                        try {
                            lock.lock();
                            messages.push(p);
                        }
                        finally {
                            cond.signalAll();
                            lock.unlock();
                        }
                    } 
                    catch (IOException ex) {
                        rcvSocket.close();
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    public Packet receive() {
        lock.lock();
        while (messages.isEmpty()) {
            try {
                cond.await();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        lock.lock();
        Packet p = messages.poll();
        lock.unlock();
        return p;
    }

    public void send(Packet p, String dest) throws IOException {
        byte[] data = p.packetToBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length,neighbours.get(dest).ipAdress, 9090);
        sendSocket.send(dp);
    }

    public void send(Packet p, InetAddress dest) throws IOException {
        byte[] data = p.packetToBytes();
        DatagramPacket dp = new DatagramPacket(data,data.length,dest, 9090);
        sendSocket.send(dp);
    }

    public void sendBroadcast(Packet p) throws IOException {
        byte[] data = p.packetToBytes();
        for(Interface i : neighbours.values()) {
            DatagramPacket dp = new DatagramPacket(data,data.length,i.ipAdress, 9090);
            sendSocket.send(dp);
        }
    }

    public void startNodeConnection(String nodeId, InetAddress bootstrapper) throws IOException {
        Packet p = new Packet(0, nodeId, "");
        send(p, bootstrapper);
    }


    public void updateNeighbours(byte[] data) throws IOException {
        neighbours = new HashMap<>();
        final ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        final DataInputStream dataIn = new DataInputStream(byteIn);
        int length = dataIn.readInt();
        for (int i = 0; i < length; i++) {
            Interface interf = new Interface(dataIn);
            neighbours.put(interf.idNode, interf);
        }
        dataIn.close();
        byteIn.close();
        System.out.println(neighbours.toString());
    }

    public void updateNeighbours(List<Interface> list) {
        neighbours = new HashMap<>();
        for (Interface i : list) {
            neighbours.put(i.idNode, i);
        }
    }

    public boolean isNeighbour(String idNode) {
        return neighbours.containsKey(idNode);
    }

    public List<InetAddress> getRouterNeighbours() {
        List<InetAddress> routers = new ArrayList<>();
        for(String node : neighbours.keySet()) {
            if (node.charAt(0) == 'n')
                routers.add(neighbours.get(node).ipAdress);
        }
        return routers;
    }
}
