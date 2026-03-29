package minesweeper.command;

import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;
import minesweeper.logic.Gameboard;

public class TimeCommand extends Command{

    @Override
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        this.setResponse(ui.printTime());
    }
}
