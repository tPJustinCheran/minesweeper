package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

public class UnflagCommand extends Command{

    private int boxNumber;

    public UnflagCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        gameboard.setFlagInGameboard(this.boxNumber, false);
        gameboard.storeGame(storage);
        this.setCommandType(CommandType.Unflag);
        System.out.println(gameboard);
    }
}
