package minesweeper.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import minesweeper.Config;
import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;
import minesweeper.logic.Box;
import minesweeper.logic.CustomTimer;
import minesweeper.logic.Gameboard;

/**
 * GamePage represents the main game screen.
 * Displays the 10x10 Minesweeper grid, header controls, and handles
 * all player interactions including cell clicks, flagging, hints, win and lose conditions.
 */
public class GamePage {

    private final Stage primaryStage;
    private final Storage storage;

    private final Gameboard gameboard;
    private final CustomTimer customTimer;

    private final ResourceManager resourceManager = new ResourceManager();

    private final Button[][] cellButtons = new Button[Config.BOARD_SIZE_ROW][Config.BOARD_SIZE_COL];
    private Button hintBtn;
    private Label timerLabel;
    private Timeline timerTimeline;
    private boolean isFirstClick = true;

    private javafx.scene.image.Image bombIcon;
    private javafx.scene.image.Image flagIcon;

    /**
     * Constructor for the GamePage class, taking account if it is a new or continued game.
     *
     * @param primaryStage the stage to display the game page on
     * @param storage the storage object to load/save game data
     * @param isContinue whether to continue an existing game
     * @throws MinesweeperException if loading or creating the gameboard fails
     */
    public GamePage(Stage primaryStage, Storage storage, boolean isContinue)
            throws MinesweeperException {
        this.primaryStage = primaryStage;
        this.storage = storage;

        customTimer = new CustomTimer(storage);

        if (isContinue) {
            gameboard = new Gameboard(customTimer, storage.loadSolution(), storage.loadGame(), storage.loadHint());
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

        Image appIcon = resourceManager.loadAppIcon();
        if (appIcon != null) {
            primaryStage.getIcons().add(appIcon);
        }

        bombIcon = resourceManager.loadBombIcon();
        flagIcon = resourceManager.loadFlagIcon();
        timerLabel = new Label(customTimer.displayTimeMinSecs());
        timerLabel.setStyle(
            "-fx-font-size: 16px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: #1a1a2e;"
        );

        Button homeBtn = new Button("\u2190 Home");
        homeBtn.setStyle(
            "-fx-font-size: 13px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-text-fill: white;"
                    + "-fx-background-color: #607D8B;"
                    + "-fx-background-radius: 6;"
                    + "-fx-padding: 6 14;"
                    + "-fx-cursor: hand;"
        );

        homeBtn.setOnMouseEntered(e -> homeBtn.setStyle(
                homeBtn.getStyle().replace("#607D8B", "#37474F")));
        homeBtn.setOnMouseExited(e -> homeBtn.setStyle(
                homeBtn.getStyle().replace("#37474F", "#607D8B")));

        hintBtn = new Button("Hint (" + gameboard.getHintsRemaining() + " left)");
        hintBtn.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-color: #F57F17;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 6 14;"
                        + "-fx-cursor: hand;"
        );

        hintBtn.setOnMouseEntered(e -> hintBtn.setStyle(
                hintBtn.getStyle().replace("#F57F17", "#E65100")));
        hintBtn.setOnMouseExited(e -> hintBtn.setStyle(
                hintBtn.getStyle().replace("#E65100", "#F57F17")));

        homeBtn.setOnAction(e -> {
            try {
                if (isFirstClick) {
                    gameboard.clearGameboard(this.storage);
                } else {
                    gameboard.closeProgram(this.storage, this.customTimer);
                }
            } catch (MinesweeperException ex) {
                System.out.println("Error saving: " + ex.getMessage());
            }
            timerTimeline.stop();
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                System.out.println("Error returning to home: " + ex.getMessage());
            }
        });

        hintBtn.setOnAction(e -> {
            try {
                if (isFirstClick) {
                    isFirstClick = false;
                    customTimer.restartTime();
                    timerTimeline.play();
                }
                gameboard.giveHint(this.storage);
                updateDisplay();
                if (gameboard.checkWin()) {
                    handleWin();
                }
            } catch (MinesweeperException ex) {
                showAlert("Hint", ex.getMessage());
            }
        });

        HBox leftHeader = new HBox(10);
        leftHeader.setAlignment(Pos.CENTER_LEFT);
        leftHeader.getChildren().add(homeBtn);

