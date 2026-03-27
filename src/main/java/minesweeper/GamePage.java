package minesweeper;
 
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import minesweeper.exception.MinesweeperException;

import java.util.function.Consumer;


public class GamePage {
 
    private final Stage primaryStage;
    private final Storage storage;
 
    private final Gameboard gameboard;
    private final CustomTimer customTimer;
 
    private final Button[][] cellButtons = new Button[10][10];
    private Label timerLabel;
    private Timeline timerTimeline;
    private boolean isFirstClick = true;

    /**
    * Constructor for the GamePage class, taking account if it is a new or continued game.
    * 
    * @param primaryStage the stage to display the game page on
    * @param storage the storage object to load/save game data
    * @param isContinue whether to continue an existing game
    */
    public GamePage(Stage primaryStage, Storage storage, boolean isContinue)
            throws MinesweeperException {
        this.primaryStage = primaryStage;
        this.storage = storage;
 
        customTimer = new CustomTimer(storage);
 
        if (isContinue) {
            gameboard = new Gameboard(customTimer, storage,
                    storage.loadSolution(), storage.loadGame());
            isFirstClick = false;
        } else {
            gameboard = new Gameboard(customTimer, storage);
        }
    }
    
    /**
     * Displays the game page, setting up the UI and event handlers for the game interactions.
     * Handles left and right clicks on the game cells, updates the display accordingly.
     */
    public void show() {

        timerLabel = new Label(customTimer.displayTimeMinSecs());

        Button hintBtn = new Button("Hint");
        Button winBtn  = new Button("Win");

        hintBtn.setOnAction(e -> {
            // TODO: does nothing for now
        });

        winBtn.setOnAction(e -> {
            handleWin();
        });

        HBox header = new HBox(10);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(timerLabel, new Label("DEBUG:"), hintBtn, winBtn);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setPadding(new Insets(10));

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int boxNumber = row * 10 + col;
                Button btn = new Button(" ");
                btn.setMinSize(46, 46); // Set button size to fit the grid nicely

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

        timerTimeline = new Timeline(
            new KeyFrame(Duration.millis(50), e ->
                timerLabel.setText(customTimer.displayTimeMinSecs())
            )
        );
        timerTimeline.setCycleCount(Timeline.INDEFINITE);

        if (!isFirstClick) {
            timerTimeline.play();
        }

        primaryStage.setOnCloseRequest(e -> {
            try {
                gameboard.closeProgram();
            } catch (MinesweeperException ex) {
                System.out.println("Error saving: " + ex.getMessage());
            }
            timerTimeline.stop();
        });

        updateDisplay();
    }
 
    /**
     * Handles left click on a cell, revealing the box and checking for win/loss conditions.
     * 
     * @param boxNumber
     */
    private void onCellLeftClick(int boxNumber) {
        try {
            if (isFirstClick) {
                handleFirstClick(boxNumber);
                return;
            }
            Gameboard.MoveResult result = gameboard.revealBoxInGameboard(boxNumber);
            updateDisplay();
            switch (result) {
                case WIN  -> handleWin();
                case BOMB -> handleLose();
                case SAFE -> {}
            }
        } catch (MinesweeperException ex) {
            showAlert("Error", ex.getMessage());  // only flagged cell or storage errors
        }
    }

    /**
     * Handles the first click on the board. Regenerates the board until the
     * clicked cell is not a bomb, then starts the timer and reveals the cell.
     *
     * @param boxNumber
     * @throws MinesweeperException
     */
    private void handleFirstClick(int boxNumber) throws MinesweeperException {
        int row = boxNumber / 10;
        int col = boxNumber % 10;

        while (gameboard.getBox(row, col).getBomb()) {
            gameboard.restartGameboard();
            customTimer.stopTime();
        }

        isFirstClick = false;
        customTimer.restartTime();   // ← creates a fresh Timer, then starts it
        timerTimeline.play();

        gameboard.revealBoxInGameboard(boxNumber);
        updateDisplay();

        if (gameboard.checkWin()) {
            handleWin();
        }
    }

    /**
     * Handles the win condition. Stops the timer, shows the win dialog with
     * a name input field. On pressing Enter, saves the entry to the leaderboard
     * and returns to the game page.
     */
    private void handleWin() {
        timerTimeline.stop();
        customTimer.stopTime();
        long finalMillis = customTimer.getTimeMillis();
        String finalTime = customTimer.displayTimeMinSecs();
        timerLabel.setText(finalTime);

        new WinPage(primaryStage, storage, finalTime, finalMillis, () -> {
            try {
                gameboard.gameover();
                customTimer.stopTime();  // counteract restartTime() inside restartGameboard()
            } catch (MinesweeperException ex) {
                showAlert("Error", ex.getMessage());
            }
            isFirstClick = true;
            customTimer.zeroTime();
            timerLabel.setText(customTimer.displayTimeMinSecs());
//            updateDisplay();
        }).show();
    }

    /**
     * Handles the lose condition. Opens the LosePage, stops the timer, and shows the lose dialog.
     * gameover() in Gameboard already restarted the board.
     */
    private void handleLose() {
        timerTimeline.stop();
        customTimer.stopTime();
        String finalTime = customTimer.displayTimeMinSecs();
        timerLabel.setText(finalTime);

        gameboard.revealAllBombs();
        updateDisplay();

        new LosePage(primaryStage, finalTime, () -> {
            try {
                onPlayAgain();
            } catch (MinesweeperException error) {
                showAlert("Error", error.getMessage());
            }
        }, () -> {
            try {
                onHomeButton();
            } catch (MinesweeperException homeBtnError) {
                showAlert("Error: ", homeBtnError.getMessage());
            }
        }).show();
    }

    private void onPlayAgain() throws MinesweeperException {
        gameboard.gameover();
        isFirstClick = true;
        timerLabel.setText(customTimer.displayTimeMinSecs());
        gameboard.restartGameboard();
        updateDisplay();
    }

    private void onHomeButton() throws MinesweeperException {
        gameboard.gameover();
    }

    /**
     * Handles right click on a cell, toggling the flag state of the box.
     * 
     * @param boxNumber
     */
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

    /**
     * Utility method to show an alert dialog with a given title and message.
     * 
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(primaryStage);
        alert.showAndWait();
    }
}