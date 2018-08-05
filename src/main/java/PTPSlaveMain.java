import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PTPSlaveMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 3) {
            new PTPSlave(Integer.valueOf(args[0]), args[1], Integer.valueOf(args[2])).run();
        }
    }
}
