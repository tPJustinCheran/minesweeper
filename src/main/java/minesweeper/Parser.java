package minesweeper;

import minesweeper.command.*;
import minesweeper.exception.ParserException;

public class Parser {
    private int boxNumber;

    public int extractBoxNumber(String userInput) throws ParserException {
        userInput = userInput.trim();
        int spacePos = userInput.indexOf(" ");
        if (spacePos == -1) {
            throw new ParserException("Command must have an integer index behind");
        } else {
            String argument = userInput.substring(spacePos + 1);
            return Integer.parseInt(argument);
        }
    }

    public Command parse(String userInput) throws ParserException {
        userInput = userInput.trim().toLowerCase();
        System.out.println(userInput);
        if (userInput.contains("restart")) {
            return new RestartCommand();
        } else if (userInput.contains("bye") || userInput.contains("end")) {
            return new ByeCommand();
        } else if (userInput.contains("flag")) {
            boxNumber = extractBoxNumber(userInput);
            return new FlagCommand(boxNumber);
        } else if (userInput.contains("unflag")) {
            boxNumber = extractBoxNumber(userInput);
            return new UnflagCommand(boxNumber);
        } else if (userInput.contains("hint")) {
            boxNumber = extractBoxNumber(userInput);
            return new HintCommand(boxNumber);
        } else if (userInput.contains("input")) {  // check a single box
            boxNumber = extractBoxNumber(userInput);
            return new InputCommand(boxNumber);
        } else if (userInput.contains("show")) {
            return new ShowCommand();
        } else {
            throw new ParserException("Invalid Command");
        }
    }
}
