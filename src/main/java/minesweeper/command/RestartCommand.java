package minesweeper.command;

import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;
import minesweeper.logic.Gameboard;

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
