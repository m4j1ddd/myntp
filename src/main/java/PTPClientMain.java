import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class PTPClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 2) {
            if(args.length >= 4)
                TimeCounter.getInstance(Long.valueOf(args[3])).start();
            else
                TimeCounter.getInstance().start();

            String secondaryPTPServerIP = args[0];
            int secondaryPTPServerPort = Integer.valueOf(args[1]);
            int port = Integer.valueOf(args[2]);

            PTPSlave ptpSlave = new PTPSlave(secondaryPTPServerIP, secondaryPTPServerPort);
            ptpSlave.start();

            Client client = new Client(port);
            client.start();

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String command = scanner.next();
                if(command.equals("send")) {
                    String other_ip = scanner.next();
                    int other_port = Integer.valueOf(scanner.next());
                    String msg = scanner.next();
                    client.sendMessage(other_ip, other_port, msg);
                }
            }
        }
    }
}
