package Streamer;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Protocol.Interface;
import Protocol.Packet;
import Protocol.PacketProtocol;

public class StreamerData {
    private String nodeId; // Nome do nodo
    private PacketProtocol protocol;
    private final String filePath = "Streamer/";
    // converter isto num map cuja key é o nó que se conecta (S1,c2,etc..) e o value é um map 
    private Map<String, List<Interface>> overlay;

    public StreamerData(String[] args) throws IOException {
        nodeId = args[0];
        this.overlay = new HashMap<>();
        parse(filePath + args[1]);
        this.protocol = new PacketProtocol(overlay.get(nodeId));
        
        
    }

    public void start() throws IOException {
        protocol.startRcv();
        while(true) {
            Packet p = protocol.receive();
            handleRcvMessages(p.messageType, p);
        }
    }


    // Resposta a cada pacote recebido
    public void handleRcvMessages(int messageType, Packet p) throws IOException {
        switch(messageType) {
            case 0: // Inicio de conexao
                startConnection(p);
                generateRoute(p.source);
                break;
            case 1: // Mensagem de routing
                break;
            case 2: // Envio de dados multimedia
                break;
            default:
                break;
        }
    }

    // Resposta a mensagem de inicio de conexao
    // Comunicar ao nodo que se conecta os seus vizinhos
    public void startConnection(Packet p) throws IOException {
        List<Interface> neighList = overlay.get(p.source);
        final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        final DataOutputStream dataOut = new DataOutputStream(byteOut);
        dataOut.writeInt(neighList.size());
        for (Interface i : neighList) {
            i.toBytes(dataOut);
        }
        dataOut.close();
        byteOut.flush();
        Packet pSend = new Packet(0, nodeId, p.source, byteOut.toByteArray());
        System.out.println(pSend.data.length );
        protocol.send(pSend, p.prevNodeIp);
    }

    // Calcula o caminho mais rapido do nodo ao servidor 
    public void generateRoute(String idNode) throws IOException {
        Packet p = new Packet(1, this.nodeId, idNode, 0);
        protocol.sendBroadcast(p);
    }

    // Parse do ficheiro de configuracao
    public void parse(String filename) throws IOException {

        // this method of try with resorces automatically closes buffered reader
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line = reader.readLine();

            while (line != null) {

                String[] parts = line.split(" *: *");
                String[] ips = parts[1].split(" *; *");

                List<Interface> interfaceList = new ArrayList<>();

                // loop through all neighbours of a node
                for (String s : ips) {
                    String[] data = s.split(" *, *");
                    InetAddress ina = InetAddress.getByName(data[1]);
                    Interface i = new Interface(data[0], ina);
                    interfaceList.add(i);
                }

                this.overlay.put(parts[0], interfaceList);

                // read next line
                line = reader.readLine();
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}