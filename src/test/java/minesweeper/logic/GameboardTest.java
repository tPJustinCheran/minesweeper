package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import minesweeper.exception.MinesweeperException;
import minesweeper.exception.StorageException;
import minesweeper.storage.Storage;

/**
 * Unit tests for the Gameboard class.
 */
@ExtendWith(MockitoExtension.class)
public class GameboardTest {

    @Mock
    private Storage mockStorage;

    @Mock
    private CustomTimer mockTimer;

    private Gameboard gameboard;

    /**
     * Sets up the gameboard with a fixed set of bomb placements before each test.
     *
     * @throws MinesweeperException if gameboard initialisation fails
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setup() throws MinesweeperException {
        gameboard = Mockito.spy(new Gameboard(mockTimer, mockStorage));
        doReturn(List.of(24, 43, 54, 59, 80)).when(gameboard).generateBombPlacements();
        gameboard.restartGameboard(mockTimer, mockStorage);
    }

    /**
     * Tests that all bomb placements are correctly set after restarting the gameboard.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkBombPlacements_restartGameboard() throws MinesweeperException {
        assertTrue(gameboard.getBox(24 / 10, 24 % 10).getBomb());
        assertTrue(gameboard.getBox(43 / 10, 43 % 10).getBomb());
        assertTrue(gameboard.getBox(54 / 10, 54 % 10).getBomb());
        assertTrue(gameboard.getBox(59 / 10, 59 % 10).getBomb());
        assertTrue(gameboard.getBox(80 / 10, 80 % 10).getBomb());
        assertFalse(gameboard.getBox(30 / 10, 30 % 10).getBomb());
        assertFalse(gameboard.getBox(20 / 10, 20 % 10).getBomb());
        assertFalse(gameboard.getBox(96 / 10, 96 % 10).getBomb());
    }

    /**
     * Tests that adjacent bomb counts are correctly calculated after restarting the gameboard.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkAdjacentBombs_restartGameboard() throws MinesweeperException {
        assertEquals(2, gameboard.getBox(34 / 10, 34 % 10).getAdjacentBombs());
        assertEquals(2, gameboard.getBox(44 / 10, 44 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(72 / 10, 72 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(70 / 10, 70 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(68 / 10, 68 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(97 / 10, 97 % 10).getAdjacentBombs());
    }

    /**
     * Tests that reloading the gameboard correctly restores reveal, flag and adjacent bomb state to gameboard.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void reloadGameboard() throws MinesweeperException {
        String solutionString = """
                 | | | | | | | | | |
                 | | |1|1|1| | | | |
                 | | |1|B|1| | | | |
                 | |1|2|2|1| | | | |
                 | |1|B|2|1| | |1|1|
                 | |1|2|B|1| | |1|B|
                 | | |1|1|1| | |1|1|
                1|1| | | | | | | | |
                B|1| | | | | | | | |
                1|1| | | | | | | | |
                """;
        List<String> solutionArray = solutionString.lines().toList();

        String gameString = """
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|N|R|R|R|R|R|
                R|R|R|R|N|R|R|R|R|R|
                R|R|R|N|N|R|R|R|R|R|
                R|R|R|R|F|R|R|R|R|N|
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|R|R|R|R|R|R|
                N|R|R|R|R|R|R|R|R|R|
                N|R|R|R|R|R|R|R|R|R|
                """;
        List<String> gameArray = gameString.lines().toList();

        gameboard.reloadGameboard(solutionArray, gameArray, mockTimer);
        assertTrue(gameboard.getBox(8, 7).getReveal());
        assertFalse(gameboard.getBox(2, 4).getReveal());
        assertEquals(2, gameboard.getBox(4, 4).getAdjacentBombs());
        assertThrows(MinesweeperException.class, () ->
                gameboard.revealBoxInGameboard(54, mockStorage));
    }

    /**
     * Tests that the correct solution string is sent to storage class after restarting the gameboard.
     *
     * @throws StorageException if storage operation fails
     */
    @Test
    public void checkCorrectString_storeSolution() throws StorageException {
        String expected = """
                 | | | | | | | | | |
                 | | |1|1|1| | | | |
                 | | |1|B|1| | | | |
                 | |1|2|2|1| | | | |
                 | |1|B|2|1| | |1|1|
                 | |1|2|B|1| | |1|B|
                 | | |1|1|1| | |1|1|
                1|1| | | | | | | | |
                B|1| | | | | | | | |
                1|1| | | | | | | | |
                """;
        verify(mockStorage).storeSolution(expected);
    }

