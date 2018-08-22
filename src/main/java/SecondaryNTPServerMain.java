import java.io.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Thread.sleep;

public class SecondaryNTPServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 3) {
            TimeCounter.getInstance().start();

            String primaryServerIP = args[0];
            int primaryServerPort = Integer.valueOf(args[1]);
            int port = Integer.valueOf(args[2]);

            NTPParent ntpParent = new NTPParent(port);
            ntpParent.start();

            NTPChild ntpChild = new NTPChild(primaryServerIP, primaryServerPort);
            ntpChild.start();
        }
    }
}
