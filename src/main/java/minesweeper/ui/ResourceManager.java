package minesweeper.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javafx.scene.image.Image;

/**
 * Handles loading of static resources from resources folder.
 */
public class ResourceManager {

    private static final String APP_ICON = "appicon.png";
    private static final String LOSE_PAGE_ICON = "losepage.png";
    private static final String WIN_PAGE_ICON = "winpage.png";
    private static final String LEADERBOARD_PAGE_ICON = "leaderboardpage.png";
    private static final String HELP_PAGE_ICON = "helppage.png";
    private static final String BOMB_ICON = "bomb.png";
    private static final String FLAG_ICON = "flag.png";

    private static final String HELP_FILE_NAME = "help.txt";

    /**
     * Loads the application icon from the resources folder.
     *
     * @return application icon as a JavaFX Image, or null if not found
     */
    public Image loadAppIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + APP_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the lose page icon from the resources folder.
     *
     * @return lose page icon as a JavaFX Image, or null if not found
     */
    public Image loadLosePageIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + LOSE_PAGE_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the win page icon from the resources folder.
     *
     * @return win page icon as a JavaFX Image, or null if not found
     */
    public Image loadWinPageIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + WIN_PAGE_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the leaderboard page icon from the resources folder.
     *
     * @return leaderboard page icon as a JavaFX Image, or null if not found
     */
    public Image loadLeaderboardPageIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + LEADERBOARD_PAGE_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the help page icon from the resources folder.
     *
     * @return help page icon as a JavaFX Image, or null if not found
     */
    public Image loadHelpPageIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + HELP_PAGE_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the bomb icon from the resources folder.
     *
     * @return bomb icon as a JavaFX Image, or null if not found
     */
    public Image loadBombIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + BOMB_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads the flag icon from the resources folder.
     *
     * @return flag icon as a JavaFX Image, or null if not found
     */
    public Image loadFlagIcon() {
        try (InputStream is = getClass().getResourceAsStream("/" + FLAG_ICON)) {
            return is != null ? new Image(is) : null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Loads help text from help.txt in resources folder.
     *
     * @return help text as a single String
     */
    public String loadHelpText() {
        try (InputStream is = getClass().getResourceAsStream("/" + HELP_FILE_NAME)) {
            if (is == null) {
                return "Help file missing.";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            return "Unable to load help.";
        }
    }
}
