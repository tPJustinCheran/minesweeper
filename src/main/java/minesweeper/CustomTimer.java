package minesweeper;

import minesweeper.exception.MinesweeperException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Custom Timer Class for Timing the Gameplay.
 */
public class CustomTimer {
    private Timer timer;
    private long startTimeMillis;
    private long offsetMillis;
    private boolean timerRunning = false;

    /**
     * Constructor Class to initialise timer and load time from an uncompleted gameplay.
     *
     * @param storage minesweeper.Storage class to access time.txt file
     * @throws MinesweeperException Exception raised when there is no timing stored in time.txt file
     */
    public CustomTimer(Storage storage) throws MinesweeperException {
        timer = new Timer();
        try {
            offsetMillis = storage.loadTime();
        } catch (MinesweeperException noOldTiming) {
            offsetMillis = 0;
        }
    }

    /**
     * Start timer. If there is a previous gameplay, add the time from that gameplay.
     */
    public void startTime() {
        startTimeMillis = System.currentTimeMillis();
        timerRunning = true;
        timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // no-op: time is calculated on read, not incremented
            }
        }, 0, 10);
    }

    /**
     * Zero the timer. Resets offset and frozen time to 0.
     */
    public void zeroTime() {
        offsetMillis = 0;
    }


    /**
     * Restart Timer. Stops, zeroes, then starts fresh from 0.
     */
    public void restartTime() {
        this.stopTime();
        this.zeroTime();
        this.startTime();
    }

    /**
     * Get Time in Milliseconds.
     *
     * @return long (time in milliseconds).
     */
    public long getTimeMillis() {
        if (!timerRunning) {
            return offsetMillis;
        }
        return System.currentTimeMillis() - startTimeMillis + offsetMillis;
    }

    /**
     * Get Time in Seconds.
     *
     * @return Integer (time in seconds).
     */
    public int getTimeSecs() {
        return (int) (getTimeMillis() / 1000);
    }

    /**
     * Stop Timer. Used when game ends.
     */
    public void stopTime() {
        if (this.getTimerRunning()) {
            offsetMillis = getTimeMillis();  // freeze current time before cancelling
            timer.cancel();
            this.setTimerRunning(false);
        }
    }

    /**
     * Pause Timer. Store time into time.txt for future use. Used when player exits program.
     *
     * @param storage minesweeper.Storage class to access time.txt file
     * @throws MinesweeperException Exception raised when unable to write to time.txt file.
     */
    public void pauseAndStopTime(Storage storage) throws MinesweeperException {
        String timeMillisString = Long.toString(this.getTimeMillis());
        storage.storeTime(timeMillisString);
        timer.cancel();
    }

    /**
     * Display Time in Mins, Seconds and Milliseconds.
     *
     * @return String (Time in MM:SS.mmm format).
     */
    public String displayTimeMinSecs() {
        long millis = getTimeMillis();
        long mins = millis / 60000;
        long secs = (millis % 60000) / 1000;
        long ms   = millis % 1000;
        return String.format("%02d:%02d.%03d", mins, secs, ms);
    }

    public boolean getTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean isTimerRunning) {
        this.timerRunning = isTimerRunning;
    }
}
