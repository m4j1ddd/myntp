import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPMasterServer extends Thread {
    ServerSocket serverSocket;

    public PTPMasterServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public PTPMasterServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while(true) {
                Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromSlave = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToSlave = new DataOutputStream(connectionSocket.getOutputStream());
                String slaveSentence;

                slaveSentence = inFromSlave.readLine();
                Date t4_date = new Date();
                if(slaveSentence.equals("Delay_Req")) {
                    System.out.println("T4 established!");
                    outToSlave.writeBytes("Delay_Resp" + '\n' + t4_date.getTime() + '\n');
                }
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
