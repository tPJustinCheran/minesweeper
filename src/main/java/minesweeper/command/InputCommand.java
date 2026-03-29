package minesweeper.command;

import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;
import minesweeper.logic.Gameboard;

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
