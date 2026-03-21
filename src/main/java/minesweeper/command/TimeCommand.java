package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

public class TimeCommand extends Command{

    @Override
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        this.setResponse(ui.printTime());
    }
}
