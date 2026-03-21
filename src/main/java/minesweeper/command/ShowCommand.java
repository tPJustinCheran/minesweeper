package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;
import minesweeper.exception.ParserException;
import minesweeper.exception.StorageException;

public class ShowCommand extends Command {

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer, Ui ui) throws MinesweeperException {
        this.setResponse(ui.printGameboard());
    }
}
