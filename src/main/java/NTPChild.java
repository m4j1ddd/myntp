import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

public class NTPChild {
    String ip;
    int port;

    public NTPChild(String ip, int port) {
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

    public long calc_offset() throws IOException {
        Date tr1_date, ts2_date = null;
        long ts1 = 0, tr1, ts2, tr2 = 0, o;
        String serverSentence;
        Socket clientSocket = new Socket(ip, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("NTP Started!");
        outToServer.writeBytes("start" + '\n');

        serverSentence = inFromServer.readLine();
        tr1_date = new Date();
        if(serverSentence.equals("MSG1")) {
            System.out.println("TR1 established!");

            ts2_date = new Date();
            System.out.println("TS2 established!");
            outToServer.writeBytes("MSG2" + '\n');

            serverSentence = inFromServer.readLine();
            if(serverSentence.equals("TS1")) {
                ts1 = Long.valueOf(inFromServer.readLine());
                System.out.println("TS1 recieved");
                serverSentence = inFromServer.readLine();
                if(serverSentence.equals("TR2")) {
                    tr2 = Long.valueOf(inFromServer.readLine());
                    System.out.println("TR2 recieved");
                }
            }
        }
        ts2 = ts2_date.getTime();
        tr1 = tr1_date.getTime();
        System.out.println("TS1 = " + ts1 + ", TR1 = " + tr1 + ", TS2 = " + ts2 + ", TR2 = " + tr2);
        o = (tr1 - tr2 + ts2 - ts1)/2;

        clientSocket.close();
        return o;
    }

    public long calc_rtt() throws IOException {
        Date rtt_start_date, rtt_date;
        long rtt = 0;
        String serverSentence;
        Socket clientSocket = new Socket(ip, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        rtt_start_date = new Date();
        System.out.println("RTT Started!");
        outToServer.writeBytes("RTT" + '\n');
        serverSentence = inFromServer.readLine();
        if(serverSentence.equals("RTT")) {
            rtt_date = new Date();
            rtt = rtt_date.getTime() - rtt_start_date.getTime();
            System.out.println("RTT calculated!");
        }

        clientSocket.close();
        return rtt;
    }
}
