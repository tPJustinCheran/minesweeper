package minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.exception.StorageException;

/**
 * The WinPage class represents the dialog shown when the player wins the game.
 * It prompts the player to enter their name for the leaderboard, validates the input,
 * saves the entry to storage, and then opens the leaderboard page. It also displays
 * the player's completion time.
 */
public class WinPage {

    private final Stage primaryStage;
    private final Storage storage;
    private final String finalTime;
    private final long timeMillis;
    private final Runnable onClose;

    /**
     * Constructor for the WinPage class.
     *
     * @param primaryStage the owner stage
     * @param storage      the storage object to save leaderboard entry
     * @param finalTime    the formatted completion time string to display
     * @param timeMillis   the completion time in milliseconds to save
     * @param onClose      callback to run after the player submits their name
     */
    public WinPage(Stage primaryStage, Storage storage,
            String finalTime, long timeMillis, Runnable onClose) {
        this.primaryStage = primaryStage;
        this.storage = storage;
        this.finalTime = finalTime;
        this.timeMillis = timeMillis;
        this.onClose = onClose;
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
        winStage.initModality(Modality.APPLICATION_MODAL);
        winStage.setResizable(false);

        Label msgLabel = new Label(
                "You cleared the board in " + finalTime + "!");
        Label nameLabel = new Label(
                "Enter your name for the leaderboard:");
        TextField nameField = new TextField();
        nameField.setPromptText("Your name");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button enterBtn = new Button("Enter");
        enterBtn.setDefaultButton(true);

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
                storage.addEntry(name, timeMillis);
            } catch (StorageException ex) {
                errorLabel.setText(
                        "Could not save: " + ex.getMessage());
                return;
            }

            winStage.close();

            LeaderboardPage leaderboardPage =
                    new LeaderboardPage(primaryStage, storage);
            leaderboardPage.show();

            onClose.run();
        });

        VBox layout = new VBox(12);
        layout.setPadding(new Insets(24));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                msgLabel,
                nameLabel,
                nameField,
                enterBtn,
                errorLabel
        );

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

        winStage.setScene(new Scene(layout, 340, 210));
        winStage.show();
    }
}
