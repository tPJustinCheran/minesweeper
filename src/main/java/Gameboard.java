import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Gameboard {

    private Box[][] gameboard = new Box[10][10];

    /**
     * Randomly generate up to 20 bomb placements
     *
     * @return list of bomb positions
     */
    public List<Integer> generateBombPlacements() {
        List<Integer> bombPlacements = new ArrayList<>();
        Random randomNum = new Random();
        int numOfBombs = randomNum.nextInt(20);
        for (int i=0; i < numOfBombs; i++) {
            bombPlacements.add(randomNum.nextInt(100));
        }
        return bombPlacements;
    }

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
     * Constructor Class if no pre-existing data from hard disk
     * Creates empty 10 x 10 grid with each cell containing a Box Object.
     *
     */
    public Gameboard() {
        List<Integer> bombPlacements = this.generateBombPlacements();
        System.out.println(bombPlacements);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int currPtr = i * 10 + j;
                if (bombPlacements.contains(currPtr)) {
                    gameboard[i][j] = new Box(this.numberOfAdjacentBombs(currPtr, bombPlacements), true, false);
                } else {
                    gameboard[i][j] = new Box(this.numberOfAdjacentBombs(currPtr, bombPlacements), false, false);
                }
            }
        }
    }

    @Override
    public String toString() {
        String totalStr = "";
        for (int i = 0; i < 10; i++) {
            totalStr += "\n";
            for (int j = 0; j < 10; j++) {
                totalStr = totalStr + gameboard[i][j].toString() + "|";
            }
        }
        return totalStr;
    }
}
