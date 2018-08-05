import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 1) {
            int port = Integer.valueOf(args[0]);
            ServerSocket serverSocket = new ServerSocket(port);
            Socket slaveSocket = serverSocket.accept();
            DataOutputStream outToMaster = new DataOutputStream(slaveSocket.getOutputStream());
            BufferedReader inFromMaster = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()));
            String masterSentence;

            masterSentence = inFromMaster.readLine();
            Date t2_date = new Date();
            if(masterSentence.equals("Sync")) {
                System.out.println("T2 established");
                masterSentence = inFromMaster.readLine();
                if(masterSentence.equals("Follow_Up")) {
                    Date t3_date = new Date();
                    System.out.println("T3 established!");
                    outToMaster.writeBytes("Delay_Req" + '\n');

                    masterSentence = inFromMaster.readLine();
                    if(masterSentence.equals("Delay_Resp")) {

                    }
                }
            }
        }
    }
}
