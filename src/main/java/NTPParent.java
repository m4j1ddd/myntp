import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public long calc_recieve_time(long ts1) {
        long tr1 = ts1 + (rtt/2) + offset;
        return tr1;
    }

    public long calc_send_time_on_child(long tr2) {
        long ts2 = tr2 - (rtt/2) + offset;
        return ts2;
    }

    public long calc_send_time_on_parent(long tr2) {
        long tss2 = tr2 - (rtt/2);
        return tss2;
    }

    @Override
    public void run() {
        String clientSentence;
        Date ts1_date, tr2_date;
        long ts1, tr2;
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
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
                        System.out.println("TS1 date: " + dateFormat.format(ts1_date) + "TR2 date: " + dateFormat.format(tr2_date));
                        outToClient.writeBytes("TS1" + '\n' + ts1 + '\n' + "TR2" + '\n' + tr2 + '\n');
                    }
                } else if (clientSentence.equals("RTT")) {
                    outToClient.writeBytes(clientSentence + '\n');
                } else if(clientSentence.equals("TIME")) {
                    Date date = new Date();
                    Long time = date.getTime();
                    System.out.println("time = " + time + " date = " + dateFormat.format(date));
                    outToClient.writeBytes("TIME" + '\n' + time + '\n');
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
