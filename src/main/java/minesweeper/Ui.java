package minesweeper;

import minesweeper.exception.MinesweeperException;

public class Ui {

    private Gameboard gameboard;
    private CustomTimer customTimer;

    public Ui (Gameboard gameboard, Storage storage, CustomTimer customTimer){
        this.gameboard = gameboard;
        this.customTimer = customTimer;
    }

    public String printGameboard() {
        return gameboard.toString();
    }

    public String exitProgram() {
        return "BYE!";
    }

    public String printHint(String hint) {
        return hint;
    }

    public String restartGame() {
        return "GAME RESTARTED";
    }

    public String printError(Exception error) {
        return error.getMessage();
    }

    public String printTime() {
        return this.customTimer.displayTimeMinSecs();
    }


}
