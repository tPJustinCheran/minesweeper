package minesweeper;

import minesweeper.exception.MinesweeperException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Custom Timer Class for Timing the Gameplay.
 */
public class CustomTimer{
    private Timer timer;
    private int seconds;
    private int oldTiming;
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
            oldTiming = storage.loadTime();
        } catch (MinesweeperException noOldTiming) {
            oldTiming = 0;
        }
    }

    /**
     * Start timer. If there is a previous gameplay, add the time from that gameplay.
     *
     */
    public void startTime() {
        seconds = oldTiming;
        timerRunning = true;
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                seconds++;
            }
        }, 0, 1000);
    }

    public void restartTime() {
        this.stopTime();
        timer = new Timer();
        oldTiming = 0;
        seconds = 0;
        this.startTime();
    }

    /**
     * Get Time in Seconds.
     *
     * @return Integer (time in seconds).
     */
    public int getTimeSecs() {
        return seconds;
    }

    /**
     * Stop Timer. Used when game ends.
     */
    public void stopTime() {
        if (this.getTimerRunning()) {
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
        String timeSecondsString = Integer.toString(this.getTimeSecs());
        storage.storeTime(timeSecondsString);
        timer.cancel();
    }

    /**
     * Display Time in Mins and Seconds.
     *
     * @return String (Time in Mins and Seconds).
     */
    public String displayTimeMinSecs() {
        return String.format("%02d:%02d", this.getTimeSecs()/60, this.getTimeSecs()%60);
    }

    public boolean getTimerRunning() {
        return timerRunning;
    }

    public void setTimerRunning(boolean isTimerRunning) {
        this.timerRunning = isTimerRunning;
    }

}
