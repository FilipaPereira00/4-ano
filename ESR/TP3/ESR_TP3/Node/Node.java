package Node;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import Protocol.Interface;
import Protocol.Packet;
import Protocol.PacketProtocol;

public abstract class Node {
    public Interface bootstrapper;
    public String nodeId;
    public PacketProtocol protocol;
    

    public Node(String[] args) throws UnknownHostException, SocketException {
        this.nodeId = args[0];
        this.bootstrapper = new Interface(args[1],InetAddress.getByName(args[2]));
        this.protocol = new PacketProtocol();
        
    }

    public void start() throws IOException {
        protocol.startNodeConnection(nodeId, this.bootstrapper.ipAdress);
        protocol.startRcv();
        while(true) {
            Packet entry = protocol.receive();
            handleRcvMessages(entry.messageType, entry);
        }
    }

    public abstract void handleRcvMessages(int messageType, Packet entry) throws IOException;

    // Resposta a mensagem de inicio de conexao
    public void startConnection(Packet p) throws IOException {
        protocol.updateNeighbours(p.data);
    }

    

}