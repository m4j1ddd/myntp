import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class PTPMaster extends Thread {
    int master_port;
    String slave_ip;
    int slave_port;

    public PTPMaster(int master_port, String slave_ip, int slave_port) {
        this.master_port = master_port;
        this.slave_ip = slave_ip;
        this.slave_port = slave_port;
    }

    public int getMaster_port() {
        return master_port;
    }

    public void setMaster_port(int master_port) {
        this.master_port = master_port;
    }

    public String getSlave_ip() {
        return slave_ip;
    }

    public void setSlave_ip(String slave_ip) {
        this.slave_ip = slave_ip;
    }

    public int getSlave_port() {
        return slave_port;
    }

    public void setSlave_port(int slave_port) {
        this.slave_port = slave_port;
    }

    public void run() {
        try {
            new PTPMasterServer(master_port).run();
            new PTPMasterClient(slave_ip, slave_port).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
