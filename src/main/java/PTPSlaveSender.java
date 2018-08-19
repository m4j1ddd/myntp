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
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
        try {
            while (connectionSocket.isConnected()) {
                DataOutputStream outToMaster = new DataOutputStream(connectionSocket.getOutputStream());

                Date t3_date = new Date();
                System.out.println("T3 established!");
                if(!connectionSocket.isOutputShutdown()) outToMaster.writeBytes("Delay_Req" + '\n');
                System.out.println("T3 date = " + dateFormat.format(t3_date));
                t3 = t3_date.getTime();

                sleep(6000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
