import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 1) {
            int port = Integer.valueOf(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();

        }
    }
}
