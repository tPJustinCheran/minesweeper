package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

public class InputCommand extends Command {
    private int boxNumber;

    public InputCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        gameboard.revealBoxInGameboard(this.boxNumber);
        this.setResponse(ui.printGameboard());
        this.setCommandType(CommandType.Input);
    }
}
