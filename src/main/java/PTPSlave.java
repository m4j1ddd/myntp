import java.io.IOException;
import java.net.Socket;

public class PTPSlave extends Thread {
    private PTPSlaveReceiver ptpSlaveReceiver;
    private PTPSlaveSender ptpSlaveSender;

    public PTPSlave(String ip, int port) throws IOException {
        Socket connectionSocket = new Socket(ip, port);
        ptpSlaveReceiver = new PTPSlaveReceiver(connectionSocket);
        ptpSlaveSender = new PTPSlaveSender(connectionSocket);
    }

    public long getMsDifference() {
        return ptpSlaveReceiver.getT2() - ptpSlaveReceiver.getT1();
    }

    public long getSmDifference() {
        return ptpSlaveReceiver.getT4() - ptpSlaveSender.getT3();
    }

    public long getOffset() {
        return (getMsDifference() - getSmDifference())/2;
    }

    public void run() {
        ptpSlaveReceiver.start();
        ptpSlaveSender.start();
    }
}
