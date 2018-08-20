import static java.lang.Thread.sleep;

public class TimeCounter extends Thread {
    private static TimeCounter timeCounter = null;
    private long time;

    private TimeCounter(long time) {
        this.time = time;
    }

    public static TimeCounter getInstance(long time) {
        if(timeCounter == null)
            timeCounter = new TimeCounter(time);

        return timeCounter;
    }

    public static TimeCounter getInstance() {
        if(timeCounter == null)
            timeCounter = new TimeCounter(0);

        return timeCounter;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void run() {
        try {
            while (true) {
                time++;
                sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
