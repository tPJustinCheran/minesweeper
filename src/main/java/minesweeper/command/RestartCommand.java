package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

/**
 * Class created by Parser when user input = 'Restart'.
 */
public class RestartCommand extends Command{

    /**
     * Restart Gameboard. Rewrites Solution.txt, Game.txt, Timer.txt.
     *
     * @param gameboard Gameboard Class.
     * @param storage Storage Class.
     * @param customTimer Timer Class.
     * @throws MinesweeperException
     */
    public void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer) throws MinesweeperException {
        gameboard.restartGameboard(customTimer, storage);
        this.setCommandType(CommandType.Restart);
    }

}
