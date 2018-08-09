import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static java.lang.Thread.sleep;

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length >= 2) {
            PTPSlave ptpSlave = new PTPSlave(args[0], Integer.valueOf(args[1]));
            ptpSlave.start();
            while(true) {
                sleep(10000);
                System.out.println(ptpSlave.getOffset());
            }
        }
    }
}
