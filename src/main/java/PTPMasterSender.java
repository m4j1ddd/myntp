import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPMasterSender extends Thread {
    private Socket connectionSocket;

    public PTPMasterSender(Socket socket) {
        connectionSocket = socket;
    }

    public void run() {
        try {
            while(true) {
                DataOutputStream outToSlave = new DataOutputStream(connectionSocket.getOutputStream());

                Date t1_date = new Date();
                System.out.println("T1 established");
                outToSlave.writeBytes("Sync" + '\n');
                outToSlave.writeBytes("Follow_Up" + '\n' + t1_date.getTime() + '\n');

                sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
