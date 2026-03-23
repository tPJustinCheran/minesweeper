package minesweeper;

import minesweeper.command.Command;
import minesweeper.command.CommandType;
import minesweeper.exception.MinesweeperException;

import java.util.Scanner;

import javafx.application.Application;

public class Minesweeper {

    private Storage storage;
    private Gameboard gameboard;
    private CustomTimer customTimer;
    private Parser parser;
    private Ui ui;
    private CommandType commandType;

    public Minesweeper() throws MinesweeperException {
        String HOME = System.getProperty("user.dir");
        try {
            storage = new Storage(HOME);
            parser = new Parser();
            customTimer = new CustomTimer(storage);
        } catch (MinesweeperException error) {
            System.out.println(error.getMessage());
        }
        try {
            gameboard = new Gameboard(customTimer, storage, storage.loadSolution(), storage.loadGame());
        } catch (MinesweeperException noPrevRecordError) {
            gameboard = new Gameboard(customTimer, storage);
        }
        ui = new Ui(gameboard, storage, customTimer);


        Scanner scanner = new Scanner(System.in);
        this.setCommandType(CommandType.Restart);
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

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}
