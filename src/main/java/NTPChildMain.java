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
//        Date date = new Date();
//        System.out.println(date);
//        Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "sudo date -s \"17 Aug 2018 04:00:00\""});
//        Date date1 = new Date();
//        System.out.println(date1);
        if(args.length >= 3) {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);
            String command = args[2];
            DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
            NTPChild ntpChild = new NTPChild(ip, port);
            if(command.equals("ntp")) {
                while(true) {
                    long offset = ntpChild.calc_offset();
                    System.out.println("offset = " + offset);
                    Date old_date = new Date();
                    long time = old_date.getTime() - offset;
                    Date date = new Date(time);
                    Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c","sudo date -s \""+ dateFormat.format(date) +"\""});
                    System.out.println("date = " + dateFormat.format(date));
                    sleep(20000);
                }
            }
            else if(command.equals("cristian")) {
                while(true) {
                    long time = ntpChild.calc_time();
                    Date date = new Date(time);
                    Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c","sudo date -s \""+ dateFormat.format(date) +"\""});
                    System.out.println("time = " + time + " date = " + dateFormat.format(date));
                    sleep(60000);
                }
            }
        }
    }
}
