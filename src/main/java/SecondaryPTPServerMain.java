import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class SecondaryPTPServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 3) {
            TimeCounter.getInstance().start();

            String primaryServerIP = args[0];
            int primaryServerPort = Integer.valueOf(args[1]);
            int port = Integer.valueOf(args[2]);

            PTPMaster ptpMaster = new PTPMaster(port);
            ptpMaster.start();

            NTPChild ntpChild = new NTPChild(primaryServerIP, primaryServerPort);
            while(true) {
                long offset = ntpChild.run_ntp();
                System.out.println("offset = " + offset);
                long old_time = TimeCounter.getInstance().getTime();
                long time = old_time - offset;
                TimeCounter.getInstance().setTime(time);
                System.out.println("time = " + time);
                sleep(20000);
            }
        }
    }
}
