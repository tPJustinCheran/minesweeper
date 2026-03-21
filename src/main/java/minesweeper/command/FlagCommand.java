package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

public class FlagCommand extends Command{

    private int boxNumber;

    public FlagCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer, Ui ui) throws MinesweeperException {
        gameboard.setFlagInGameboard(this.boxNumber, true);
        gameboard.storeGame();
        this.setResponse(ui.printGameboard());
        this.setCommandType(CommandType.Flag);
    }
}
