package minesweeper;

import minesweeper.exception.MinesweeperException;
import minesweeper.exception.StorageException;

import java.util.*;

public class Gameboard {

    private Box[][] gameboard;
    private CustomTimer customTimer;
    private Storage storage;
    private int hintCount;

    /**
     * Randomly generate up to 20 bomb placements
     *
     * @return list of bomb positions
     */
    public List<Integer> generateBombPlacements() {
        List<Integer> bombPlacements = new ArrayList<>();
        Random randomNum = new Random();
        int numOfBombs = randomNum.nextInt(5,20);
        for (int i=0; i < numOfBombs; i++) {
            bombPlacements.add(randomNum.nextInt(100));
        }
        return bombPlacements;
    }

    /**
     * Given a position on the board, find out the number of adjacent bombs to that position.
     *
     * @param position integer from 0 to 99
     * @param bombPlacements list of bombplacements
     * @return
     */
    public int numberOfAdjacentBombs(int position, List<Integer> bombPlacements) {
        int count = 0;
        int row = position / 10;
        int col = position % 10;

        for (int iterateRow = -1; iterateRow <= 1; iterateRow++){
            for (int iterateCol = -1; iterateCol <= 1; iterateCol++){
                int currRow = row + iterateRow;
                int currCol = col + iterateCol;
                if (currRow >= 0 && currRow < 10 && currCol >= 0 && currCol < 10) {
                    int currPos = currRow * 10 + currCol;
                    if (bombPlacements.contains(currPos)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Restart a Gameboard.
     *
     * @throws MinesweeperException Handle Errors.
     */
    public void restartGameboard() throws MinesweeperException {
        this.gameboard = new Box[10][10];
        List<Integer> bombPlacements = this.generateBombPlacements();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int currPtr = i * 10 + j;
                if (bombPlacements.contains(currPtr)) {
                    gameboard[i][j] = new Box(false, 0, true, false);
                } else {
                    gameboard[i][j] = new Box(false, this.numberOfAdjacentBombs(currPtr, bombPlacements), false, false);
                }
            }
        }
        this.storeSolution();  // store solution to solution.txt file
        this.storeGame();   // store existing gameplay to game.txt file
        this.customTimer.restartTime(); // start timer
    }

    /**
     * Constructor Class if no pre-existing data from hard disk
     * Creates empty 10 x 10 grid with each cell containing a minesweeper.Box Object.
     * Randomly generates bombs and stores the grid in solution.txt
     *
     * @param customTimer Timer Class.
     * @param storage Storage Class. To store data to game.txt and solution.txt files.
     */
    public Gameboard(CustomTimer customTimer, Storage storage) throws MinesweeperException {
        this.customTimer = customTimer;
        this.storage = storage;
        restartGameboard();
    }

    private boolean checkRevealInGameboard(String marker) {
        return Objects.equals(marker, "R");
    }

    private boolean checkFlagInGameboard(String marker) {
        return Objects.equals(marker, "F");
    }

    public void reloadGameboard(List<String> solutionGrid, List<String> gameGrid) throws MinesweeperException {
        this.gameboard = new Box[10][10];
        for (int i = 0; i < 10; i++) {
            String currRowSoln = solutionGrid.get(i);
            String currRowGame = gameGrid.get(i);
            String[] partsSoln = currRowSoln.split("\\|");
            String[] partsGame = currRowGame.split("\\|");
            for (int j = 0; j < 10; j++) {
                if (Objects.equals(partsSoln[j], "B")) {
                    this.gameboard[i][j] = new Box(checkFlagInGameboard(partsGame[j]), 0, true, checkRevealInGameboard(partsGame[j]));
                } else if (Objects.equals(partsSoln[j], " ")) {
                    this.gameboard[i][j] = new Box(checkFlagInGameboard(partsGame[j]), 0, false, checkRevealInGameboard(partsGame[j]));
                } else {
                    this.gameboard[i][j] = new Box(checkFlagInGameboard(partsGame[j]), Integer.parseInt(partsSoln[j]), false, checkRevealInGameboard(partsGame[j]));
                }
            }
        }
        this.customTimer.startTime();
    }

    /**
     * Constructor Class if there is pre-existing data from hard disk.
     * Loads Previous Gameplay and Solutions (from game.txt and solution.txt files).
     *
     * @param customTimer Timer class.
     * @param storage Storage Class.
     * @param solutionGrid data from solution.txt.
     * @param gameGrid data from game.txt.
     * @throws MinesweeperException Error Handling.
     */
    public Gameboard(CustomTimer customTimer, Storage storage, List<String> solutionGrid, List<String> gameGrid) throws MinesweeperException {
        this.customTimer = customTimer;
        this.storage = storage;
        reloadGameboard(solutionGrid, gameGrid);
    }


    /**
     * Store Solution (Bombs + adjacent bombs) to solution.txt file
     * Called upon initialisation of new game board.
     *
     * @throws MinesweeperException
     */
    public void storeSolution() throws MinesweeperException {
        String totalStr = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].solutionDisplay() + "|";
            }
            totalStr += "\n";
        }
        this.storage.storeSolution(totalStr);
    }

