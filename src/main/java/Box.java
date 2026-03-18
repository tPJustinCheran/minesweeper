public class Box {
    private boolean flag;
    private int adjacentBombs;
    private boolean bomb;
    private boolean reveal;

    public Box (int adjacentBombs, boolean bomb, boolean reveal) {
        this.flag = false;
        this.adjacentBombs = adjacentBombs;
        this.bomb = bomb;
        this.reveal = reveal;
    }

    private boolean getFlag(){
        return this.flag;
    }

    private int getAdjacentBombs(){
        return this.adjacentBombs;
    }

    private boolean getBomb(){
        return this.bomb;
    }

    private boolean getReveal(){
        return this.reveal;
    }

    private String printAdjacentBombs(){
        if (this.getAdjacentBombs() == 0) {
            return " ";
        } else {
            return Integer.toString(this.getAdjacentBombs());
        }
    }

    @Override
    public String toString() {
        if (this.getFlag()) {
            return "F";
        } else if (this.getBomb()) {
            return "B";
        } else if (this.getReveal()) {
            return this.printAdjacentBombs();
        }
        return " ";
    }
}
