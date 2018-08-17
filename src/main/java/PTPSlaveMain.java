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

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 2) {
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
            PTPSlave ptpSlave = new PTPSlave(args[0], Integer.valueOf(args[1]));
            ptpSlave.start();
            while(true) {
                long offset = ptpSlave.getOffset();
                System.out.println("offset = " + offset);
                Date old_date = new Date();
                Date date = new Date(old_date.getTime() + offset);
                Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c","sudo date -s \""+ dateFormat.format(date) +"\""});
                System.out.println("date = " + dateFormat.format(date));
                MonitorMain.send_sync_time(date.getTime());
                sleep(10000);
            }
        }
    }
}
