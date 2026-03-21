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
     * @param storage Storage Class.
     * @param customTimer Timer Class.
     * @throws MinesweeperException
     */
    @Override
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer, Ui ui) throws MinesweeperException {
        gameboard.storeGame();  // store gameplay to game.txt file
        customTimer.pauseAndStopTime(storage); // store time to time.txt file
        this.setResponse(ui.exitProgram());
        this.setCommandType(CommandType.Bye);
    }

}
