package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import minesweeper.exception.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

@ExtendWith(MockitoExtension.class)
public class GameboardTest {
    @Mock
    private Storage mockStorage;

    @Mock
    private CustomTimer mockTimer;

    private Gameboard gameboard;

    @BeforeEach
    void setup() throws MinesweeperException {
        gameboard = Mockito.spy(new Gameboard(mockTimer, mockStorage));
        doReturn(List.of(24, 43, 54, 59, 80)).when(gameboard).generateBombPlacements();
        gameboard.restartGameboard(mockTimer, mockStorage);
    }

    @Test
    public void checkBombPlacements_RestartGameboard() throws MinesweeperException {
        assertTrue(gameboard.getBox(24 / 10, 24 % 10).getBomb());
        assertTrue(gameboard.getBox(43 / 10, 43 % 10).getBomb());
        assertTrue(gameboard.getBox(54 / 10, 54 % 10).getBomb());
        assertTrue(gameboard.getBox(59 / 10, 59 % 10).getBomb());
        assertTrue(gameboard.getBox(80 / 10, 80 % 10).getBomb());
        assertFalse(gameboard.getBox(30 / 10, 30 % 10).getBomb());
        assertFalse(gameboard.getBox(20 / 10, 20 % 10).getBomb());
        assertFalse(gameboard.getBox(96 / 10, 96 % 10).getBomb());
    }

    @Test
    public void checkAdjacentBombs_RestartGameboard() throws MinesweeperException {
        assertEquals(2, gameboard.getBox(34 / 10, 34 % 10).getAdjacentBombs());
        assertEquals(2, gameboard.getBox(44 / 10, 44 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(72 / 10, 72 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(70 / 10, 70 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(68 / 10, 68 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(97 / 10, 97 % 10).getAdjacentBombs());
    }

    @Test
    public void reloadGameboard() throws MinesweeperException {
        String solutionString = " | | | | | | | | | |\n" +
                " | | |1|1|1| | | | |\n" +
                " | | |1|B|1| | | | |\n" +
                " | |1|2|2|1| | | | |\n" +
                " | |1|B|2|1| | |1|1|\n" +
                " | |1|2|B|1| | |1|B|\n" +
                " | | |1|1|1| | |1|1|\n" +
                "1|1| | | | | | | | |\n" +
                "B|1| | | | | | | | |\n" +
                "1|1| | | | | | | | |\n";
        List<String> solutionArray = Arrays.asList(solutionString.split("\n"));
        String gameString = "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|N|R|R|R|R|R|\n" +
                "R|R|R|R|N|R|R|R|R|R|\n" +
                "R|R|R|N|N|R|R|R|R|R|\n" +
                "R|R|R|R|F|R|R|R|R|N|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "N|R|R|R|R|R|R|R|R|R|\n" +
                "N|R|R|R|R|R|R|R|R|R|\n";
        List<String> gameArray = Arrays.asList(gameString.split("\n"));
        gameboard.reloadGameboard(solutionArray, gameArray, mockTimer);
        assertTrue(gameboard.getBox(8, 7).getReveal());
        assertFalse(gameboard.getBox(2, 4).getReveal());
        assertEquals(2, gameboard.getBox(4, 4).getAdjacentBombs());
        assertThrows(MinesweeperException.class, () -> {
            gameboard.revealBoxInGameboard(54, mockStorage);
        });
    }

    @Test
    public void checkCorrectString_storeSolution() throws StorageException {
        List<Integer> bombs = List.of(24, 43, 54, 59, 80);
        String expected = " | | | | | | | | | |\n" +
                " | | |1|1|1| | | | |\n" +
                " | | |1|B|1| | | | |\n" +
                " | |1|2|2|1| | | | |\n" +
                " | |1|B|2|1| | |1|1|\n" +
                " | |1|2|B|1| | |1|B|\n" +
                " | | |1|1|1| | |1|1|\n" +
                "1|1| | | | | | | | |\n" +
                "B|1| | | | | | | | |\n" +
                "1|1| | | | | | | | |\n";
        verify(mockStorage).storeSolution(expected);
    }

    @Test
    public void checkCorrectString_storeGame() throws MinesweeperException {
        gameboard.revealBoxInGameboard(33, mockStorage);
        gameboard.revealBoxInGameboard(88, mockStorage);
        gameboard.setFlagInGameboard(54, true, mockStorage);
        String expected = "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|N|R|R|R|R|R|\n" +
                "R|R|R|R|N|R|R|R|R|R|\n" +
                "R|R|R|N|N|R|R|R|R|R|\n" +
                "R|R|R|R|F|R|R|R|R|N|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "R|R|R|R|R|R|R|R|R|R|\n" +
                "N|R|R|R|R|R|R|R|R|R|\n" +
                "N|R|R|R|R|R|R|R|R|R|\n";
        verify(mockStorage).storeGame(expected);
    }

    @Test
    public void checkSetFlag_setFlagInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, true, mockStorage);
        assertTrue(gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    @Test
    public void checkUnsetFlag_setFlagInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, false, mockStorage);
        assertFalse(gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    @Test
    public void checkRevealBox_revealBoxInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(59, true, mockStorage);
        assertEquals(Gameboard.MoveResult.BOMB, gameboard.revealBoxInGameboard(54, mockStorage));
        assertThrows(MinesweeperException.class, () -> {
            gameboard.revealBoxInGameboard(59, mockStorage);
        });
        assertEquals(Gameboard.MoveResult.SAFE, gameboard.revealBoxInGameboard(60, mockStorage));
    }

    @Test
    public void returnFalse_safeCellHidden_checkWin() throws MinesweeperException {
        gameboard.revealBoxInGameboard(20, mockStorage);
        assertFalse(gameboard.checkWin());
    }

    @Test
    public void returnTrue_allSafeCellsRevealed_checkWin() throws MinesweeperException {
        List<Integer> bombs = List.of(24, 43, 54, 59, 80);
        for (int i = 0; i < 100; i++) {
            if (!bombs.contains(i)) {
                gameboard.revealBoxInGameboard(i, mockStorage);
            }
        }
        assertTrue(gameboard.checkWin());
    }

    @Test
    public void checkFloodFill_floodfill() {
        gameboard.floodfill(85 / 10, 85 % 10);
        assertTrue(gameboard.getBox(16 / 10, 16 % 10).getReveal());
        assertTrue(gameboard.getBox(20 / 10, 20 % 10).getReveal());
        assertFalse(gameboard.getBox(54 / 10, 54 % 10).getReveal()); // check that bomb is not revealed
        assertFalse(gameboard.getBox(43 / 10, 43 % 10).getReveal()); // check that bomb is not revealed
        assertTrue(gameboard.getBox(48 / 10, 48 % 10).getReveal());
    }

    @Test
    public void checkRevealAllBombs_revealAllBombs() {
        List<Integer> bombs = List.of(24, 43, 54, 59, 80);
        gameboard.revealAllBombs();
        for (int i : bombs) {
            assertTrue(gameboard.getBox(i / 10, i % 10).getReveal());
        }
    }

    @Test
    public void checkUnflaggedBombCount_getUnflaggedBombCount() throws MinesweeperException {
        List<Integer> bombs = List.of(24, 43, 54, 59, 80);
        gameboard.setFlagInGameboard(24, true, mockStorage);
        gameboard.setFlagInGameboard(43, true, mockStorage);
        gameboard.setFlagInGameboard(59, true, mockStorage);
        gameboard.setFlagInGameboard(80, true, mockStorage);
        gameboard.setFlagInGameboard(43, false, mockStorage);
        assertEquals(2, gameboard.getUnflaggedBombCount());
    }

}
