import com.sun.webkit.ThemeClient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class PTPSlave extends Thread {
    int slave_port;
    String master_ip;
    int master_port;

    public PTPSlave(int slave_port, String master_ip, int master_port) {
        this.slave_port = slave_port;
        this.master_ip = master_ip;
        this.master_port = master_port;
    }

    public int getSlave_port() {
        return slave_port;
    }

    public void setSlave_port(int slave_port) {
        this.slave_port = slave_port;
    }

    public String getMaster_ip() {
        return master_ip;
    }

    public void setMaster_ip(String master_ip) {
        this.master_ip = master_ip;
    }

    public int getMaster_port() {
        return master_port;
    }

    public void setMaster_port(int master_port) {
        this.master_port = master_port;
    }

    public void run() {
        try {
            new PTPSlaveServer(slave_port).run();
            new PTPSlaveClient(master_ip, master_port).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
