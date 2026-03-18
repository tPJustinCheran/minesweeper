import java.util.List;

public class Minesweeper {

    private Storage storage;
    private Gameboard gameboard;
    private CustomTimer customTimer;

    public Minesweeper() throws MinesweeperException {
        String HOME = System.getProperty("user.dir");
        try {
            storage = new Storage(HOME);
        } catch (MinesweeperException error) {
            System.out.println(error.getMessage());
        }
        try {
            customTimer = new CustomTimer(storage);
        } catch (MinesweeperException timerError) {
            System.out.println(timerError.getMessage());
        }
        try {
            gameboard = new Gameboard(customTimer, storage, storage.loadSolution(), storage.loadGame());
        } catch (MinesweeperException noPrevRecordError) {
            gameboard = new Gameboard(customTimer, storage);
        }
        System.out.println(gameboard.printForChecking());
        System.out.println(customTimer.displayTimeMinSecs());
        customTimer.pauseAndStopTime(storage);
    }

    public static void main(String[] args) throws MinesweeperException {
        new Minesweeper();
    }
}
