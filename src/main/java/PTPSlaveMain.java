import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 2) {
            new PTPSlave(args[0], Integer.valueOf(args[1])).run();
        }
    }
}
