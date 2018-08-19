import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 1) {
            Client client = new Client(Integer.valueOf(args[0]));
            client.start();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String command = scanner.next();
                if(command.equals("send")) {
                    String ip = scanner.next();
                    int port = Integer.valueOf(scanner.next());
                    String msg = scanner.next();
                    client.sendMessage(ip, port, msg);
                }
            }
        }
    }
}
