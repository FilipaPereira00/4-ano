package Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Interface {
    public String idNode;
    public InetAddress ipAdress;
    public boolean active;

    public Interface(DataInputStream dataIn) throws UnknownHostException, IOException{
        fromBytes(dataIn);
    }

    public Interface(String id,InetAddress ip) {
        this.idNode = id;
        this.ipAdress = ip; 
        active = true;
    }

    public void toBytes(DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(idNode);
        dataOut.writeUTF(ipAdress.getHostAddress());
    }

    public void fromBytes(DataInputStream dataIn) throws UnknownHostException, IOException {
        idNode = dataIn.readUTF();
        ipAdress = InetAddress.getByName(dataIn.readUTF());
    }
}