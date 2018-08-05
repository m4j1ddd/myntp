import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class PTPSlaveClient extends Thread {
    String ip;
    int port;
    long smDifference;

    public PTPSlaveClient(String ip, int port) {
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

    public long getSmDifference() {
        return smDifference;
    }

    public void run() {
        long t3, t4 = 0;
        try {
            while(true) {
                Socket slaveSocket = new Socket(ip, port);
                DataOutputStream outToMaster = new DataOutputStream(slaveSocket.getOutputStream());
                BufferedReader inFromMaster = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()));
                String masterSentence;

                Date t3_date = new Date();
                System.out.println("T3 established!");
                outToMaster.writeBytes("Delay_Req" + '\n');
                masterSentence = inFromMaster.readLine();
                if(masterSentence.equals("Delay_Resp")) {
                    masterSentence = inFromMaster.readLine();
                    t4 = Long.valueOf(masterSentence);
                }
                t3 = t3_date.getTime();
                smDifference = t4 - t3;

                slaveSocket.close();
                sleep(120000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
