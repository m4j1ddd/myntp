import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTPMasterReceiver extends Thread {
    private Socket connectionSocket;

    public PTPMasterReceiver(Socket socket) {
        connectionSocket = socket;
    }

    public void run() {
        try {
            while(true) {
                DataOutputStream outToSlave = new DataOutputStream(connectionSocket.getOutputStream());
                BufferedReader inFromSlave = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String slaveSentence = inFromSlave.readLine();
                if (slaveSentence.equals("Delay_Req")) {
                    long t4 = TimeCounter.getInstance().getTime();
                    System.out.println("T4 established!");
                    System.out.println("T4 = " + t4);
                    outToSlave.writeBytes("Delay_Resp" + '\n' + t4 + '\n');
                }
                MonitorMain.send_count(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
