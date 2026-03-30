package minesweeper.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The LosePage class represents the dialog shown when the player loses the game.
 * It prompts the player to play again or return to the home page, and displays
 * the player's completion time.
 */
public class LosePage {

    private final int bombsNotFlagged;
    private final Stage primaryStage;
    private final String finalTime;
    private final Runnable onPlayAgain;
    private final Runnable onHomeButton;

    /**
     * Constructor for the LosePage class.
     *
     * @param primaryStage    the owner stage
     * @param finalTime       the formatted completion time string to display
     * @param bombsNotFlagged the number of bombs not flagged by the player
     * @param onPlayAgain     callback to run when the player clicks "Play Again"
     * @param onHomeButton    callback to run when the player clicks "Back to Home"
     */
    public LosePage(Stage primaryStage, String finalTime,
            int bombsNotFlagged, Runnable onPlayAgain, Runnable onHomeButton) {
        this.primaryStage = primaryStage;
        this.finalTime = finalTime;
        this.bombsNotFlagged = bombsNotFlagged;
        this.onPlayAgain = onPlayAgain;
        this.onHomeButton = onHomeButton;
    }

    /**
     * Displays the lose dialog. Shows the final time, unflagged bomb count,
     * and provides Play Again and Back to Home buttons.
     */
    public void show() {
        Stage loseStage = new Stage();
        loseStage.setTitle("Game Over");
        loseStage.initOwner(primaryStage);
        loseStage.initModality(Modality.WINDOW_MODAL);
        loseStage.setResizable(false);

        Image loseIcon = new ResourceManager().loadLosePageIcon();
        if (loseIcon != null) {
            loseStage.getIcons().add(loseIcon);
        }

        Label titleLabel = new Label("Game Over");
        titleLabel.setStyle(
                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1a1a2e;"
        );

        Label msgLabel = new Label("You hit a bomb!");
        msgLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #c0392b;"
        );

        Label timeLabel = new Label("Time: " + finalTime);
        timeLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #555577;"
                        + "-fx-font-family: monospace;"
        );

        Label bombLabel = new Label("Bombs not flagged: " + bombsNotFlagged);
        bombLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #333333;"
        );

        Button playAgainBtn = new Button("Play Again");
        playAgainBtn.setDefaultButton(true);
        playAgainBtn.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-color: #4CAF50;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 8 20;"
                        + "-fx-cursor: hand;"
        );
        playAgainBtn.setOnMouseEntered(e -> playAgainBtn.setStyle(
                playAgainBtn.getStyle().replace("#4CAF50", "#388E3C")));
        playAgainBtn.setOnMouseExited(e -> playAgainBtn.setStyle(
                playAgainBtn.getStyle().replace("#388E3C", "#4CAF50")));

        Button homeBtn = new Button("Back to Home");
        homeBtn.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-color: #607D8B;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 8 20;"
                        + "-fx-cursor: hand;"
        );
        homeBtn.setOnMouseEntered(e -> homeBtn.setStyle(
                homeBtn.getStyle().replace("#607D8B", "#37474F")));
        homeBtn.setOnMouseExited(e -> homeBtn.setStyle(
                homeBtn.getStyle().replace("#37474F", "#607D8B")));

        boolean[] buttonClicked = {false};

        playAgainBtn.setOnAction(e -> {
            buttonClicked[0] = true;
            loseStage.close();
            onPlayAgain.run();
        });

        homeBtn.setOnAction(e -> {
            buttonClicked[0] = true;
            loseStage.close();
            onHomeButton.run();
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                System.out.println("Error returning to home: " + ex.getMessage());
            }
        });

        loseStage.setOnCloseRequest(e -> {
            if (!buttonClicked[0]) {
                onPlayAgain.run();
            }
        });

        HBox btnRow = new HBox(12);
        btnRow.setAlignment(Pos.CENTER);
        btnRow.getChildren().addAll(playAgainBtn, homeBtn);

        VBox loseCard = new VBox(12);
        loseCard.setPadding(new Insets(28, 32, 28, 32));
        loseCard.setAlignment(Pos.CENTER);
        loseCard.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #e0e0e8;"
                        + "-fx-border-radius: 12;"
                        + "-fx-border-width: 1;"
        );
        loseCard.getChildren().addAll(
                titleLabel,
                msgLabel,
                timeLabel,
                bombLabel,
                btnRow
        );

        VBox root = new VBox(loseCard);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f0f5;");

        loseStage.setScene(new Scene(root, 360, 280));
        loseStage.show();
    }
}
