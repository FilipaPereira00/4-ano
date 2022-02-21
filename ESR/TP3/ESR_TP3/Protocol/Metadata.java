package Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Metadata {
    String filename;
    long fileSize;
    long packetNumber;

    public void toBytes(DataOutputStream dataOut) throws IOException {
        dataOut.writeUTF(filename);
        dataOut.writeLong(fileSize);
        dataOut.writeLong(packetNumber);
    } 

    public Metadata(DataInputStream dataIn) throws IOException {
        filename = dataIn.readUTF();
        fileSize = dataIn.readLong();
        packetNumber = dataIn.readLong();
    }
}
