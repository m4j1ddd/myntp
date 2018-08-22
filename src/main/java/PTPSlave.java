import java.io.IOException;
import java.net.Socket;
import java.util.Date;

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
        try {
            ptpSlaveReceiver.start();
            ptpSlaveSender.start();

            while (true) {
                long offset = getOffset();
                System.out.println("offset = " + offset);
                long old_time = TimeCounter.getInstance().getTime();
                long time = old_time + offset;
                TimeCounter.getInstance().setTime(time);
                System.out.println("time = " + time);
                MonitorMain.send_sync_time(time);
                sleep(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
