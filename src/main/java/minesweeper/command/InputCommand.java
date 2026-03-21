package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

public class InputCommand extends Command {
    private int boxNumber;

    public InputCommand(int boxNumber) {
        this.boxNumber = boxNumber;
    }

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        gameboard.revealBoxInGameboard(this.boxNumber, customTimer, storage);
        this.setCommandType(CommandType.Input);
        System.out.println(gameboard);
    }

}
