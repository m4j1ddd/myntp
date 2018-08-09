import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class NTPParent extends Thread {
    private ServerSocket serverSocket;
    private long offset, rtt;

    public NTPParent(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        offset = 0;
        rtt = 0;
    }

    public NTPParent(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        offset = 0;
        rtt = 0;
    }



    @Override
    public void run() {
        String clientSentence;
        Date ts1_date, tr2_date;
        long ts1, tr2;
        try {
            while(true) {
                Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                clientSentence = inFromClient.readLine();
                if (clientSentence.equals("start")) {
                    System.out.println("NTP Started!");
                    ts1_date = new Date();
                    System.out.println("TS1 established!");
                    outToClient.writeBytes("MSG1" + '\n');

                    clientSentence = inFromClient.readLine();
                    tr2_date = new Date();
                    if (clientSentence.equals("MSG2")) {
                        System.out.println("TR2 established!");

                        ts1 = ts1_date.getTime();
                        tr2 = tr2_date.getTime();
                        outToClient.writeBytes("TS1" + '\n' + ts1 + '\n' + "TR2" + '\n' + tr2 + '\n');
                    }
                } else if (clientSentence.equals("RTT")) {
                    outToClient.writeBytes(clientSentence + '\n');
                } else if(clientSentence.equals("OR")) {
                    offset = Long.valueOf(inFromClient.readLine());
                    rtt = Long.valueOf(inFromClient.readLine());
                    System.out.println("OFFSET and RTT received");

                }
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
