package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

public class FlagCommand extends Command{

    private int boxNumber;

    public FlagCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        gameboard.setFlagInGameboard(this.boxNumber, true);
        gameboard.storeGame(storage);
        this.setCommandType(CommandType.Flag);
        System.out.println(gameboard);
    }
}
