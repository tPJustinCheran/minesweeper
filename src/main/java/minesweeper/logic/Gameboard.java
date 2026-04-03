package minesweeper.logic;

import java.util.*;

import minesweeper.Config;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;
import minesweeper.exception.StorageException;

/**
 * Represents the Minesweeper gameboard.
 * Manages the 10x10 grid of Box objects, bomb placements, flood fill,
 * hints, flags, win/lose detection, and game state persistence.
 */
public class Gameboard {

    private Box[][] gameboard;
    private int hintsRemaining;

    /**
     * Enum representing the result of a move on the gameboard.
     * SAFE: the cell was revealed and is not a bomb.
     * BOMB: the cell was a bomb.
     * WIN: all non-bomb cells have been revealed.
     */
    public enum MoveResult {
        SAFE, BOMB, WIN
    }

    /**
     * Constructor Class if no pre-existing data from hard disk.
     * Creates empty 10x10 grid with each cell containing a Box Object.
     * Randomly generates bombs and stores the grid in solution.txt.
     *
     * @throws MinesweeperException if initialisation fails.
     */
    public Gameboard(CustomTimer customTimer, Storage storage) throws MinesweeperException {
        this.hintsRemaining = Config.MAX_HINTS;
        restartGameboard(customTimer, storage);
    }

    /**
     * Constructor Class if there is pre-existing data from hard disk.
     * Loads Previous Gameplay and Solutions (from game.txt and solution.txt files).
     *
     * @param solutionGrid data from solution.txt.
     * @param gameGrid     data from game.txt.
     * @throws MinesweeperException Error Handling.
     */
    public Gameboard(CustomTimer customTimer, List<String> solutionGrid,
                     List<String> gameGrid, int remainingHints) throws MinesweeperException {
        this.hintsRemaining = remainingHints;
        reloadGameboard(solutionGrid, gameGrid, customTimer);
    }

    /**
     * Generates List of Neighbours.
     *
     * @param bombPlacement position of bomb to be checked.
     * @return list of neighbours.
     */
    public List<Integer> generateNeighbours(int bombPlacement) {
        int row = bombPlacement / Config.BOARD_SIZE_COL;
        int col = bombPlacement % Config.BOARD_SIZE_COL;
        List<Integer> neighbours = new ArrayList<>();

        for (int iterateRow = -1; iterateRow <= 1; iterateRow++) {
            for (int iterateCol = -1; iterateCol <= 1; iterateCol++) {
                if (iterateRow == 0 && iterateCol == 0) {
                    continue;
                }
                int currRow = row + iterateRow;
                int currCol = col + iterateCol;
                if (currRow >= 0 && currRow < Config.BOARD_SIZE_ROW && currCol >= 0 && currCol < Config.BOARD_SIZE_COL) {
                    int currPos = currRow * Config.BOARD_SIZE_COL + currCol;
                    neighbours.add(currPos);
                }
            }
        }
        return neighbours;
    }

    /**
     * Given the position of a bomb, checks if that position is valid.
     *
     * @param bombPlacement bomb target position.
     * @param bombPlacements list of current bomb positions.
     * @return boolean true if valid, false if not valid.
     */
    public boolean checkBombNeighbours(int bombPlacement, List<Integer> bombPlacements) {
        List<Integer> neighbours = generateNeighbours(bombPlacement);
        boolean isNotValid = new HashSet<>(bombPlacements).containsAll(neighbours);
        return !isNotValid;
    }

    /**
     * Randomly generates position of a bomb, and checks if bomb is valid.
     * If bomb is not valid, keep generating a new position until it is valid.
     *
     * @param bombPlacements list of existing bomb positions.
     * @return
     */
    public int generateSingleBomb(List<Integer> bombPlacements) {
        Random randomNum = new Random();
        int singleBombPlacement = randomNum.nextInt(Config.BOARD_SIZE_ROW * Config.BOARD_SIZE_COL);
        boolean isValidBomb = checkBombNeighbours(singleBombPlacement, bombPlacements);
        while (!isValidBomb) {
            // keep generating bomb until bomb placement is valid
            singleBombPlacement = randomNum.nextInt(Config.BOARD_SIZE_ROW * Config.BOARD_SIZE_COL);
            isValidBomb = checkBombNeighbours(singleBombPlacement, bombPlacements);
        }
        return singleBombPlacement;
    }

