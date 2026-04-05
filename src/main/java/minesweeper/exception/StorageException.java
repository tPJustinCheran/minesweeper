package minesweeper.exception;

/**
 * Custom exception class for storage
 * Used to indicate errors specific to storage operations, such as file read/write issues.
 */
public class StorageException extends MinesweeperException {
    public StorageException(String errorMessage) {
        super(errorMessage);
    }
}
