package minesweeper;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import minesweeper.exception.StorageException;

/**
 * Displays instructions loaded from help.txt.
 */
public class HelpPage {

    private final Stage primaryStage;
    private final Storage storage;

    public HelpPage(Stage primaryStage, Storage storage) {
        this.primaryStage = primaryStage;
        this.storage = storage;
    }

    public void show() {
        Label title = new Label("Help");
        title.setStyle("-fx-font-size: 28px;-fx-font-weight: bold;");

        Label helpLabel = new Label();

        try {
            List<String> helpLines = storage.loadHelp();
            String helpText = String.join("\n", helpLines);
            helpLabel.setText(helpText);
        } catch (StorageException e) {
            helpLabel.setText("Unable to load help file.");
        }

        helpLabel.setWrapText(true);
        helpLabel.setStyle("-fx-font-size: 14px;-fx-line-spacing: 4px;");

        ScrollPane scrollPane = new ScrollPane(helpLabel);
        scrollPane.setFitToWidth(true);

        Button backBtn = new Button("← Back");
        backBtn.setOnAction(e -> {
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                System.out.println("Error returning home");
            }
        });

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