package minesweeper;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.exception.MinesweeperException;

public class HomePage extends Application {

    private Storage storage;
    private boolean hasExistingSave;

    @Override
    public void start(Stage primaryStage) {

        // Check for existing save
        try {
            String home = System.getProperty("user.dir");
            storage = new Storage(home);
            storage.loadGame();         // throws StorageException if game.txt is empty
            storage.loadSolution();     // also verify solution exists
            hasExistingSave = true;
        } catch (MinesweeperException e) {
            hasExistingSave = false;
        }

        // Title
        Label title = new Label("Minesweeper");
        title.setStyle(
            "-fx-font-size: 35px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #1a1a2e;"
        );

        // Buttons
        Button newGameBtn      = createButton("New Game",       "#4CAF50", "#388E3C");
        Button continueBtn     = createButton("Continue Game",  "#2196F3", "#1565C0");
        Button leaderboardBtn  = createButton("Leaderboard",    "#9C27B0", "#6A1B9A");
        Button helpBtn         = createButton("Help",           "#607D8B", "#37474F");

        // Disable continue if no save file exists
        if (!hasExistingSave) {
            continueBtn.setDisable(true);
            continueBtn.setOpacity(0.45);
            continueBtn.setStyle(
                continueBtn.getStyle() +
                "-fx-cursor: default;"
            );
        }

        // Button actions
        newGameBtn.setOnAction(e -> {
            try {
                GamePage gamePage = new GamePage(primaryStage, storage, false);
                gamePage.show();
            } catch (MinesweeperException ex) {
                showError(primaryStage, ex.getMessage());
            }
        });

        continueBtn.setOnAction(e -> {
            showInfo(primaryStage, "Continue Game button works!");
        });

        leaderboardBtn.setOnAction(e -> {
            showInfo(primaryStage, "Leaderboard button works!");
        });

        helpBtn.setOnAction(e -> {
            showInfo(primaryStage, "Help button works!");
        });

        // Layout
        VBox layout = new VBox(16);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(60, 60, 60, 60));
        layout.setStyle("-fx-background-color: #f0f0f5;");
        layout.getChildren().addAll(
            title,
            spacer(20),
            newGameBtn,
            continueBtn,
            spacer(8),
            leaderboardBtn,
            helpBtn
        );

        // Scene
        Scene scene = new Scene(layout, 420, 520);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Creates a consistently styled button.
     *
     * @param text         Label shown on button.
     * @param bgColor      Normal background hex color.
     * @param hoverColor   Hover background hex color.
     * @return Styled Button.
     */
    private Button createButton(String text, String bgColor, String hoverColor) {
        Button btn = new Button(text);
        String base =
            "-fx-min-width: 240px;" +
            "-fx-min-height: 46px;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;" +
            "-fx-background-color: " + bgColor + ";";
        btn.setStyle(base);

        DropShadow shadow = new DropShadow(6, Color.rgb(0, 0, 0, 0.2));
        btn.setEffect(shadow);

        btn.setOnMouseEntered(e -> btn.setStyle(base.replace(bgColor, hoverColor)));
        btn.setOnMouseExited(e  -> btn.setStyle(base));

        return btn;
    }

    /**
     * Creates an invisible spacer node of a given height.
     *
     * @param height Height in pixels.
     * @return Label acting as spacer.
     */
    private Label spacer(int height) {
        Label spacer = new Label();
        spacer.setMinHeight(height);
        return spacer;
    }

    /**
     * Shows a simple error dialog on the primary stage.
     * Temporary helper — replace with a proper DialogPage when built.
     *
     * @param stage   Owner stage.
     * @param message Error message to display.
     */
    private void showError(Stage stage, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.ERROR
        );
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    /**
     * Shows a simple information dialog on the primary stage.
     * Temporary helper — replace with a proper DialogPage when built.
     *
     * @param stage   Owner stage.
     * @param message Information message to display.
     */
    private void showInfo(Stage stage, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION
        );
        alert.setTitle("Debug");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}