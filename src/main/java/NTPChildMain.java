import java.io.IOException;

public class NTPChildMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 2) {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);
            NTPChild ntpChild = new NTPChild(ip, port);
            System.out.println("offset = " + ntpChild.calc_offset() + ", rtt = " + ntpChild.calc_rtt());
        }
    }
}
