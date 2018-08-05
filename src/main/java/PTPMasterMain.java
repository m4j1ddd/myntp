import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.*;

public class PTPMasterMain {
    public static void main(String[] args) throws IOException {
        if(args.length >= 3) {
            new PTPMaster(Integer.valueOf(args[0]), args[1], Integer.valueOf(args[2]));
        }
    }
}
