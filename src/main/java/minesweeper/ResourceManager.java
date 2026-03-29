package minesweeper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Handles loading of static resources from resources folder.
 */
public class ResourceManager {

    /**
     * Loads help text from help.txt in resources folder.
     *
     * @return help text as a single String
     */
    public String loadHelpText() {
        try (InputStream is = getClass().getResourceAsStream("/help.txt")) {
            if (is == null) {
                return "Help file missing.";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Unable to load help.";
        }
    }
}
