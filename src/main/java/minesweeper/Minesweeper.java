package minesweeper;

import javafx.application.Application;
import minesweeper.ui.HomePage;

/**
 * Entry point for the Minesweeper application.
 * Handles CLI interaction and launches the GUI.
 */
public class Minesweeper {

    public static void main(String[] args) {
        Application.launch(HomePage.class, args);
    }
}
