package minesweeper.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.ResourceManager;

/**
 * Displays instructions loaded from resources/help.txt.
 */
public class HelpPage {

    private final Stage primaryStage;
    private final ResourceManager resourceManager;

    /**
     * Constructor for the HelpPage class.
     *
     * @param primaryStage the owner stage
     * @param resourceManager the resource manager to load help text
     */
    public HelpPage(Stage primaryStage, ResourceManager resourceManager) {
        this.primaryStage = primaryStage;
        this.resourceManager = resourceManager;
    }

    /**
     * Shows the help page with instructions loaded from resources.
     * Provides a back button to return to the home page.
     */
    public void show() {
        Label title = new Label("Help");
        title.setStyle("-fx-font-size: 28px;-fx-font-weight: bold;");

        Label helpLabel = new Label(resourceManager.loadHelpText());
        helpLabel.setWrapText(true);
        helpLabel.setStyle("-fx-font-size: 14px;-fx-line-spacing: 4px;");

        ScrollPane scrollPane = new ScrollPane(helpLabel);
        scrollPane.setFitToWidth(true);

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> new HomePage().start(primaryStage));

        VBox content = new VBox(20);
        content.setPadding(new Insets(25));
        content.setAlignment(Pos.TOP_CENTER);
        content.getChildren().addAll(title, scrollPane, backBtn);

        BorderPane layout = new BorderPane();
        layout.setCenter(content);
        layout.setStyle("-fx-background-color: #f0f0f5;");

        Scene scene = new Scene(layout, 460, 520);
        primaryStage.setTitle("Help");
        primaryStage.setScene(scene);
    }
}
