import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PTPMaster extends Thread {
    private ServerSocket serverSocket;

    public PTPMaster(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while(true) {
                Socket connectionSocket = serverSocket.accept();
                new PTPMasterSender(connectionSocket).start();
                new PTPMasterReceiver(connectionSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
