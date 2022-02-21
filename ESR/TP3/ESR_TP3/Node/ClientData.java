package Node;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import Protocol.Packet;

public class ClientData extends Node {
    public ClientData(String[] args) throws UnknownHostException, SocketException {
        super(args);
    }

    // Resposta a cada pacote recebido
    public void handleRcvMessages(int messageType, Packet entry) throws IOException {
        switch(messageType) {
            case 0: // Inicio de conexao
                startConnection(entry);
                break;
            case 1: // Mensagem de routing
                setNewRoute(entry);
                break;
            case 3: // Envio de dados multimedia
                break;
            default:
                break;
        }
    }   

    // Resposta a mensagem de routing
    // Ativar a rota
    public void setNewRoute(Packet p) throws IOException {
        Packet rt = new Packet(2,nodeId, bootstrapper.idNode);
        protocol.sendBroadcast(rt);
    }
}
