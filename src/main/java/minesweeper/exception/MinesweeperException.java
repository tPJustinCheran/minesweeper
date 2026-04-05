package minesweeper.exception;

/**
 * Custom exception class for Minesweeper application.
 * Used to indicate errors specific to game logic, storage, or other operations.
 */
public class MinesweeperException extends Exception {
    public MinesweeperException(String errorMessage) {
        super(errorMessage);
    }
}
