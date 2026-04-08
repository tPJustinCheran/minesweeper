package minesweeper.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.exception.StorageException;
import minesweeper.logic.StorageTimerUiGateway;
import minesweeper.storage.Storage;

/**
 * The WinPage class represents the dialog shown when the player wins the game.
 * It prompts the player to enter their name for the leaderboard, validates the input,
 * saves the entry to storage, and then opens the leaderboard page. It also displays
 * the player's completion time.
 */
public class WinPage {

    private final Stage primaryStage;
    private final StorageTimerUiGateway gateway;
    private final String finalTime;
    private final long timeMillis;
    private final Runnable onClose;

    /**
     * Constructor for the WinPage class.
     *
     * @param primaryStage the owner stage
     * @param finalTime    the formatted completion time string to display
     * @param timeMillis   the completion time in milliseconds to save
     * @param onClose      callback to run after the player submits their name
     */
    public WinPage(StorageTimerUiGateway gateway, Stage primaryStage,
            String finalTime, long timeMillis, Runnable onClose) {
        this.primaryStage = primaryStage;
        this.finalTime = finalTime;
        this.timeMillis = timeMillis;
        this.onClose = onClose;
        this.gateway = gateway;
    }

    /**
     * Displays the win dialog. Shows the completion time and prompts
     * the player to enter their name for the leaderboard.
     * Validates the name, saves the entry, opens the leaderboard,
     * then calls the onClose callback.
     */
    public void show() {
        Stage winStage = new Stage();
        winStage.setTitle("You win!");
        winStage.initOwner(primaryStage);
        winStage.initModality(Modality.WINDOW_MODAL);
        winStage.setResizable(false);

        Image winicon = new ResourceManager().loadWinPageIcon();
        if (winicon != null) {
            winStage.getIcons().add(winicon);
        }

        Label titleLabel = new Label("You Win!");
        titleLabel.setStyle(
                "-fx-font-size: 20px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1a1a2e;"
        );

        Label msgLabel = new Label("Completed in " + finalTime);
        msgLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #555577;"
                        + "-fx-font-family: monospace;"
        );

        Label nameLabel = new Label("Enter your name for the leaderboard:");
        nameLabel.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-text-fill: #333333;"
        );

        TextField nameField = new TextField();
        nameField.setPromptText("Your name");
        nameField.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-background-radius: 6;"
                        + "-fx-border-color: #cccccc;"
                        + "-fx-border-radius: 6;"
                        + "-fx-border-width: 1;"
                        + "-fx-padding: 6 10;"
        );
        nameField.setMaxWidth(240);

        Label errorLabel = new Label();
        errorLabel.setStyle(
                "-fx-text-fill: #be1300;"
                        + "-fx-font-size: 12px;"
        );

        Button enterBtn = new Button("Submit");
        enterBtn.setDefaultButton(true);
        enterBtn.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-color: #4CAF50;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 8 24;"
                        + "-fx-cursor: hand;"
        );
        enterBtn.setOnMouseEntered(e -> enterBtn.setStyle(
                enterBtn.getStyle().replace("#4CAF50", "#388E3C")));
        enterBtn.setOnMouseExited(e -> enterBtn.setStyle(
                enterBtn.getStyle().replace("#388E3C", "#4CAF50")));

        boolean[] buttonClicked = {false};

        enterBtn.setOnAction(e -> {
            buttonClicked[0] = true;
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                name = "Anonymous";
            }

            if (name.contains("|")) {
                errorLabel.setText("Name cannot contain '|'.");
                return;
            }

            try {
                gateway.addEntry(name, timeMillis);
            } catch (StorageException ex) {
                errorLabel.setText("Could not save: " + ex.getMessage());
                return;
            }

            winStage.close();
            new LeaderboardPage(gateway, primaryStage, onClose).showAndWait();
            onClose.run();
        });

        VBox winCard = new VBox(12);
        winCard.setPadding(new Insets(28, 32, 28, 32));
        winCard.setAlignment(Pos.CENTER);
        winCard.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #e0e0e8;"
                        + "-fx-border-radius: 12;"
                        + "-fx-border-width: 1;"
        );
        winCard.getChildren().addAll(
                titleLabel,
                msgLabel,
                nameLabel,
                nameField,
                enterBtn,
                errorLabel
        );

        VBox root = new VBox(winCard);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f0f5;");

        winStage.setOnCloseRequest(e -> {
            if (!buttonClicked[0]) {
                onClose.run();
                try {
                    new HomePage().start(primaryStage);
                } catch (Exception ex) {
                    System.out.println("Error returning to home: " + ex.getMessage());
                }
            }
        });

        winStage.setScene(new Scene(root, 360, 290));
        winStage.show();
    }
}
