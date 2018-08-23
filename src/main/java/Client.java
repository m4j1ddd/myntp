import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Client extends Thread {
    ServerSocket serverSocket;

    public Client(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while(true) {
                Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromOther = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String msg = inFromOther.readLine();
                System.out.println("Message received: " + msg);
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String ip, int port, String msg) throws IOException {
        Socket socket = new Socket(ip, port);
        DataOutputStream outToOther = new DataOutputStream(socket.getOutputStream());
        long send_time = TimeCounter.getInstance().getTime();
        outToOther.writeBytes(msg + '\n');
        socket.close();
        MonitorMain.send_msg_time(send_time, msg);
    }
}
