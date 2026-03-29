package minesweeper;

import java.util.Scanner;

import javafx.application.Application;
import minesweeper.command.Command;
import minesweeper.command.CommandType;
import minesweeper.exception.MinesweeperException;
import minesweeper.ui.HomePage;

/**
 * Entry point for the Minesweeper application.
 * Handles CLI interaction and launches the GUI.
 */
public class Minesweeper {

    private Storage storage;
    private Gameboard gameboard;
    private CustomTimer customTimer;
    private Parser parser;
    private Ui ui;
    private CommandType commandType;

    /**
     * Constructs the Minesweeper application and initializes core components.
     *
     * @throws MinesweeperException if initialization fails
     */
    public Minesweeper() throws MinesweeperException {
        String home = System.getProperty("user.dir");
        try {
            storage = new Storage(home);
            parser = new Parser();
            customTimer = new CustomTimer(storage);
        } catch (MinesweeperException error) {
            System.out.println(error.getMessage());
        }

        try {
            gameboard = new Gameboard(
                    customTimer,
                    storage,
                    storage.loadSolution(),
                    storage.loadGame()
            );
        } catch (MinesweeperException noPrevRecordError) {
            gameboard = new Gameboard(customTimer, storage);
        }

        ui = new Ui(gameboard, storage, customTimer);

        this.setCommandType(CommandType.Restart);

        try (Scanner scanner = new Scanner(System.in)) {
            while (this.getCommandType() != CommandType.Bye) {
                String input = scanner.nextLine();
                System.out.println(input);

                try {
                    Command command = parser.parse(input);
                    command.execute(gameboard, ui);

                    this.setCommandType(command.getCommandType());
                    System.out.println(command.getResponse());

                } catch (MinesweeperException error) {
                    System.out.println(ui.printError(error));
                }
            }
        }
    }

    public final void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public final CommandType getCommandType() {
        return this.commandType;
    }

    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}
