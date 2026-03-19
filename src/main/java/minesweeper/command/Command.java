package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;
import minesweeper.exception.ParserException;
import minesweeper.exception.StorageException;

/**
 * Boiler Plate Code for all Command Classes.
 */
public abstract class Command {
    private CommandType commandType;

    /**
     * Executes the Command.
     *
     * @param gameboard Gameboard Class.
     * @param storage Storage Class.
     * @param customTimer Timer Class.
     * @throws MinesweeperException Error raised.
     * @throws ParserException Error raised.
     * @throws StorageException Error raised.
     */
    public abstract void execute(Gameboard gameboard, Storage storage, CustomTimer customTimer)
            throws MinesweeperException, ParserException, StorageException;

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

}
