import java.util.List;

public class Minesweeper {

    private Storage storage;
    private Gameboard gameboard;

    public Minesweeper() throws MinesweeperException {
        String HOME = System.getProperty("user.dir");
        try {
            storage = new Storage(HOME);
        } catch (MinesweeperException error) {
            System.out.println(error.getMessage());
        }
        try {
            gameboard = new Gameboard(storage, storage.loadSolution(), storage.loadGame());
        } catch (MinesweeperException noPrevRecordError) {
            gameboard = new Gameboard(storage);
        }
        System.out.println(gameboard.printForChecking());
    }

    public static void main(String[] args) throws MinesweeperException {
        new Minesweeper();
    }
}