        HBox rightHeader = new HBox(10);
        rightHeader.setAlignment(Pos.CENTER_RIGHT);
        rightHeader.getChildren().add(hintBtn);

        if (Config.DEBUG_MODE) {
            Button winBtn = new Button("Win");
            winBtn.setOnAction(e -> handleWin());
            rightHeader.getChildren().add(winBtn);
        }

        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER);

        HBox spacerLeft = new HBox();
        HBox spacerRight = new HBox();
        javafx.scene.layout.HBox.setHgrow(spacerLeft, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.HBox.setHgrow(spacerRight, javafx.scene.layout.Priority.ALWAYS);
        header.getChildren().addAll(leftHeader, spacerLeft, timerLabel, spacerRight, rightHeader);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(3);
        grid.setVgap(3);
        grid.setPadding(new Insets(10));

        for (int row = 0; row < Config.BOARD_SIZE_ROW; row++) {
            for (int col = 0; col < Config.BOARD_SIZE_COL; col++) {
                final int boxNumber = row * Config.BOARD_SIZE_COL + col;
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
                if (isFirstClick) {
                    gameboard.clearGameboard(this.storage);
                } else {
                    gameboard.closeProgram(this.storage, this.customTimer);
                }
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
     * @param boxNumber the index of the cell clicked (0-99)
     */
    private void onCellLeftClick(int boxNumber) {
        try {
            if (isFirstClick) {
                handleFirstClick(boxNumber);
                return;
            }
            Gameboard.MoveResult result = gameboard.revealBoxInGameboard(boxNumber, this.storage);
            updateDisplay();
            switch (result) {
            case WIN -> handleWin();
            case BOMB -> handleLose();
            default -> {
            }
            }
        } catch (MinesweeperException ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    /**
     * Handles the first click on the board. Regenerates the board until the
     * clicked cell is not a bomb, then starts the timer and reveals the cell.
     *
     * @param boxNumber the index of the first cell clicked (0-99)
     * @throws MinesweeperException if revealing the cell or restarting the board fails
     */
    private void handleFirstClick(int boxNumber) throws MinesweeperException {
        int row = boxNumber / Config.BOARD_SIZE_COL;
        int col = boxNumber % Config.BOARD_SIZE_COL;

        while (gameboard.getBox(row, col).getBomb()) {
            gameboard.restartGameboard(this.customTimer, this.storage);
            customTimer.stopTime();
        }

        isFirstClick = false;
        customTimer.restartTime();
        timerTimeline.play();

        Gameboard.MoveResult result = gameboard.revealBoxInGameboard(boxNumber, this.storage);
        updateDisplay();
        switch (result) {
        case WIN -> handleWin();
        case BOMB -> handleLose();
        default -> {
        }
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
                gameboard.restartGameboard(this.customTimer, this.storage);
                customTimer.stopTime();
                gameboard.clearGameboard(this.storage);
            } catch (MinesweeperException ex) {
                showAlert("Error", ex.getMessage());
            }
            isFirstClick = true;
            customTimer.zeroTime();
            timerLabel.setText(customTimer.displayTimeMinSecs());
            updateDisplay();
        }).show();
    }

    /**
     * Handles the lose condition. Opens the LosePage, stops the timer, and shows the lose dialog.
     * Reveals all bomb locations before showing the dialog.
     */
    private void handleLose() {
        timerTimeline.stop();
        customTimer.stopTime();
        String finalTime = customTimer.displayTimeMinSecs();
        timerLabel.setText(finalTime);

        gameboard.revealAllBombs();
        updateDisplay();

        new LosePage(primaryStage, finalTime, gameboard.getUnflaggedBombCount(), () -> {
            try {
                onPlayAgain();
            } catch (MinesweeperException error) {
                showAlert("Error", error.getMessage());
            }
        }, () -> {
            try {
                onHomeButton();
            } catch (MinesweeperException homeBtnError) {
                showAlert("Error", homeBtnError.getMessage());
            }
        }).show();
    }

    /**
     * Resets the board and timer for a new game after losing.
     *
     * @throws MinesweeperException if restarting the gameboard fails
     */
    private void onPlayAgain() throws MinesweeperException {
        gameboard.restartGameboard(this.customTimer, this.storage);
        customTimer.stopTime();
        isFirstClick = true;
        timerLabel.setText(customTimer.displayTimeMinSecs());
        updateDisplay();
    }

    /**
     * Clears the save and stops the timer when navigating home after losing.
     *
     * @throws MinesweeperException if clearing the gameboard fails
     */
    private void onHomeButton() throws MinesweeperException {
        gameboard.clearGameboard(this.storage);
        customTimer.stopTime();
        customTimer.zeroTime();
        timerTimeline.stop();
    }

    /**
     * Handles right click on a cell, toggling the flag state of the box.
     *
     * @param boxNumber the index of the cell right-clicked (0-99)
     */
    private void onCellRightClick(int boxNumber) {
        try {
            int row = boxNumber / Config.BOARD_SIZE_COL;
            int col = boxNumber % Config.BOARD_SIZE_COL;
            boolean currentlyFlagged = gameboard.getBox(row, col).getFlag();
            gameboard.setFlagInGameboard(boxNumber, !currentlyFlagged, this.storage);
            updateDisplay();
        } catch (MinesweeperException ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    /**
     * Updates all cell buttons to reflect the current gameboard state.
     * Also updates the hint button label with remaining hints.
     */
    private void updateDisplay() {
        for (int row = 0; row < Config.BOARD_SIZE_ROW; row++) {
            for (int col = 0; col < Config.BOARD_SIZE_COL; col++) {
                Box box = gameboard.getBox(row, col);
                Button btn = cellButtons[row][col];

                if (box.getFlag()) {
                    setButtonIcon(btn, flagIcon, "F");
                    btn.setDisable(false);
                } else if (box.getReveal()) {
                    btn.setDisable(true);
                    if (box.getBomb()) {
                        setButtonIcon(btn, bombIcon, "B");
                    } else {
                        int adj = box.getAdjacentBombs();
                        btn.setGraphic(null);
                        btn.setText(adj == 0 ? "" : String.valueOf(adj));
                        btn.setStyle(getNumberStyle(adj));
                    }
                } else {
                    btn.setGraphic(null);
                    btn.setText(" ");
                    btn.setDisable(false);
                    btn.setStyle(
                            "-fx-background-color: #bdbdbd;"
                                    + "-fx-border-color: #9e9e9e;"
                                    + "-fx-border-width: 1;"
                                    + "-fx-background-radius: 3;"
                                    + "-fx-border-radius: 3;"
                                    + "-fx-cursor: hand;"
                    );
                }
            }
        }
        hintBtn.setText("Hint (" + gameboard.getHintsRemaining() + " left)");
    }

    /**
     * Returns the style string for a revealed cell based on adjacent bomb count.
     *
     * @param adj number of adjacent bombs (0-8)
     * @return JavaFX CSS style string
     */
    private String getNumberStyle(int adj) {
        String base = "-fx-background-color: #e0e0e0;"
                + "-fx-border-color: #bdbdbd;"
                + "-fx-border-width: 1;"
                + "-fx-background-radius: 3;"
                + "-fx-border-radius: 3;"
                + "-fx-font-size: 16px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: ";
        return switch (adj) {
        case 1 -> base + "blue;";
        case 2 -> base + "green;";
        case 3 -> base + "red;";
        case 4 -> base + "darkblue;";
        case 5 -> base + "darkred;";
        case 6 -> base + "teal;";
        case 7 -> base + "brown;";
        case 8 -> base + "gray;";
        default -> base + "black;";
        };
    }

    /**
     * Sets a button's graphic to an icon, falling back to text if the icon is null.
     *
     * @param btn      the button to update
     * @param image    the image to display, or null to fall back to text
     * @param fallback the text to show if image is null
     */
    private void setButtonIcon(Button btn,
            javafx.scene.image.Image image, String fallback) {
        if (image != null) {
            javafx.scene.image.ImageView iv =
                    new javafx.scene.image.ImageView(image);
            iv.setFitWidth(24);
            iv.setFitHeight(24);
            btn.setGraphic(iv);
            btn.setText("");
        } else {
            btn.setGraphic(null);
            btn.setText(fallback);
        }
    }

    /**
     * Utility method to show an alert dialog with a given title and message.
     *
     * @param title the title of the alert dialog
     * @param message the message to display in the alert dialog
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
