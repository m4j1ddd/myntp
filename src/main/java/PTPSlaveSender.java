import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
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
            while (connectionSocket.isConnected()) {
                DataOutputStream outToMaster = new DataOutputStream(connectionSocket.getOutputStream());

                Date t3_date = new Date();
                System.out.println("T3 established!");
                if(!connectionSocket.isOutputShutdown()) outToMaster.writeBytes("Delay_Req" + '\n');

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
