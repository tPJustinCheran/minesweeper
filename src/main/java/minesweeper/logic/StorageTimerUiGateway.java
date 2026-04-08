package minesweeper.logic;

import minesweeper.exception.MinesweeperException;
import minesweeper.exception.StorageException;
import minesweeper.storage.Storage;

import java.util.List;

/**
 * Class to bridge Storage, Timer and UI objects.
 */
public class StorageTimerUiGateway {

    private final Storage storage;
    private final CustomTimer customTimer;

    /**
     * Initialises storage and customTimer objects
     *
     * @throws StorageException if unable to create Storage or CustomTimer object.
     */
    public StorageTimerUiGateway() throws StorageException {
        String home = System.getProperty("user.dir");
        this.storage = new Storage(home);
        this.customTimer = new CustomTimer(storage);
    }

    /**
     * Checks if there is existing gameplay stored in /data folder.
     *
     * @return boolean true if previous gameplay exists.
     */
    public boolean hasExistingSave() {
        boolean hasExistingSave;
        try {
            storage.loadGame();
            storage.loadSolution();
            hasExistingSave = true;
        } catch (MinesweeperException e) {
            hasExistingSave = false;
        }
        return hasExistingSave;
    }

    /**
     * Stores Solution to data/solution.txt file using storage object.
     *
     * @param solutionString Solution String to be stored.
     * @throws StorageException
     */
    public void storeSolution(String solutionString) throws StorageException {
        storage.storeSolution(solutionString);
    }

    /**
     * Stores Game to data/game.txt file using storage object.
     *
     * @param gameString Game String to be stored.
     * @throws StorageException
     */
    public void storeGame(String gameString) throws StorageException {
        storage.storeGame(gameString);
    }

    /**
     * Loads Solution from data/solution.txt file using storage object.
     *
     * @return Solution String from data/solution.txt
     * @throws StorageException
     */
    public List<String> loadSolution() throws StorageException {
        return storage.loadSolution();
    }

    /**
     * Loads Game from data/game.txt file using storage object.
     *
     * @return Game String from data/game.txt
     * @throws StorageException
     */
    public List<String> loadGame() throws StorageException {
        return storage.loadGame();
    }

    /**
     * Stores Hint count to data/hint.txt file using storage object.
     *
     * @param count value to be stored to hint.txt file.
     * @throws StorageException
     */
    public void storeHint(String count) throws StorageException {
        storage.storeHint(count);
    }

    /**
     * Loads Hint count from data/hint.txt file using storage object.
     *
     * @return integer, value from hint.txt file.
     * @throws StorageException
     */
    public int loadHint() throws StorageException {
        return storage.loadHint();
    }

    /**
     * Loads leaderboard from data/leaderboard.txt using storage object.
     *
     * @return List of Strings representing leaderboard entries, empty list if no entries.
     * @throws StorageException
     */
    public List<String> loadLeaderboard() throws StorageException {
        return storage.loadLeaderboard();
    }

    /**
     * Calls addEntry from storage object.
     * Adds a new entry to the leaderboard, sorts by time, then saves to leaderboard.txt.
     *
     * @param name player name.
     * @param timeMillis completion time in milliseconds.
     * @throws StorageException
     */
    public void addEntry(String name, long timeMillis) throws StorageException {
        storage.addEntry(name, timeMillis);
    }

    /**
     * Clear all game-related files in data folder using storage object.
     *
     * @throws StorageException
     */
    public void clearGame() throws StorageException {
        storage.clearGame();
    }

    /**
     * Start Timer using customTimer object.
     */
    public void startTime() {
        customTimer.startTime();
    }

    /**
     * Zero Timer using customTimer object.
     */
    public void zeroTime() {
        customTimer.zeroTime();
    }

    /**
     * Restart Timer using customTimer object.
     */
    public void restartTime() {
        customTimer.restartTime();
    }

    /**
     * Get time in milliseconds using customTimer object.
     *
     * @return time (milliseconds)
     */
    public long getTimeMillis() {
        return customTimer.getTimeMillis();
    }

    /**
     * Stop Timer using customTimer object.
     */
    public void stopTime() {
        customTimer.stopTime();
    }

    /**
     * Pause and Stop Timer using customTimer and storage object.
     * @throws MinesweeperException
     */
    public void pauseAndStopTime() throws MinesweeperException {
        customTimer.pauseAndStopTime(storage);
    }

    /**
     * Display the time in min and secs using customTimer object.
     *
     * @return time in min and seconds.
     */
    public String displayTimeMinSecs() {
        return customTimer.displayTimeMinSecs();
    }

}
