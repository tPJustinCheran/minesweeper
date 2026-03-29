package minesweeper.logic;

public class Box {
    private boolean flag;
    private int adjacentBombs;
    private boolean bomb;
    private boolean reveal;

    /**
     * Constructor Class for minesweeper.Box.
     *
     * @param flag boolean.
     * @param adjacentBombs integer.
     * @param bomb boolean.
     * @param reveal boolean.
     */
    public Box(boolean flag, int adjacentBombs, boolean bomb, boolean reveal) {
        this.flag = flag;
        this.adjacentBombs = adjacentBombs;
        this.bomb = bomb;
        this.reveal = reveal;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getAdjacentBombs() {
        return this.adjacentBombs;
    }

    public boolean getBomb() {
        return this.bomb;
    }

    public boolean getReveal() {
        return this.reveal;
    }

    public void setReveal(boolean reveal) {
        this.reveal = reveal;
    }

    private String printAdjacentBombs() {
        if (this.getAdjacentBombs() == 0) {
            return " ";
        } else {
            return Integer.toString(this.getAdjacentBombs());
        }
    }


    /**
     * Print Grid such that each box indicates: 1) Bomb ; 2) Number of Adjacent Bombs.
     *
     * @return String.
     */
    public String solutionDisplay() {
        if (this.getBomb()) {
            return "B";
        } else {
            return this.printAdjacentBombs();
        }
    }

    public String toString() {
        if (this.getFlag()) {
            return "F";
        } else if (this.getReveal()) {
            return this.printAdjacentBombs();
        }
        return "X";
    }
}
