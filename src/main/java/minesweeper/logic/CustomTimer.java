package minesweeper.logic;

import minesweeper.exception.MinesweeperException;
import minesweeper.storage.Storage;

/**
 * Custom Timer Class for Timing the Gameplay.
 */
public class CustomTimer {
    //private Timer timer;
    private long startTimeMillis;
    private long offsetMillis;
    private boolean isTimerRunning = false;

    /**
     * Constructor Class which loads time from an uncompleted gameplay.
     *
     * @param storage minesweeper.storage.Storage class to access time.txt file
     */
    public CustomTimer(Storage storage) {
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
        isTimerRunning = true;
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
        if (!isTimerRunning) {
            return offsetMillis;
        }
        return System.currentTimeMillis() - startTimeMillis + offsetMillis;
    }

    /**
     * Stop Timer. Used when game ends.
     */
    public void stopTime() {
        if (isTimerRunning) {
            offsetMillis = getTimeMillis();
            this.setTimerRunningBoolean(false);
        }
    }

    /**
     * Pause Timer. Store time into time.txt for future use. Used when player exits program.
     *
     * @param storage minesweeper.storage.Storage class to access time.txt file
     * @throws MinesweeperException Exception raised when unable to write to time.txt file.
     */
    public void pauseAndStopTime(Storage storage) throws MinesweeperException {
        offsetMillis = getTimeMillis();
        if (isTimerRunning) {
            isTimerRunning = false;
        }
        storage.storeTime(Long.toString(offsetMillis));
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
        long ms = millis % 1000;
        return String.format("%02d:%02d.%03d", mins, secs, ms);
    }

    public void setTimerRunningBoolean(boolean isTimerRunning) {
        this.isTimerRunning = isTimerRunning;
    }

    /**
     * Used for unit testing
     * @return offsetMillis
     */
    public long getOffsetMillis() {
        return this.offsetMillis;
    }
}
