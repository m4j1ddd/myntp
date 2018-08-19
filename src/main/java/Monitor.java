import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Monitor {
    ServerSocket serverSocket;
    ArrayList<Message> messages;
    int total_count;

    public Monitor(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        messages = new ArrayList<Message>();
        total_count = 0;
    }

    public void print_sorted() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss.SSS");
        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message lhs, Message rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getTime() > rhs.getTime() ? -1 : (lhs.getTime() < rhs.getTime()) ? 1 : 0;
            }
        });
        for(int i = 0; i < messages.size(); i++) {
            System.out.println("Message: " + messages.get(i).getText() + ", Date: " + dateFormat.format(messages.get(i).getDate()));
        }
    }

    public void print() {
        print_sorted();
        System.out.println("Total number of messages for synchronization: " + total_count);
    }

    public void run() {
        try {
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String clientSentence = inFromClient.readLine();
                if(clientSentence.equals("time")) {
                    clientSentence = inFromClient.readLine();
                    long time = Long.valueOf(clientSentence);
                    clientSentence = inFromClient.readLine();
                    if(clientSentence.equals("message")) {
                        String msg = inFromClient.readLine();
                        Message message = new Message(time, msg);
                        messages.add(message);
                    }
                }
                else if(clientSentence.equals("count")) {
                    int count = Integer.valueOf(inFromClient.readLine());
                    total_count += count;
                }
                connectionSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
