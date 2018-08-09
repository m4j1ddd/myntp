import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class NTPParentMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 1) {
            int port = Integer.valueOf(args[0]);
            NTPParent ntpParent = new NTPParent(port);
            ntpParent.start();
        }
    }
}
