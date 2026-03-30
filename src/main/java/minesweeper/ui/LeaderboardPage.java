package minesweeper.ui;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import minesweeper.Storage;
import minesweeper.exception.StorageException;

/**
 * Displays the leaderboard window containing ranked completion times.
 */
public class LeaderboardPage {

    private final Stage primaryStage;
    private final Stage leaderboardStage;
    private final Storage storage;
    private final Runnable onClose;

    /**
     * Constructor for the LeaderboardPage class.
     * Call show() to display it.
     *
     * @param primaryStage the owner stage
     * @param storage      the storage object to load leaderboard entries
     */
    public LeaderboardPage(Stage primaryStage, Storage storage, Runnable onClose) {
        this.primaryStage = primaryStage;
        this.storage = storage;
        this.onClose = onClose;

        leaderboardStage = new Stage();
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.initOwner(primaryStage);
        leaderboardStage.initModality(Modality.WINDOW_MODAL);
        leaderboardStage.setResizable(false);
        leaderboardStage.setMinWidth(400);

        Image leaderboardIcon = new ResourceManager().loadLeaderboardPageIcon();
        if (leaderboardIcon != null) {
            leaderboardStage.getIcons().add(leaderboardIcon);
        }
    }

    /**
     * Shows the leaderboard window. If already open, brings it to the front.
     */
    public void show() {
        leaderboardStage.setScene(buildScene());
        if (leaderboardStage.isShowing()) {
            leaderboardStage.toFront();
        } else {
            leaderboardStage.show();
        }

        leaderboardStage.setOnCloseRequest(e -> {
            if (onClose != null) {
                onClose.run();
            }
        });
    }

    /**
     * Builds the leaderboard scene by loading entries from storage.
     * Displays rank, player name and completion time. Handles empty state and loading errors.
     *
     * @return Scene containing the leaderboard layout.
     */
    private Scene buildScene() {
        VBox content = new VBox(12);
        content.setPadding(new Insets(20));

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backButton = new Button("← Home");
        backButton.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-background-radius: 8;"
        );

        backButton.setOnAction(e -> {
            if (onClose != null) {
                onClose.run();
            }
            leaderboardStage.close();
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                System.out.println("Error returning to home: " + ex.getMessage());
            }
        });

        Label title = new Label("Leaderboard");
        title.setStyle(
                "-fx-font-size: 16px;"
                        + "-fx-font-weight: bold;"
        );

        header.getChildren().addAll(backButton, title);

        content.getChildren().add(header);

        try {
            List<String> entries = storage.loadLeaderboard();

            if (entries.isEmpty()) {
                Label emptyLabel = new Label(
                        "No entries yet. Win a game to get on the board!"
                );
                emptyLabel.setStyle("-fx-font-style: italic;");
                content.getChildren().add(emptyLabel);

            } else {
                for (int i = 0; i < entries.size(); i++) {

                    String[] parts = entries.get(i).split("\\|");

                    if (parts.length < 2) {
                        continue;
                    }

                    String name = parts[0];
                    long millis = Long.parseLong(parts[1]);
                    String time = formatMillis(millis);

                    content.getChildren().add(
                            buildEntryRow(i + 1, name, time)
                    );
                }
            }

        } catch (StorageException ex) {

            Label errorLabel = new Label(
                    "Could not load leaderboard: "
                            + ex.getMessage()
            );

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
     * @param rank Position on the leaderboard.
     * @param name Player name.
     * @param time Formatted completion time string.
     * @return Styled HBox row.
     */
    private HBox buildEntryRow(int rank, String name, String time) {

        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 10, 6, 10));

        String bgColor;
        String medal;

        switch (rank) {
        case 1 -> {
            bgColor = "#f6e7b6";
            medal = "🥇";
        }
        case 2 -> {
            bgColor = "#bababa";
            medal = "🥈";
        }
        case 3 -> {
            bgColor = "#b08d8d";
            medal = "🥉";
        }
        default -> {
            bgColor = "#f0f0f5";
            medal = "";
        }
        }

        row.setStyle(
                "-fx-background-color: " + bgColor + ";"
                        + "-fx-background-radius: 8;"
                        + "-fx-border-color: #cccccc;"
                        + "-fx-border-radius: 8;"
                        + "-fx-border-width: 1;"
        );

        Label rankLabel = new Label("#" + medal + rank);
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
     * Shows the leaderboard window and waits until it is closed.
     */
    public void showAndWait() {
        leaderboardStage.setScene(buildScene());
        leaderboardStage.showAndWait();
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
        long ms = millis % 1000;

        return String.format(
                "%02d:%02d.%03d",
                mins,
                secs,
                ms
        );
    }
}
