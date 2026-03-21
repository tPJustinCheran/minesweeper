package minesweeper;

import minesweeper.exception.StorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {

    // Constants
    private static final String FOLDER_NAME = "data";
    private static final String GAME_FILE_NAME = "game.txt";
    private static final String SOLUTION_FILE_NAME = "solution.txt";
    private static final String TIME_FILE_NAME = "time.txt";
    private static final String HINT_FILE_NAME = "hint.txt";

    // Instance Attributes
    private Path folderPath;
    private Path gameFilePath;
    private Path solutionFilePath;
    private Path timeFilePath;
    private Path hintFilePath;

    /**
     * Constructor class to define directories
     * Calls onStartup() to initialise all required files
     *
     * @param home current directory (e.g. src/main/java)
     * @throws StorageException
     */
    public Storage(String home) throws StorageException {
        this.folderPath = Paths.get(home, FOLDER_NAME);
        this.gameFilePath = this.folderPath.resolve(GAME_FILE_NAME);
        this.solutionFilePath = this.folderPath.resolve(SOLUTION_FILE_NAME);
        this.timeFilePath = this.folderPath.resolve(TIME_FILE_NAME);
        this.hintFilePath = this.folderPath.resolve(HINT_FILE_NAME);
        onStartup();
    }

    /**
     * Helper function to check and create files / folders
     *
     * @param filepath File/folder to be checked / created
     * @param isDirectory True if folder to be created, false if file to be created
     * @throws StorageException Error while creating File
     */
    private void checkAndCreateFileFolder(Path filepath, boolean isDirectory) throws StorageException {
        boolean fileExists = Files.exists(filepath);
        if (!fileExists) {
            // directory/file does not exist -- create folder/file
            try {
                if (isDirectory) {
                    Files.createDirectories(filepath);
                } else {
                    Files.createFile(filepath);
                }
            } catch (IOException fileError) {
                throw new StorageException("Unable to create directory/file: " + fileError.getMessage());
            }
        }
    }

    /**
     * Check if directory and file exists. If not, create empty directory and file.
     */
    public void onStartup() throws StorageException {
        checkAndCreateFileFolder(this.folderPath, true);
        checkAndCreateFileFolder(this.gameFilePath, false);
        checkAndCreateFileFolder(this.solutionFilePath, false);
        checkAndCreateFileFolder(this.timeFilePath, false);
        checkAndCreateFileFolder(this.hintFilePath, false);
    }

    /**
     * Store solution to solution.txt file
     *
     * @param solutionString Solution Grid as a string
     * @throws StorageException
     */
    public void storeSolution(String solutionString) throws StorageException {
        try {
            Files.write(this.solutionFilePath, solutionString.getBytes());
        } catch (IOException writeError) {
            throw new StorageException("Unable to store solution: " + writeError.getMessage());
        }
    }

    /**
     * Store existing game to game.txt file.
     *
     * @param gameString Game Grid as a string
     * @throws StorageException
     */
    public void storeGame(String gameString) throws StorageException {
        try {
            Files.write(this.gameFilePath, gameString.getBytes());
        } catch (IOException writeError) {
            throw new StorageException("Unable to store game: " + writeError.getMessage());
        }
    }

    /**
     * Load Solution from solution.txt file.
     * Shows Location of Bombs, and numberOfAdjacentBombs for each box in grid.
     *
     * @return A List of Strings (which represents the gameboard).
     * @throws StorageException
     */
    public List<String> loadSolution() throws StorageException {
        try {
            List<String> solution = Files.readAllLines(this.solutionFilePath);
            if (solution.isEmpty()) {
                throw new StorageException("No Existing Solution");
            }
            return solution;
        } catch (IOException loadError) {
            throw new StorageException("No Existing Solution");
        }
    }

    /**
     * Load Gameplay from game.txt file.
     * For each box in grid, shows if the box has been flagged, revealed or not revealed.
     *
     * @return A List of Strings (which represents the gameboard).
     * @throws StorageException
     */
    public List<String> loadGame() throws StorageException {
        try {
            List<String> gameplay = Files.readAllLines(this.gameFilePath);
            if (gameplay.isEmpty()) {
                throw new StorageException("No Existing Gameplay");
            }
            return gameplay;
        } catch (IOException loadError) {
            throw new StorageException("No Existing Gameplay");
        }
    }

    /**
     * Load time from time.txt file.
     * Time is an integer value (represents seconds).
     *
     * @return Time (integer value).
     * @throws StorageException
     */
    public int loadTime() throws StorageException {
        try {
            List<String> lines = Files.readAllLines(this.timeFilePath);
            if (lines.isEmpty()) {
                throw new StorageException("No Existing Timing");
            }
            return Integer.parseInt(lines.getFirst().trim());
        } catch (IOException loadError) {
            throw new StorageException("No Existing Timing");
        }
    }

    /**
     * Stores time to time.txt file.
     * Time is an integer value (represents seconds).
     *
     * @param timeSeconds value to be stored to time.txt file.
     * @throws StorageException
     */
    public void storeTime(String timeSeconds) throws StorageException {
        try {
            Files.write(this.timeFilePath, timeSeconds.getBytes());
        } catch (IOException writeError) {
            throw new StorageException("Unable to store time: " + writeError.getMessage());
        }
    }

    /**
     * Stores hint count to hint.txt file.
     * Hint Count is an integer value of the number of hints given thus far. (Max 3)
     *
     * @param count value to be stored to hint.txt file.
     * @throws StorageException
     */
    public void storeHint(String count) throws StorageException {
        try {
            Files.write(this.hintFilePath, count.getBytes());
        } catch (IOException writeError) {
            throw new StorageException("Unable to store hint count: " + writeError.getMessage());
        }
    }

    /**
     * Load Hint Count from hint.txt file.
     * Hint Count is an integer value of the number of hints given thus far. (Max 3)
     *
     * @return integer value representing hint count.
     * @throws StorageException
     */
    public int loadHint() throws StorageException {
        try {
            List<String> lines = Files.readAllLines(this.hintFilePath);
            if (lines.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(lines.getFirst().trim());
        } catch (IOException loadError) {
            throw new StorageException("Unable to Load Hint File");
        }
    }

}
