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
            String modifiedSentence;

            Socket clientSocket = new Socket(ip, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Date start_date = new Date();
            System.out.println("Start at:" + start_date.toString());
            System.out.println("Start at:" + start_date.toString());
            outToServer.writeBytes("start" + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            clientSocket.close();
        }
    }
}
