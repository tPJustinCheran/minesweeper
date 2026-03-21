package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

public class HintCommand extends Command {
    private int boxNumber;

    public HintCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer, Ui ui) throws MinesweeperException {
        String hint = gameboard.giveHint(this.boxNumber);
        this.setCommandType(CommandType.Hint);
        this.setResponse(ui.printHint(hint));
    }
}
