import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTPSlaveReceiver extends Thread {
    private Socket connectionSocket;
    private long t1, t2, t4;

    public PTPSlaveReceiver(Socket socket) {
        connectionSocket = socket;
        t1 = 0;
        t2 = 0;
        t4 = 0;
    }

    public long getT1() {
        return t1;
    }

    public long getT2() {
        return t2;
    }

    public long getT4() {
        return t4;
    }

    public void run() {
        try {
            while(true) {
                BufferedReader inFromMaster = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                String masterSentence = inFromMaster.readLine();
                if (masterSentence.equals("Sync")) {
                    t2 = TimeCounter.getInstance().getTime();
//                    System.out.println("T2 established!");
//                    System.out.println("T2 = " + t2);
                } else if (masterSentence.equals("Follow_Up")) {
                    masterSentence = inFromMaster.readLine();
                    t1 = Long.valueOf(masterSentence);
                } else if (masterSentence.equals("Delay_Resp")) {
                    masterSentence = inFromMaster.readLine();
                    t4 = Long.valueOf(masterSentence);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
