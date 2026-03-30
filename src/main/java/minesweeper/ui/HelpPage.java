package minesweeper.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Displays instructions loaded from resources/help.txt.
 */
public class HelpPage {

    private final Stage primaryStage;
    private final ResourceManager resourceManager;

    /**
     * Constructor for the HelpPage class.
     *
     * @param primaryStage    the owner stage
     * @param resourceManager the resource manager to load help text
     */
    public HelpPage(Stage primaryStage, ResourceManager resourceManager) {
        this.primaryStage = primaryStage;
        this.resourceManager = resourceManager;
    }

    /**
     * Shows the help page with instructions loaded from resources.
     * Provides a home button to close the help page.
     */
    public void show() {
        Stage helpStage = new Stage();
        helpStage.initOwner(primaryStage);
        helpStage.initModality(javafx.stage.Modality.WINDOW_MODAL);

        Image helpIcon = resourceManager.loadHelpPageIcon();
        if (helpIcon != null) {
            helpStage.getIcons().add(helpIcon);
        }

        Label title = new Label("\uD83D\uDCA3 Minesweeper Help");
        title.setStyle(
                "-fx-font-size: 22px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1a1a2e;"
        );

        VBox helpContent = buildHelpContent();

        ScrollPane scrollPane = new ScrollPane(helpContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #f0f0f5;"
                        + "-fx-background-color: #f0f0f5;"
        );

        Button homeBtn = new Button("\u2190 Home");
        homeBtn.setStyle(
                "-fx-font-size: 13px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: white;"
                        + "-fx-background-color: #607D8B;"
                        + "-fx-background-radius: 6;"
                        + "-fx-padding: 8 20;"
                        + "-fx-cursor: hand;"
        );
        homeBtn.setOnAction(e -> helpStage.close());

        VBox outerContent = new VBox(16);
        outerContent.setPadding(new Insets(25));
        outerContent.setAlignment(Pos.TOP_CENTER);
        outerContent.setStyle("-fx-background-color: #f0f0f5;");
        outerContent.getChildren().addAll(title, scrollPane, homeBtn);

        BorderPane layout = new BorderPane();
        layout.setCenter(outerContent);
        layout.setStyle("-fx-background-color: #f0f0f5;");

        helpStage.setScene(new Scene(layout, 460, 540));
        helpStage.setTitle("Help");
        helpStage.setResizable(false);
        helpStage.show();
    }

    /**
     * Builds the help content by parsing the help text into styled sections.
     *
     * @return VBox containing all styled help sections
     */
    private VBox buildHelpContent() {
        VBox container = new VBox(12);
        container.setPadding(new Insets(4, 8, 4, 8));
        container.setStyle("-fx-background-color: #f0f0f5;");

        String[] lines = resourceManager.loadHelpText().split("\n");

        VBox currentSection = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            if (isSectionHeader(line)) {
                currentSection = buildSection(line);
                container.getChildren().add(currentSection);
            } else if (currentSection != null) {
                Label bodyLabel = new Label(line);
                bodyLabel.setWrapText(true);
                bodyLabel.setStyle(
                        "-fx-font-size: 13px;"
                                + "-fx-text-fill: #333333;"
                );
                currentSection.getChildren().add(bodyLabel);
            }
        }

        return container;
    }

    /**
     * Builds a styled section card with a header label.
     *
     * @param header the section header text
     * @return VBox styled as a card with the header
     */
    private VBox buildSection(String header) {
        VBox section = new VBox(6);
        section.setPadding(new Insets(12, 14, 12, 14));
        section.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 8;"
                        + "-fx-border-color: #d0d0d8;"
                        + "-fx-border-radius: 8;"
                        + "-fx-border-width: 1;"
        );

        String emoji = getSectionEmoji(header);
        Label headerLabel = new Label(emoji + " " + header);
        headerLabel.setStyle(
                "-fx-font-size: 14px;"
                        + "-fx-font-weight: bold;"
                        + "-fx-text-fill: #1a1a2e;"
        );

        section.getChildren().add(headerLabel);
        return section;
    }

    /**
     * Checks if a line is a section header (all uppercase).
     *
     * @param line the line to check
     * @return true if the line is a section header
     */
    private boolean isSectionHeader(String line) {
        return line.equals(line.toUpperCase()) && line.length() > 1 && !line.contains("=");
    }

    /**
     * Returns an emoji for a given section header.
     * Defaults to an information emoji if the header is unrecognized.
     *
     * @param header the section header
     * @return emoji string for the section
     */
    private String getSectionEmoji(String header) {
        return switch (header) {
        case "GOAL" -> "\uD83C\uDFAF";
        case "CONTROLS" -> "\uD83D\uDDA5";
        case "NUMBERS" -> "\uD83D\uDD22";
        case "HINTS" -> "\uD83D\uDCA1";
        case "WIN" -> "\uD83C\uDFC6";
        case "LOSE" -> "\uD83D\uDCA3";
        default -> "\u2139";
        };
    }
}