    /**
     * Tests that the correct game string is sent to storage class after revealing and flagging cells.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkCorrectString_storeGame() throws MinesweeperException {
        gameboard.revealBoxInGameboard(33, mockStorage);
        gameboard.revealBoxInGameboard(88, mockStorage);
        gameboard.setFlagInGameboard(54, true, mockStorage);
        String expected = """
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|N|R|R|R|R|R|
                R|R|R|R|N|R|R|R|R|R|
                R|R|R|N|N|R|R|R|R|R|
                R|R|R|R|F|R|R|R|R|N|
                R|R|R|R|R|R|R|R|R|R|
                R|R|R|R|R|R|R|R|R|R|
                N|R|R|R|R|R|R|R|R|R|
                N|R|R|R|R|R|R|R|R|R|
                """;
        verify(mockStorage).storeGame(expected);
    }

    /**
     * Tests that a flag is correctly set on a cell.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkSetFlag_setFlagInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, true, mockStorage);
        assertTrue(gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    /**
     * Tests that a flag is correctly unset on a cell.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkUnsetFlag_setFlagInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, false, mockStorage);
        assertFalse(gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    /**
     * Tests that revealing a bomb returns BOMB, a flagged cell throws, and a safe cell returns SAFE.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkRevealBox_revealBoxInGameboard() throws MinesweeperException {
        gameboard.setFlagInGameboard(59, true, mockStorage);
        assertEquals(Gameboard.MoveResult.BOMB, gameboard.revealBoxInGameboard(54, mockStorage));
        MinesweeperException thrown = assertThrows(MinesweeperException.class, () ->
                gameboard.revealBoxInGameboard(59, mockStorage));
        assertEquals("Box has been flagged. Select a different Box!", thrown.getMessage());
        assertEquals(Gameboard.MoveResult.SAFE, gameboard.revealBoxInGameboard(60, mockStorage));
    }

    /**
     * Tests that checkWin returns false when a safe cell is still hidden.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void returnFalse_safeCellHidden_checkWin() throws MinesweeperException {
        gameboard.revealBoxInGameboard(20, mockStorage);
        assertFalse(gameboard.checkWin());
    }

    /**
     * Tests that checkWin returns true when all safe cells are revealed.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
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

    /**
     * Tests that flood fill correctly reveals connected safe cells and does not reveal bombs.
     */
    @Test
    public void checkFloodFill_floodfill() {
        gameboard.floodfill(85 / 10, 85 % 10);
        assertTrue(gameboard.getBox(16 / 10, 16 % 10).getReveal());
        assertTrue(gameboard.getBox(20 / 10, 20 % 10).getReveal());
        assertFalse(gameboard.getBox(54 / 10, 54 % 10).getReveal());
        assertFalse(gameboard.getBox(43 / 10, 43 % 10).getReveal());
        assertTrue(gameboard.getBox(48 / 10, 48 % 10).getReveal());
    }

    /**
     * Tests that all bombs are revealed after calling revealAllBombs.
     */
    @Test
    public void checkRevealAllBombs_revealAllBombs() {
        gameboard.revealAllBombs();
        for (int bomb : List.of(24, 43, 54, 59, 80)) {
            assertTrue(gameboard.getBox(bomb / 10, bomb % 10).getReveal());
        }
    }

    /**
     * Tests that the unflagged bomb count is correctly calculated.
     *
     * @throws MinesweeperException if gameboard operation fails
     */
    @Test
    public void checkUnflaggedBombCount_getUnflaggedBombCount() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, true, mockStorage);
        gameboard.setFlagInGameboard(43, true, mockStorage);
        gameboard.setFlagInGameboard(59, true, mockStorage);
        gameboard.setFlagInGameboard(80, true, mockStorage);
        gameboard.setFlagInGameboard(43, false, mockStorage);
        assertEquals(2, gameboard.getUnflaggedBombCount());
    }
}
