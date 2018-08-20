import java.io.IOException;
import java.util.Date;

import static java.lang.Thread.sleep;

public class PrimaryServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 3) {
            TimeCounter.getInstance().start();

            String timeServerIP = args[0];
            int timeServerPort = Integer.valueOf(args[1]);
            int port = Integer.valueOf(args[2]);

            NTPParent ntpParent = new NTPParent(port);
            ntpParent.start();

            NTPChild ntpChild = new NTPChild(timeServerIP, timeServerPort);
            while(true) {
                long time = ntpChild.run_cristian();
                TimeCounter.getInstance().setTime(time);
                System.out.println("time = " + time);
                sleep(60000);
            }
        }
    }
}
