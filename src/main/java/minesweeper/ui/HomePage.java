package minesweeper.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.exception.MinesweeperException;
import minesweeper.logic.StorageTimerUiGateway;

/**
 * Home page UI for the Minesweeper game.
 * Displays navigation options such as starting a new game,
 * continuing an existing game, viewing leaderboard and help.
 */
public class HomePage extends Application {

    private StorageTimerUiGateway gateway;
    private boolean hasExistingSave;


    /**
     * Starts the JavaFX application by setting up the home page UI.
     * It checks for existing saved game data to enable or disable the "Continue Game" button accordingly.
     * Sets up title and stylised buttons for "New Game", "Continue Game", "Leaderboard", and "Help".
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            this.gateway = new StorageTimerUiGateway();
            this.hasExistingSave = this.gateway.hasExistingSave();
        } catch (MinesweeperException e) {
            this.hasExistingSave = false;
        }

        Image appIcon = new ResourceManager().loadAppIcon();
        if (appIcon != null) {
            primaryStage.getIcons().add(appIcon);
        }

        Label title = new Label("Minesweeper");
        title.setStyle(
                "-fx-font-size: 35px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1a1a2e;"
        );

        Button newGameBtn = createButton(
            "New Game",
            "#4CAF50",
            "#388E3C"
        );

        Button continueBtn = createButton(
            "Continue Game",
            "#2196F3",
            "#1565C0"
        );

        Button leaderboardBtn = createButton("Leaderboard",
            "#9C27B0",
            "#6A1B9A"
        );

        Button helpBtn = createButton("Help",
            "#607D8B",
            "#37474F"
        );

        if (!hasExistingSave) {
            continueBtn.setDisable(true);
            continueBtn.setOpacity(0.45);
            continueBtn.setStyle(
                    continueBtn.getStyle()
                            + "-fx-cursor: default;"
            );
        }

        newGameBtn.setOnAction(e -> {
            try {
                GamePage gamePage = new GamePage(gateway, primaryStage, false);
                gamePage.show();
            } catch (MinesweeperException ex) {
                showError(primaryStage, ex.getMessage());
            }
        });

        continueBtn.setOnAction(e -> {
            try {
                GamePage gamePage = new GamePage(gateway, primaryStage, true);
                gamePage.show();
            } catch (MinesweeperException ex) {
                showError(primaryStage, ex.getMessage());
            }
        });

        leaderboardBtn.setOnAction(e -> {
            LeaderboardPage leaderboardPage = new LeaderboardPage(gateway, primaryStage, null);
            leaderboardPage.show();
        });

        helpBtn.setOnAction(e -> {
            ResourceManager resourceManager = new ResourceManager();
            new HelpPage(primaryStage, resourceManager).show();
        });

        Label versionLabel = new Label("Version: " + StorageTimerUiGateway.getVersion());
        versionLabel.setStyle(
                "-fx-font-size: 11px;"
                        + "-fx-text-fill: #9e9e9e;"
        );

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

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f0f5;");
        root.setCenter(layout);
        root.setBottom(versionLabel);
        BorderPane.setAlignment(versionLabel, Pos.BOTTOM_LEFT);
        BorderPane.setMargin(versionLabel, new Insets(0, 0, 10, 10));
        
        Scene scene = new Scene(root, 420, 520);
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
                "-fx-min-width: 240px;"
                        + "-fx-min-height: 46px;"
                        + "-fx-font-size: 15px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-radius: 8;"
                        + "-fx-cursor: hand;"
                        + "-fx-background-color: "
                        + bgColor
                        + ";";
        btn.setStyle(base);

        DropShadow shadow = new DropShadow(6, Color.rgb(0, 0, 0, 0.2));
        btn.setEffect(shadow);

        btn.setOnMouseEntered(e -> btn.setStyle(base.replace(bgColor, hoverColor)));
        btn.setOnMouseExited(e -> btn.setStyle(base));

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
    public static void main(String[] args) {
        launch(args);
    }
}
