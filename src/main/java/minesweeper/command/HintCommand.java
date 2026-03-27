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
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        int boxNumber = gameboard.giveHint();
        this.setCommandType(CommandType.Hint);
        this.setResponse(ui.printHint("Revealed cell " + boxNumber));
    }
}
