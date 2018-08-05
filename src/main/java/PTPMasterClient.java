import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class PTPMasterClient extends Thread {
    String ip;
    int port;

    public PTPMasterClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void run() {
        try {
            while(true) {
                Socket masterSocket = new Socket(ip, port);
                DataOutputStream outToSlave = new DataOutputStream(masterSocket.getOutputStream());
                BufferedReader inFromSlave = new BufferedReader(new InputStreamReader(masterSocket.getInputStream()));
                String slaveSentence;

                Date t1_date = new Date();
                System.out.println("T1 established");
                outToSlave.writeBytes("Sync" + '\n');
                outToSlave.writeBytes("Follow_Up" + '\n' + t1_date.getTime() + '\n');

                masterSocket.close();
                sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
