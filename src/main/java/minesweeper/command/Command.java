package minesweeper.command;

import minesweeper.CustomTimer;
import minesweeper.Gameboard;
import minesweeper.Storage;
import minesweeper.Ui;
import minesweeper.exception.MinesweeperException;
import minesweeper.exception.ParserException;
import minesweeper.exception.StorageException;

/**
 * Boiler Plate Code for all Command Classes.
 */
public abstract class Command {
    private CommandType commandType;
    private String response;

    /**
     * Executes the Command.
     *
     * @param gameboard Gameboard Class.
     * @throws MinesweeperException Error raised.
     * @throws ParserException Error raised.
     * @throws StorageException Error raised.
     */
    public abstract void execute(Gameboard gameboard, Ui ui)
            throws MinesweeperException, ParserException, StorageException;

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setResponse(String message) {
        this.response = message;
    }

    public String getResponse() {
        return this.response;
    }

}
