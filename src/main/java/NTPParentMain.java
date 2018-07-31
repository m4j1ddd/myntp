import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class NTPParentMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 1) {
            int port = Integer.valueOf(args[0]);
            String clientSentence;
            String capitalizedSentence;
            ServerSocket welcomeSocket = new ServerSocket(port);

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();
                if(clientSentence.equals("start")) {
                    System.out.println("Start received!");

                    Date ts1 = new Date();
                    outToClient.writeBytes("Ts1" + '\n');
                }
            }
        }
    }
}
