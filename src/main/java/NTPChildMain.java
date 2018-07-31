import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class NTPChildMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 2) {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);
            String serverSentence;
            Date start_date, tr1_date, ts2_date = null;
            long ts1 = 0, tr1, ts2, tr2 = 0;

            Socket clientSocket = new Socket(ip, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            start_date = new Date();
            System.out.println("Started!");
            outToServer.writeBytes("start" + '\n');

            serverSentence = inFromServer.readLine();
            tr1_date = new Date();
            if(serverSentence.equals("MSG1")) {
                System.out.println("TR1 established!");

                ts2_date = new Date();
                System.out.println("TS2 established!");
                outToServer.writeBytes("MSG2" + '\n');

                serverSentence = inFromServer.readLine();
                if(serverSentence.equals("TS1")) {
                    ts1 = Long.valueOf(inFromServer.readLine());
                    System.out.println("TS1 recieved");
                    serverSentence = inFromServer.readLine();
                    if(serverSentence.equals("TR2")) {
                        tr2 = Long.valueOf(inFromServer.readLine());
                        System.out.println("TR2 recieved");
                    }
                }
            }
            ts2 = ts2_date.getTime() - start_date.getTime();
            tr1 = tr1_date.getTime() - start_date.getTime();
            System.out.println("TS1 = " + ts1 + ", TR1 = " + tr1 + ", TS2 = " + ts2 + ", TR2 = " + tr2);
            clientSocket.close();
        }
    }
}
