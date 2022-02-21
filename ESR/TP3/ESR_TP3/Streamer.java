import java.io.IOException;
import java.net.*;

import Streamer.StreamerData;

// Nó que vai enviar dados para os clientes
public class Streamer {
    private static StreamerData streamer;
    public static void main(String[] args) throws IOException {
        try {
            streamer = new StreamerData(args);
            streamer.start();
            //streamer uses protocol to send packets and ip's
            

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

}