package minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.exception.StorageException;

import java.util.List;

public class LeaderboardPage {

    private final Stage leaderboardStage;
    private final Storage storage;

    /**
     * Constructor for the LeaderboardPage class.
     * Call show() to display it.
     *
     * @param primaryStage the owner stage
     * @param storage      the storage object to load leaderboard entries
     */
    public LeaderboardPage(Stage primaryStage, Storage storage) {
        this.storage = storage;

        leaderboardStage = new Stage();
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.initOwner(primaryStage);
        leaderboardStage.setResizable(true);
        leaderboardStage.setMinWidth(400);
    }

    /**
     * Shows the leaderboard window. If already open, brings it to the front.
     */
    public void show() {
        leaderboardStage.setScene(buildScene());  // rebuild each time to get latest entries
        if (leaderboardStage.isShowing()) {
            leaderboardStage.toFront();
        } else {
            leaderboardStage.show();
        }
    }

    /**
     * Builds the leaderboard scene by loading entries from storage.
     *
     * @return Scene containing the leaderboard layout.
     */
    private Scene buildScene() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(20));

        Label title = new Label("Leaderboard");
        title.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;"
        );

        content.getChildren().add(title);

        try {
            List<String> entries = storage.loadLeaderboard();
            if (entries.isEmpty()) {
                Label emptyLabel = new Label("No entries yet. Win a game to get on the board!");
                emptyLabel.setStyle("-fx-font-style: italic;");
                content.getChildren().add(emptyLabel);
            } else {
                for (int i = 0; i < entries.size(); i++) {
                    String[] parts = entries.get(i).split("\\|");
                    if (parts.length < 2) {
                        continue;  // skip malformed lines
                    }
                    String name = parts[0];
                    long millis = Long.parseLong(parts[1]);
                    String time = formatMillis(millis);
                    content.getChildren().add(buildEntryRow(i + 1, name, time));
                }
            }
        } catch (StorageException ex) {
            Label errorLabel = new Label("Could not load leaderboard: " + ex.getMessage());
            errorLabel.setStyle("-fx-text-fill: red;");
            content.getChildren().add(errorLabel);
        }

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);

        return new Scene(scroll, 420, 400);
    }

    /**
     * Builds a single leaderboard entry row with rank, name and time.
     *
     * @param rank  Position on the leaderboard.
     * @param name  Player name.
     * @param time  Formatted completion time string.
     * @return Styled HBox row.
     */
    private HBox buildEntryRow(int rank, String name, String time) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 10, 6, 10));
        row.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-radius: 8;" +
            "-fx-border-width: 1;"
        );

        Label rankLabel = new Label("#" + rank);
        rankLabel.setMinWidth(40);
        rankLabel.setStyle("-fx-font-weight: bold;");

        Label nameLabel = new Label(name);
        nameLabel.setMinWidth(200);

        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-family: monospace;");

        row.getChildren().addAll(rankLabel, nameLabel, timeLabel);
        return row;
    }

    /**
     * Formats a millisecond value into MM:SS.mmm string.
     *
     * @param millis time in milliseconds
     * @return formatted time string
     */
    private String formatMillis(long millis) {
        long mins = millis / 60000;
        long secs = (millis % 60000) / 1000;
        long ms   = millis % 1000;
        return String.format("%02d:%02d.%03d", mins, secs, ms);
    }
}
