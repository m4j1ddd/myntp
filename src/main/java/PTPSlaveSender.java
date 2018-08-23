import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTPSlaveSender extends Thread {
    private Socket connectionSocket;
    private long t3;

    public PTPSlaveSender(Socket socket) {
        connectionSocket = socket;
    }

    public long getT3() {
        return t3;
    }

    public void run() {
        try {
            while (true) {
                DataOutputStream outToMaster = new DataOutputStream(connectionSocket.getOutputStream());

                t3 = TimeCounter.getInstance().getTime();
//                System.out.println("T3 established!");
                outToMaster.writeBytes("Delay_Req" + '\n');
//                System.out.println("T3 = " + t3);

                MonitorMain.send_count(1);
                sleep(6000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
