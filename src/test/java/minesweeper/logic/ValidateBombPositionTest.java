package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import minesweeper.exception.MinesweeperException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Check that bomb position generation code and adjacent bomb count code works.
 */
@ExtendWith(MockitoExtension.class)
public class ValidateBombPositionTest {

    @Mock
    private StorageTimerUiGateway mockGateway;

    private Gameboard gameboard;

    /**
     * Set up gameboard for testing before each test case.
     *
     * @throws MinesweeperException if gameboard initialisation fails.
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setup() throws MinesweeperException {
        gameboard = new Gameboard(mockGateway);
    }

    /**
     * Tests that checkBombNeighbours correctly identifies that a bomb cannot be placed
     * at a centre cell (which has 8 neighbours) when all neighbours are already bombs.
     */
    @Test
    public void errorValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43, List.of(32, 33, 34, 42, 44, 52, 53, 54));
        assertEquals(false, isValid);
    }

    /**
     * Tests that checkBombNeighbours correctly identifies that a bomb cannot be placed
     * at a corner cell (which has fewer than 8 neighbours) when all neighbours are already bombs.
     */
    @Test
    public void errorValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 11, 83));
        assertEquals(false, isValid);
    }

    /**
     * Tests that checkBombNeighbours correctly identifies that a bomb can be placed
     * at a corner cell when not all neighbours are bombs.
     */
    @Test
    public void correctValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 83));
        assertEquals(true, isValid);
    }

    /**
     * Tests that checkBombNeighbours correctly identifies that a bomb can be placed
     * at a centre cell when not all neighbours are bombs.
     */
    @Test
    public void correctValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43,
                List.of(32, 33, 34, 42, 44, 53, 54, 24, 99, 84));
        assertEquals(true, isValid);
    }

    /**
     * Tests that the adjacent bomb count is correctly calculated for a cell with 3 adjacent bombs.
     */
    @Test
    public void checkNumOfAdjacentBombsOne() {
        assertEquals(3, gameboard.numberOfAdjacentBombs(54, List.of(43, 65, 44, 22, 85)));
    }

    /**
     * Tests that the adjacent bomb count is correctly calculated for a cell with 0 adjacent bombs.
     */
    @Test
    public void checkNumOfAdjacentBombsTwo() {
        assertEquals(0, gameboard.numberOfAdjacentBombs(18, List.of(43, 65, 44, 22, 85)));
    }
}
