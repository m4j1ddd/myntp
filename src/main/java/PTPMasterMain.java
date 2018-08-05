import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class PTPMasterMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 2) {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);
            Socket masterSocket = new Socket(ip, port);
            DataOutputStream outToSlave = new DataOutputStream(masterSocket.getOutputStream());
            BufferedReader inFromSlave = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()));
            String slaveSentence;

            Date t1_date = new Date();
            System.out.println("T1 established!");
            outToSlave.writeBytes("Sync" + '\n');
            outToSlave.writeBytes("Follow_Up" + '\n');
            slaveSentence = inFromSlave.readLine();
            Date t4_date = new Date();
            if(slaveSentence.equals("Delay_Req")) {
                System.out.println("T4 established!");
                outToSlave.writeBytes("Delay_Resp" + '\n');
            }
        }
    }
}
