package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

public class HintCommand extends Command {
    private int boxNumber;

    public HintCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        System.out.println(gameboard.giveHint(this.boxNumber));
        this.setCommandType(CommandType.Hint);
    }
}
