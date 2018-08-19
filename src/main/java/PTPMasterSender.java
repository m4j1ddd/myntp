import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PTPMasterSender extends Thread {
    private Socket connectionSocket;

    public PTPMasterSender(Socket socket) {
        connectionSocket = socket;
    }

    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
        try {
            while(connectionSocket.isConnected()) {
                DataOutputStream outToSlave = new DataOutputStream(connectionSocket.getOutputStream());

                Date t1_date = new Date();
                System.out.println("T1 established");
                if(!connectionSocket.isOutputShutdown()) outToSlave.writeBytes("Sync" + '\n');
                System.out.println("T1 date = " + dateFormat.format(t1_date));
                if(!connectionSocket.isOutputShutdown()) outToSlave.writeBytes("Follow_Up" + '\n' + t1_date.getTime() + '\n');

                MonitorMain.send_count(2);
                sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
