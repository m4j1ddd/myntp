import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MonitorMain {
    public static String monitor_ip = "10.0.0.8";
    public static int monitor_port = 8000;

    public static void send_msg_time(long time, String msg) throws IOException {
        Socket monitor_socket = new Socket(monitor_ip, monitor_port);
        DataOutputStream outToMonitor = new DataOutputStream(monitor_socket.getOutputStream());
        outToMonitor.writeBytes("time" + '\n' + time + '\n' + "message" + '\n' + msg + '\n');
        monitor_socket.close();
    }

    public static void send_count(int count) throws IOException {
        Socket monitor_socket = new Socket(monitor_ip, monitor_port);
        DataOutputStream outToMonitor = new DataOutputStream(monitor_socket.getOutputStream());
        outToMonitor.writeBytes("count" + '\n' + count + '\n');
        monitor_socket.close();
    }

    public static void send_sync_time(long time) throws IOException {
        Socket monitor_socket = new Socket(monitor_ip, monitor_port);
        DataOutputStream outToMonitor = new DataOutputStream(monitor_socket.getOutputStream());
        outToMonitor.writeBytes("sync" + '\n' + time + '\n');
        monitor_socket.close();
    }

    public static void main(String[] args) throws IOException {
        TimeCounter.getInstance().start();

        Monitor monitor = new Monitor(monitor_port);
        monitor.start();

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String command = scanner.next();
            if(command.equals("print")) {
                monitor.print();
            }
        }
    }
}
