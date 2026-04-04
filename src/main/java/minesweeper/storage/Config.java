package minesweeper.storage;

/**
 * Holds game configuration settings.
 * TODO: load/save from config.txt in future versions.
 */
public class Config {

    public static final boolean DEBUG_MODE = false;

    public static final int BOARD_SIZE_ROW = 10;
    public static final int BOARD_SIZE_COL = 10;
    public static final int MIN_BOMBS = 5;
    public static final int MAX_BOMBS = 20;
    public static final int MAX_HINTS = 3;
}