    /**
     * After bomb position is successfully generated, add bomb position to list of bomb positions.
     *
     * @return final list of bombs.
     */
    public List<Integer> generateBombPlacements() {
        List<Integer> bombPlacements = new ArrayList<>();
        Random randomNum = new Random();
        int numOfBombs = randomNum.nextInt(Config.MIN_BOMBS, Config.MAX_BOMBS);
        for (int i = 0; i < numOfBombs; i++) {
            int singleBomb = generateSingleBomb(bombPlacements);
            bombPlacements.add(singleBomb);
        }
        return bombPlacements;
    }

    /**
     * Given a position on the board, find out the number of adjacent bombs to that position.
     *
     * @param position       integer from 0 to 99
     * @param bombPlacements list of bomb placements
     * @return number of adjacent bombs
     */
    public int numberOfAdjacentBombs(int position, List<Integer> bombPlacements) {
        int count = 0;
        int row = position / Config.BOARD_SIZE_COL;
        int col = position % Config.BOARD_SIZE_COL;

        for (int iterateRow = -1; iterateRow <= 1; iterateRow++) {
            for (int iterateCol = -1; iterateCol <= 1; iterateCol++) {
                int currRow = row + iterateRow;
                int currCol = col + iterateCol;
                if (currRow >= 0 && currRow < Config.BOARD_SIZE_ROW && currCol >= 0 && currCol < Config.BOARD_SIZE_COL) {
                    int currPos = currRow * Config.BOARD_SIZE_COL + currCol;
                    if (bombPlacements.contains(currPos)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Restarts the Gameboard with a new random bomb layout.
     * Stores solution and game state to file, resets timer and hints.
     *
     * @throws MinesweeperException if storing game state fails.
     */
    public void restartGameboard(CustomTimer customTimer, Storage storage) throws MinesweeperException {
        this.gameboard = new Box[Config.BOARD_SIZE_ROW][Config.BOARD_SIZE_COL];
        List<Integer> bombPlacements = this.generateBombPlacements();
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                int currPtr = i * Config.BOARD_SIZE_COL + j;
                if (bombPlacements.contains(currPtr)) {
                    gameboard[i][j] = new Box(false, 0, true, false);
                } else {
                    gameboard[i][j] = new Box(
                            false,
                            this.numberOfAdjacentBombs(currPtr, bombPlacements),
                            false,
                            false
                    );
                }
            }
        }
        this.storeSolution(storage);
        this.storeGame(storage);
        customTimer.restartTime();
        customTimer.stopTime();
        customTimer.zeroTime();
        this.hintsRemaining = Config.MAX_HINTS;
        storage.storeHint(String.valueOf(Config.MAX_HINTS));
    }

    /**
     * Clears all saved game data so Continue is disabled on HomePage.
     *
     * @throws MinesweeperException if clearing the save files fails.
     */
    public void clearGameboard(Storage storage) throws MinesweeperException {
        storage.clearGame();
    }

    /**
     * Reloads the gameboard from saved solution and game grid data.
     * Resumes the timer from the stored time.
     *
     * @param solutionGrid list of strings representing the solution grid.
     * @param gameGrid     list of strings representing the game progress grid.
     * @throws MinesweeperException if loading fails.
     */
    public void reloadGameboard(List<String> solutionGrid,
            List<String> gameGrid, CustomTimer customTimer) throws MinesweeperException {
        this.gameboard = new Box[Config.BOARD_SIZE_ROW][Config.BOARD_SIZE_COL];
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            String currRowSoln = solutionGrid.get(i);
            String currRowGame = gameGrid.get(i);
            String[] partsSoln = currRowSoln.split("\\|");
            String[] partsGame = currRowGame.split("\\|");
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                if (Objects.equals(partsSoln[j], "B")) {
                    this.gameboard[i][j] = new Box(
                            checkFlagInGameboard(partsGame[j]),
                            0, true,
                            checkRevealInGameboard(partsGame[j])
                    );
                } else if (Objects.equals(partsSoln[j], " ")) {
                    this.gameboard[i][j] = new Box(
                            checkFlagInGameboard(partsGame[j]),
                            0, false,
                            checkRevealInGameboard(partsGame[j])
                    );
                } else {
                    this.gameboard[i][j] = new Box(
                            checkFlagInGameboard(partsGame[j]),
                            Integer.parseInt(partsSoln[j]),
                            false,
                            checkRevealInGameboard(partsGame[j])
                    );
                }
            }
        }
        customTimer.startTime();
    }

    /**
     * Store Solution (Bombs + adjacent bombs) to solution.txt file.
     * Called upon initialisation of new game board.
     *
     * @throws MinesweeperException if storing fails.
     */
    public void storeSolution(Storage storage) throws MinesweeperException {
        String totalStr = "";
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                totalStr = totalStr + this.gameboard[i][j].solutionDisplay() + "|";
            }
            totalStr += "\n";
        }
        storage.storeSolution(totalStr);
    }

    /**
     * Store Game Progress. Shows what has been revealed and what has not.
     * Called after every player move.
     *
     * @throws MinesweeperException if storing fails.
     */
    public void storeGame(Storage storage) throws MinesweeperException {
        String totalStr = "";
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                if (this.gameboard[i][j].getReveal()) {
                    totalStr = totalStr + "R|";
                } else if (this.gameboard[i][j].getFlag()) {
                    totalStr = totalStr + "F|";
                } else {
                    totalStr = totalStr + "N|";
                }
            }
            totalStr += "\n";
        }
        storage.storeGame(totalStr);
    }

    /**
     * Sets the flag state of a cell in the gameboard.
     *
     * @param boxNumber the cell index (0-99)
     * @param isFlag    true to flag, false to unflag
     * @throws MinesweeperException if storing game state fails.
     */
    public void setFlagInGameboard(int boxNumber, boolean isFlag, Storage storage) throws MinesweeperException {
        int row = boxNumber / Config.BOARD_SIZE_COL;
        int col = boxNumber % Config.BOARD_SIZE_COL;
        this.getBox(row, col).setFlag(isFlag);
        this.storeGame(storage);
    }

    /**
     * Reveals a random safe unrevealed unflagged cell on the board.
     * Maximum of 3 hints per game. Hint count is stored in hint.txt.
     *
     * @return the box number (0-99) of the revealed cell
     * @throws MinesweeperException if max hints reached or no safe cell found.
     */
    public int giveHint(Storage storage) throws MinesweeperException {
        if (hintsRemaining <= 0) {
            throw new MinesweeperException("No more hints remaining!");
        }

        List<Integer> unrevealedNonBombs = new ArrayList<>();
        for (int i = 0; i < Config.BOARD_SIZE_ROW * Config.BOARD_SIZE_COL; i++) {
            int row = i / Config.BOARD_SIZE_COL;
            int col = i % Config.BOARD_SIZE_COL;
            Box currBox = this.getBox(row, col);
            if (!currBox.getBomb() && !currBox.getReveal()) {
                unrevealedNonBombs.add(i);
            }
        }

        if (unrevealedNonBombs.isEmpty()) {
            throw new MinesweeperException("No more safe boxes to reveal!");
        }

        int randomHintBoxReveal = unrevealedNonBombs.get(
                new Random().nextInt(unrevealedNonBombs.size()));
        int hintRow = randomHintBoxReveal / 10;
        int hintCol = randomHintBoxReveal % 10;
        this.floodfill(hintRow, hintCol);

        hintsRemaining--;
        storage.storeHint(String.valueOf(hintsRemaining));
        this.storeGame(storage);

        return randomHintBoxReveal;
    }

    /**
     * Peeks at a cell's solution value without revealing it on the board.
     * Maximum of 3 shields per game.
     * TODO: currently untested and unimplemented in UI, uses same hint count as giveHint.
     *
     * @param boxNumber the cell to peek at (0-99)
     * @return the solution value of the cell — "B" for bomb, "0" for no adjacent bombs,
     *         or the number of adjacent bombs
     * @throws MinesweeperException if max shields reached.
     */
    public String giveShield(int boxNumber, Storage storage) throws MinesweeperException {
        if (hintsRemaining <= 0) {
            throw new MinesweeperException("No shields remaining!");
        }
        hintsRemaining--;
        storage.storeHint(String.valueOf(hintsRemaining));

        int row = boxNumber / Config.BOARD_SIZE_COL;
        int col = boxNumber % Config.BOARD_SIZE_COL;
        String boxValue = this.gameboard[row][col].solutionDisplay();

        return Objects.equals(boxValue, " ") ? "0" : boxValue;
    }

    /**
     * Reveals a box in the gameboard given a box number.
     * Returns a MoveResult indicating what happened.
     *
     * @param boxNumber integer from 0 to 99
     * @return MoveResult — BOMB if bomb, WIN if all safe cells revealed, SAFE otherwise
     * @throws MinesweeperException if the box is flagged or a storage error occurs.
     */
    public MoveResult revealBoxInGameboard(int boxNumber, Storage storage) throws MinesweeperException {
        int row = boxNumber / Config.BOARD_SIZE_COL;
        int col = boxNumber % Config.BOARD_SIZE_COL;
        if (this.getBox(row, col).getFlag()) {
            throw new MinesweeperException("Box has been flagged. Select a different Box!");
        }
        if (this.getBox(row, col).getBomb()) {
            return MoveResult.BOMB;
        }
        this.floodfill(row, col);
        this.storeGame(storage);
        if (this.checkWin()) {
            return MoveResult.WIN;
        }
        return MoveResult.SAFE;
    }

    /**
     * Checks if the player has won by verifying all non-bomb cells are revealed.
     *
     * @return true if all non-bomb cells are revealed, false otherwise.
     * @throws StorageException if a storage error occurs.
     */
    public boolean checkWin() { //throws StorageException {
        for (int i = 0; i < Config.BOARD_SIZE_ROW * Config.BOARD_SIZE_COL; i++) {
            int row = i / Config.BOARD_SIZE_COL;
            int col = i % Config.BOARD_SIZE_COL;
            Box currBox = this.getBox(row, col);
            if (!currBox.getBomb() && !currBox.getReveal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursively reveals all adjacent cells with no adjacent bombs (flood fill).
     * Also checks all 8 directions including diagonals.
     *
     * @param row the row of the cell to flood fill from.
     * @param col the column of the cell to flood fill from.
     */
    public void floodfill(int row, int col) {
        Box currBox = this.gameboard[row][col];
        if (!currBox.getReveal()) {
            currBox.setReveal(true);
            if (currBox.getAdjacentBombs() == 0) {
                if (row > 0) {
                    floodfill(row - 1, col);
                }
                if (row < Config.BOARD_SIZE_ROW - 1) {
                    floodfill(row + 1, col);
                }
                if (col > 0) {
                    floodfill(row, col - 1);
                }
                if (col < Config.BOARD_SIZE_COL - 1) {
                    floodfill(row, col + 1);
                }
                if (row > 0 && col > 0) {
                    floodfill(row - 1, col - 1);
                }
                if (row > 0 && col < Config.BOARD_SIZE_COL - 1) {
                    floodfill(row - 1, col + 1);
                }
                if (row < Config.BOARD_SIZE_ROW - 1 && col > 0) {
                    floodfill(row + 1, col - 1);
                }
                if (row < Config.BOARD_SIZE_ROW - 1 && col < Config.BOARD_SIZE_COL - 1) {
                    floodfill(row + 1, col + 1);
                }
            }
        }
    }

    /**
     * Reveals all bombs on the board. Called when the player loses,
     * so the player can see where all the bombs were.
     */
    public void revealAllBombs() {
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                if (this.getBox(i, j).getBomb()) {
                    this.getBox(i, j).setReveal(true);
                }
            }
        }
    }

    /**
     * Returns the number of bombs that have not been flagged.
     *
     * @return count of unflagged bombs
     */
    public int getUnflaggedBombCount() {
        int count = 0;
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                if (this.getBox(i, j).getBomb() && !this.getBox(i, j).getFlag()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Stores the current game state and pauses the timer when the player exits.
     *
     * @throws MinesweeperException if storing fails.
     */
    public void closeProgram(Storage storage, CustomTimer customTimer) throws MinesweeperException {
        this.storeGame(storage);
        customTimer.pauseAndStopTime(storage);
    }

    /**
     * Returns the Box object at the given row and column.
     *
     * @param row the row index (0-9)
     * @param col the column index (0-9)
     * @return the Box at the given position
     */
    public Box getBox(int row, int col) {
        return this.gameboard[row][col];
    }

    /**
     * Returns the number of hints remaining for the current game.
     *
     * @return number of hints remaining
     */
    public int getHintsRemaining() {
        return this.hintsRemaining;
    }

    /**
     * Returns a string representation of the current gameboard state.
     *
     * @return string representation of the gameboard
     */
    public String toString() {
        String totalStr = "";
        for (int i = 0; i < Config.BOARD_SIZE_ROW; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < Config.BOARD_SIZE_COL; j++) {
                totalStr = totalStr + this.gameboard[i][j].toString() + "|";
            }
        }
        return totalStr;
    }

    private boolean checkRevealInGameboard(String marker) {
        return Objects.equals(marker, "R");
    }

    private boolean checkFlagInGameboard(String marker) {
        return Objects.equals(marker, "F");
    }
}
