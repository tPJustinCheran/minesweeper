package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

public class UnflagCommand extends Command{

    private int boxNumber;

    public UnflagCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer, Ui ui) throws MinesweeperException {
        gameboard.setFlagInGameboard(this.boxNumber, false);
        gameboard.storeGame();
        this.setResponse(ui.printGameboard());
        this.setCommandType(CommandType.Unflag);
    }
}
