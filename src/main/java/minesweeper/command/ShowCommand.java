package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;
import minesweeper.exception.ParserException;
import minesweeper.exception.StorageException;

public class ShowCommand extends Command {

    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        System.out.println(gameboard);
    }
}
