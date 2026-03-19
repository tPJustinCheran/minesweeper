package minesweeper;

import minesweeper.command.Command;
import minesweeper.command.CommandType;
import minesweeper.exception.MinesweeperException;

import java.util.Scanner;

public class Minesweeper {

    private Storage storage;
    private Gameboard gameboard;
    private CustomTimer customTimer;
    private Parser parser;
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
            System.out.println("LOAD OLD GAMEBOARD");
        } catch (MinesweeperException noPrevRecordError) {
            gameboard = new Gameboard(customTimer, storage);
            System.out.println("LOAD NEW GAMEBOARD");
        }


        Scanner scanner = new Scanner(System.in);
        this.setCommandType(CommandType.Restart);
        while (this.getCommandType() != CommandType.Bye) {
            String input = scanner.nextLine();
            try {
                Command command = parser.parse(input);
                command.execute(gameboard, storage, customTimer);
                this.setCommandType(command.getCommandType());
            } catch (MinesweeperException error) {
                System.out.println(error.getMessage());
            }
        }
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public static void main(String[] args) throws MinesweeperException {
        new Minesweeper();
    }
}
