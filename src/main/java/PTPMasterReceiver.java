import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class PTPMasterReceiver extends Thread {
    private Socket connectionSocket;

    public PTPMasterReceiver(Socket socket) {
        connectionSocket = socket;
    }

    public void run() {
        Date t4_date;
        try {
            while(connectionSocket.isConnected()) {
                DataOutputStream outToSlave = new DataOutputStream(connectionSocket.getOutputStream());
                BufferedReader inFromSlave = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String slaveSentence = null;
                slaveSentence = inFromSlave.readLine();
                if (slaveSentence != null && slaveSentence.equals("Delay_Req")) {
                    t4_date = new Date();
                    System.out.println("T4 established!");
                    if(!connectionSocket.isOutputShutdown()) outToSlave.writeBytes("Delay_Resp" + '\n' + t4_date.getTime() + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
