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
            Date start_date, ts1_date, tr2_date;
            long ts1, tr2;

            ServerSocket welcomeSocket = new ServerSocket(port);

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedReader inFromClient =
                        new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();
                start_date = new Date();
                if(clientSentence.equals("start")) {
                    System.out.println("Started!");
                    ts1_date = new Date();
                    System.out.println("TS1 established!");
                    outToClient.writeBytes("MSG1" + '\n');

                    clientSentence = inFromClient.readLine();
                    tr2_date = new Date();
                    if(clientSentence.equals("MSG2")) {
                        System.out.println("TR2 established!");

                        ts1 = ts1_date.getTime() - start_date.getTime();
                        tr2 = tr2_date.getTime() - start_date.getTime();
                        outToClient.writeBytes("TS1" + '\n' + ts1 + '\n' + "TR2" + '\n' + tr2 + '\n');
                    }
                }
            }
        }
    }
}
