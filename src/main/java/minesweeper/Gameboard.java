package minesweeper;

import minesweeper.exception.MinesweeperException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Gameboard {

    private Box[][] gameboard;

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
     * @param customTimer Timer Class.
     * @param storage Storage Class. To store data to game.txt and solution.txt files.
     * @throws MinesweeperException Handle Errors.
     */
    public void restartGameboard(CustomTimer customTimer, Storage storage) throws MinesweeperException {
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
        this.storeSolution(storage);  // store solution to solution.txt file
        this.storeGame(storage);   // store existing gameplay to game.txt file
        customTimer.restartTime(); // start timer
    }

    /**
     * Constructor Class if no pre-existing data from hard disk
     * Creates empty 10 x 10 grid with each cell containing a minesweeper.Box Object.
     * Randomly generates bombs and stores the grid in solution.txt
     *
     */
    public Gameboard(CustomTimer customTimer, Storage storage) throws MinesweeperException {
        restartGameboard(customTimer, storage);
    }

    private boolean checkRevealInGameboard(String marker) {
        return Objects.equals(marker, "R");
    }

    private boolean checkFlagInGameboard(String marker) {
        return Objects.equals(marker, "F");
    }

    public void reloadGameboard(CustomTimer customTimer, Storage storage, List<String> solutionGrid, List<String> gameGrid) throws MinesweeperException {
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
        customTimer.startTime();
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
        reloadGameboard(customTimer, storage, solutionGrid, gameGrid);
    }


    /**
     * Store Solution (Bombs + adjacent bombs) to solution.txt file
     * Called upon initialisation of new game board.
     *
     * @param storage storage class
     * @throws MinesweeperException
     */
    public void storeSolution(Storage storage) throws MinesweeperException {
        String totalStr = "";
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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
     * @param storage storage class.
     * @throws MinesweeperException
     */
    public void storeGame(Storage storage) throws MinesweeperException {
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
        storage.storeGame(totalStr);
    }

    public void setFlagInGameboard(int boxNumber, boolean isFlag) {
        int row = boxNumber / 10;
        int col = boxNumber % 10;
        this.gameboard[row][col].setFlag(isFlag);
    }

    public String giveHint(int boxNumber) {
        int row = boxNumber / 10;
        int col = boxNumber % 10;
        String output = this.gameboard[row][col].solutionDisplay();
        if (Objects.equals(output, " ")) {
            return "0";
        } else {
            return this.gameboard[row][col].solutionDisplay();
        }
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

    public String printForChecking() {
        String totalStr = "FLAG: \n";
        for (int i = 0; i < 10; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].getFlag() + "|";
            }
        }
        totalStr = totalStr + "\nBOMB: \n";
        for (int i = 0; i < 10; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].getBomb() + "|";
            }
        }
        totalStr = totalStr + "\nADJ BOMB: \n";
        for (int i = 0; i < 10; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].getAdjacentBombs() + "|";
            }
        }
        totalStr = totalStr + "\nREVEAL: \n";
        for (int i = 0; i < 10; i++) {
            totalStr = totalStr + "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + this.gameboard[i][j].getReveal() + "|";
            }
        }
        return totalStr;
    }
}
