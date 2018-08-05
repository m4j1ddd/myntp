import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPSlaveServer extends Thread {
    ServerSocket serverSocket;
    long msDifference;

    public PTPSlaveServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public PTPSlaveServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public long getMsDifference() {
        return msDifference;
    }

    public void run() {
        long t1 = 0, t2;
        try {
            while(true) {
                Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromMaster = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToMaster = new DataOutputStream(connectionSocket.getOutputStream());
                String masterSentence;

                masterSentence = inFromMaster.readLine();
                Date t2_date = new Date();
                if(masterSentence.equals("Sync")) {
                    System.out.println("T2 established!");
                    masterSentence = inFromMaster.readLine();
                    if(masterSentence.equals("Follow_Up")) {
                        masterSentence = inFromMaster.readLine();
                        t1 = Long.valueOf(masterSentence);
                    }
                }
                t2 = t2_date.getTime();
                msDifference = t2 - t1;
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