    /**
     * Store Game Progress. Shows what has been revealed and what has not.
     * Called after every player move.
     *
     * @throws MinesweeperException
     */
    public void storeGame() throws MinesweeperException {
        String totalStr = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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
        this.storage.storeGame(totalStr);
    }

    public void setFlagInGameboard(int boxNumber, boolean isFlag) throws MinesweeperException {
        int row = boxNumber / 10;
        int col = boxNumber % 10;
        System.out.println(this.gameboard[row][col].getFlag());
        System.out.println(isFlag);
        this.gameboard[row][col].setFlag(isFlag);
        this.storeGame();
    }

    public String giveHint(int boxNumber) throws MinesweeperException {
        if (this.hintCount < 3) {
            this.hintCount++;
            int row = boxNumber / 10;
            int col = boxNumber % 10;
            String boxValue = this.gameboard[row][col].solutionDisplay();
            if (Objects.equals(boxValue, " ")) {
                return "0";
            } else {
                return this.gameboard[row][col].solutionDisplay();
            }
        } else {
            throw new MinesweeperException("Max 3 Hints!");
        }
    }

    public void revealBoxInGameboard(int boxNumber) throws MinesweeperException {
        int row = boxNumber / 10;
        int col = boxNumber % 10;
        if (this.gameboard[row][col].getFlag()) {
            throw new MinesweeperException("Box has been flagged. Select a different Box!");
        } else {
            String boxValue = this.gameboard[row][col].solutionDisplay();
            if (Objects.equals(boxValue, "B")) {
                this.gameover();
            } else {
                this.floodfill(row, col);
                this.storeGame();
                if (this.checkWin()) {
                    System.out.println("WIN");
                    this.restartGameboard();
                    // WIN FUNCTION -- leaderboard + restart game
                }
            }
        }
    }

    public boolean checkWin() throws StorageException {
        for (int i = 0; i < 100; i ++) {
            int row = i / 10;
            int col = i % 10;
            Box currBox = this.gameboard[row][col];
            if (!currBox.getBomb() && !currBox.getReveal()) {
                return false;
            }
        }
        return true;
    }

    public void floodfill(int row, int col) {
        Box currBox = this.gameboard[row][col];
        if (!currBox.getReveal()) {
            currBox.setReveal(true);
            if (currBox.getAdjacentBombs() == 0) {
                if (row > 0) {
                    floodfill(row - 1, col);
                }
                if (row < 9) {
                    floodfill(row + 1, col);
                }
                if (col > 0) {
                    floodfill(row, col - 1);
                }
                if (col < 9) {
                    floodfill(row, col + 1);
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }

    public void gameover() throws MinesweeperException {
        this.restartGameboard();
        throw new MinesweeperException("BOMB FOUND. GAME OVER");
    }

    public void closeProgram() throws MinesweeperException {
        this.storeGame();  // store gameplay to game.txt file
        this.customTimer.pauseAndStopTime(storage); // store time to time.txt file
    }

    public String toString() {
        String totalStr = "";
        for (int i = 0; i < 10; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].toString() + "|";
            }
        }
        return totalStr;
    }

}
