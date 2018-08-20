import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NTPChild {
    private String ip;
    private int port;

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

    public long run_ntp() throws IOException {
        long ts1 = 0, tr1, ts2 = 0, tr2 = 0;
        String serverSentence;
        Socket clientSocket = new Socket(ip, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        System.out.println("NTP Started!");
        outToServer.writeBytes("start" + '\n');

        serverSentence = inFromServer.readLine();
        tr1 = TimeCounter.getInstance().getTime();
        if(serverSentence.equals("MSG1")) {
            System.out.println("TR1 established!");

            ts2 = TimeCounter.getInstance().getTime();
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

        System.out.println("TS1 = " + ts1 + ", TR1 = " + tr1 + ", TS2 = " + ts2 + ", TR2 = " + tr2);
        long offset = (tr1 - tr2 + ts2 - ts1)/2;

        clientSocket.close();
        MonitorMain.send_count(4);
        return offset;
    }

    public long run_cristian() throws IOException {
        long time = 0;
        String serverSentence;
        Socket clientSocket = new Socket(ip, port);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        long rtt_start = TimeCounter.getInstance().getTime();
        System.out.println("Cristian Started!");
        outToServer.writeBytes("TIME" + '\n');

        serverSentence = inFromServer.readLine();
        long rtt = TimeCounter.getInstance().getTime() - rtt_start;
        if(serverSentence.equals("TIME")) {
            serverSentence = inFromServer.readLine();
            time = Long.valueOf(serverSentence);
            System.out.println("TIME calculated!");
        }

        clientSocket.close();
        MonitorMain.send_count(2);
        return time + (rtt/2);
    }
}
