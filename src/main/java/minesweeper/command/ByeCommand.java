package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

/**
 * Class created by Parser when user input = 'Bye'.
 */
public class ByeCommand extends Command {

    /**
     * Close Program. Store Gameboard to game.txt file, and time to time.txt file.
     *
     * @param gameboard Gameboard Class.
     * @throws MinesweeperException
     */
    @Override
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        gameboard.closeProgram();
        this.setResponse(ui.exitProgram());
        this.setCommandType(CommandType.Bye);
    }

}
