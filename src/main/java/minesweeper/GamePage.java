package minesweeper;
 
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import minesweeper.exception.MinesweeperException;

 
public class GamePage {
 
    private final Stage primaryStage;
    private final Storage storage;
 
    private Gameboard gameboard;
    private CustomTimer customTimer;
 
    private Button[][] cellButtons = new Button[10][10];
 
    public GamePage(Stage primaryStage, Storage storage, boolean isContinue)
            throws MinesweeperException {
        this.primaryStage = primaryStage;
        this.storage = storage;
 
        customTimer = new CustomTimer(storage);
 
        if (isContinue) {
            gameboard = new Gameboard(customTimer, storage,
                    storage.loadSolution(), storage.loadGame());
        } else {
            gameboard = new Gameboard(customTimer, storage);
        }
    }
 
    public void show() {

        Button hintBtn = new Button("Hint");
        Button winBtn  = new Button("Win");

        hintBtn.setOnAction(e -> {
            // does nothing for now
        });

        winBtn.setOnAction(e -> {
            Alert win = new Alert(Alert.AlertType.INFORMATION);
            win.setTitle("You win!");
            win.setHeaderText(null);
            win.setContentText("You cleared the board!");
            win.initOwner(primaryStage);
            win.showAndWait();
        });

        HBox header = new HBox(10);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(new Label("DEBUG:"), hintBtn, winBtn);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setPadding(new Insets(10));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int boxNumber = row * 10 + col;
                Button btn = new Button(" ");
                btn.setMinSize(46, 46);

                btn.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        onCellLeftClick(boxNumber);
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        onCellRightClick(boxNumber);
                    }
                });

                cellButtons[row][col] = btn;
                grid.add(btn, col, row);
            }
        }

        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(grid);

        primaryStage.setScene(new Scene(layout, 510, 560));
        primaryStage.setTitle("Minesweeper");
        updateDisplay();
    }
 
    private void onCellLeftClick(int boxNumber) {
        try {
            gameboard.revealBoxInGameboard(boxNumber);
            updateDisplay();
            if (gameboard.checkWin()) {
                Alert win = new Alert(Alert.AlertType.INFORMATION);
                win.setTitle("You win!");
                win.setHeaderText(null);
                win.setContentText("You cleared the board!");
                win.initOwner(primaryStage);
                win.showAndWait();
            }
        } catch (MinesweeperException ex) {
            updateDisplay();
            Alert lose = new Alert(Alert.AlertType.ERROR);
            lose.setTitle("Game Over");
            lose.setHeaderText(null);
            lose.setContentText("You hit a bomb!");
            lose.initOwner(primaryStage);
            lose.showAndWait();
        }
    }
 
    private void onCellRightClick(int boxNumber) {
        try {
            int row = boxNumber / 10;
            int col = boxNumber % 10;
            boolean currentlyFlagged = gameboard.getBox(row, col).getFlag();
            gameboard.setFlagInGameboard(boxNumber, !currentlyFlagged);
            updateDisplay();
        } catch (MinesweeperException ex) {
            showAlert("Error", ex.getMessage());
        }
    }
 
    private void updateDisplay() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Box box = gameboard.getBox(row, col);
                Button btn = cellButtons[row][col];
 
                if (box.getFlag()) {
                    btn.setText("F");
                    btn.setDisable(false);
                } else if (box.getReveal()) {
                    int adj = box.getAdjacentBombs();
                    btn.setText(box.getBomb() ? "B" : (adj == 0 ? "" : String.valueOf(adj)));
                    btn.setDisable(true);
                } else {
                    btn.setText(" ");
                    btn.setDisable(false);
                }
            }
        }
    }
 
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(primaryStage);
        alert.showAndWait();
    }
}