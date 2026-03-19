package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
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
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        System.out.println("BYE!");
        gameboard.storeGame(storage);  // store gameplay to game.txt file
        customTimer.pauseAndStopTime(storage); // store time to time.txt file
        this.setCommandType(CommandType.Bye);
    }

}
