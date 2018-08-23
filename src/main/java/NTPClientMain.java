import java.io.IOException;
import java.util.Scanner;

public class NTPClientMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 3) {
            if(args.length >= 5)
                TimeCounter.getInstance(Long.valueOf(args[3]), Integer.valueOf(args[4])).start();
            else if(args.length == 4)
                TimeCounter.getInstance(Long.valueOf(args[3])).start();
            else
                TimeCounter.getInstance().start();

            String secondaryNTPServerIP = args[0];
            int secondaryNTPServerPort = Integer.valueOf(args[1]);
            int port = Integer.valueOf(args[2]);

            NTPChild ntpChild = new NTPChild(secondaryNTPServerIP, secondaryNTPServerPort, true);
            ntpChild.start();

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
