package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;

/**
 * Class created by Parser when user input = 'Restart'.
 */
public class RestartCommand extends Command{

    /**
     * Restart Gameboard. Rewrites Solution.txt, Game.txt, Timer.txt.
     *
     * @param gameboard Gameboard Class.
     * @throws MinesweeperException
     */
    @Override
    public void execute(Gameboard gameboard, Ui ui) throws MinesweeperException {
        gameboard.restartGameboard();
        this.setResponse(ui.restartGame());
        this.setCommandType(CommandType.Restart);
    }

}
