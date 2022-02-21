import java.io.IOException;

//import Node.ClientData;

// Nó que vai receber os dados enviados pelo streamer
public class Client {

    // GUI variables:



    //private static ClientData client;
    public static void main(String[] args) {
        /*try {
            client = new ClientData(args); 
            client.start();  
        }
        catch (IOException e){
            e.printStackTrace();
        }*/
        ClientGUI gui = new ClientGUI();
        
    }

}