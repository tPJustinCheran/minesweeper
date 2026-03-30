package minesweeper.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
     * @param primaryStage the owner stage
     * @param finalTime    the formatted completion time string to display
     * @param onPlayAgain callback to run when the player clicks "Play Again"
     * @param onHomeButton callback to run when the player clicks "Back to Home"
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
     * Displays the lose dialog. Shows the final time and provides
     * a Play Again button to reset the board.
     */
    public void show() {
        Stage loseStage = new Stage();
        loseStage.setTitle("Game Over");
        loseStage.initOwner(primaryStage);
        loseStage.initModality(Modality.APPLICATION_MODAL);
        loseStage.setResizable(false);

        Image loseIcon = new ResourceManager().loadLosePageIcon();
        if (loseIcon != null) {
            loseStage.getIcons().add(loseIcon);
        }

        Label msgLabel = new Label("You hit a bomb!");
        Label timeLabel = new Label("Time: " + finalTime);
        Label bombLabel = new Label("Bombs not flagged: " + bombsNotFlagged);

        Button playAgainBtn = new Button("Play Again");
        playAgainBtn.setDefaultButton(true);

        Button homeBtn = new Button("Back to Home");

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

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(24));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                msgLabel,
                timeLabel,
                bombLabel,
                playAgainBtn,
                homeBtn
        );

        loseStage.setScene(new Scene(layout, 280, 180));
        loseStage.show();
    }
}
