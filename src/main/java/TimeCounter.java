import static java.lang.Thread.sleep;

public class TimeCounter extends Thread {
    private static TimeCounter timeCounter = null;
    private long time;
    private int drift;

    private TimeCounter(long time) {
        this.time = time;
        this.drift = 1;
    }

    private TimeCounter(long time, int drift) {
        this.time = time;
        this.drift = drift;
    }

    public static TimeCounter getInstance(long time, int drift) {
        if(timeCounter == null)
            timeCounter = new TimeCounter(time, drift);

        return timeCounter;
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
                sleep(drift);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
