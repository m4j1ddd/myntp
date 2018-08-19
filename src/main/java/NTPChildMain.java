import java.io.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;

public class NTPChildMain {
    public static void main(String[] args) throws IOException, InterruptedException {
//        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date));
//        sleep(5000);
//        Date date1 = new Date();
//        System.out.println(dateFormat.format(date1));
//        String command = "sudo date +%Y%m%d -s \"20120418\"";
//        System.out.println(command);
//        String s;
//        Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c","sudo date --set=\""+ dateFormat.format(date) +"\""});
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(p.getInputStream()));
//        while ((s = br.readLine()) != null)
//            System.out.println("line: " + s);
//        p.waitFor();
//        System.out.println ("exit: " + p.exitValue());
//        p.destroy();
//        Date date2 = new Date();
//        System.out.println(dateFormat.format(date2));
//        Date date = new Date();
//        System.out.println(date);
//        long d = date.getTime();
//        Date date1 = new Date(d);
//        System.out.println(date1);
        if(args.length >= 3) {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);
            String command = args[2];
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
            NTPChild ntpChild = new NTPChild(ip, port);
            if(command.equals("ntp")) {
                System.out.println("offset = " + ntpChild.calc_offset() + ", rtt = " + ntpChild.calc_rtt());
                ntpChild.send_or();
            }
            else if(command.equals("cristian")) {
                while(true) {
                    long time = ntpChild.calc_time();
                    Date date = new Date(time);
                    Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c","sudo date --set=\""+ dateFormat.format(date) +"\""});
                    System.out.println("time = " + time + " date = " + date);
                    sleep(60000);
                }
            }
        }
    }
}
