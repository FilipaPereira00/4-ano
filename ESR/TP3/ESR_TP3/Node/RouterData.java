package Node;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Protocol.Packet;
import Protocol.TableEntry;

public class RouterData extends Node {
    private Map<String, Map<InetAddress,TableEntry>> routingTable;

    public RouterData(String[] args) throws UnknownHostException, SocketException{
        super(args);
        this.routingTable = new HashMap<>();
    }

    // Resposta a cada pacote recebido
    public void handleRcvMessages(int messageType, Packet entry) throws IOException {
        switch(messageType) {
            case 0: // Inicio de conexao
                startConnection(entry);
                break;
            case 1: // Mensagem de routing
                updateRoutingTable(entry);
                spreadRoutingPacket(entry);
                break;
            case 2: // Envio de dados multimedia
                break;
            default:
                break;
        }
    }   

    // Atualizar tabela de routing
    public void updateRoutingTable(Packet p) throws UnknownHostException {
        Map<InetAddress,TableEntry> entry = routingTable.get(p.destination);
        if (entry == null) {
            entry = new HashMap<>();
            TableEntry e = new TableEntry(p.destination, p.prevNodeIp, p.nHops+1);
            entry.put(e.nextHop,e);
        }
        else {
            TableEntry e = entry.get(p.prevNodeIp);
            if (e == null) {
                e = new TableEntry(p.destination, p.prevNodeIp, p.nHops+1);
                entry.put(e.nextHop,e);
            }
            else {
                if (p.nHops + 1 < e.cost) {
                    e.cost = p.nHops+1;
                }
            }
        }
    }

    public int getLessCost(String dest, List<InetAddress> routers) {
        int minCost = 100000;
        for(InetAddress r : routers) {
            int cost = routingTable.get(dest).get(r).cost;
            if(cost<minCost)
                minCost = cost;
        }
        return minCost;
    }
    
    public void spreadRoutingPacket(Packet p) throws IOException {
        List<InetAddress> routers = super.protocol.getRouterNeighbours();
        Set<InetAddress> entryRouters = routingTable.get(p.destination).keySet();
        if (super.protocol.isNeighbour(p.destination)) { // O destino é um dos seus vizinhos
            boolean receivedAll = true;
            
            for(InetAddress r : routers) {
                if (!entryRouters.contains(r)) {
                    receivedAll = false;
                    break;
                }
            }
            if (receivedAll) { // Se ja recebeu mensagem de routing de todos os vizinhos
                int minCost = getLessCost(p.destination, routers);
                Packet rp = new Packet(1, nodeId, p.destination , minCost);
                protocol.send(rp, p.destination); // Envia mensagem diretamente ao destino
            }
        }
        else { // O destino nao é um dos seus vizinhos
            for(InetAddress r : routers) {
                if (!entryRouters.contains(r)) {
                    int minCost = getLessCost(p.destination, routers);
                    Packet rp = new Packet(1, nodeId, p.destination, minCost);
                    protocol.send(rp, r); // Envia aos routers vizinhos
                }
            }
        }
    }
}
