package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import minesweeper.exception.MinesweeperException;
import minesweeper.storage.Storage;

/**
 * Check that bomb position generation code & num of bombs adjacent code works.
 */
@ExtendWith(MockitoExtension.class)
public class ValidateBombPositionTest {
    @Mock
    private Storage mockStorage;

    @Mock
    private CustomTimer mockTimer;

    private Gameboard gameboard;

    /**
     * Set up gameboard for testing before each test case.
     *
     * @throws MinesweeperException if gameboard initialisation fails.
     */
    @BeforeEach
    @SuppressWarnings("unused")
    void setup() throws MinesweeperException {
        gameboard = new Gameboard(mockTimer, mockStorage);
    }

    /**
     * Tests that checkBombNeighbours can correctly identify that a bomb cannot be placed at a particular position.
     * Checks with a centre cell (which has 8 neighbours).
     */
    @Test
    public void errorValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43, List.of(32, 33, 34, 42, 44, 52, 53, 54));
        assertEquals(false, isValid);
    }

    /**
     * Tests that checkBombNeighbours can correctly identify that a bomb cannot be placed at a particular position.
     * Checks with a corner cell (which has <8 neighbours).
     */
    @Test
    public void errorValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 11, 83));
        assertEquals(false, isValid);
    }

    /**
     * Tests that checkBombNeighbours can correctly identify that a bomb can be placed at a particular position.
     * Checks with a centre cell (which has <8 neighbours).
     */
    @Test
    public void correctValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 83));
        assertEquals(true, isValid);
    }

    /**
     * Tests that checkBombNeighbours can correctly identify that a bomb can be placed at a particular position.
     * Checks with a centre cell (which has 8 neighbours).
     */
    @Test
    public void correctValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43,
                List.of(32, 33, 34, 42, 44, 53, 54, 24, 99, 84));
        assertEquals(true, isValid);
    }

    /**
     * Tests that the adjacent bomb count is calculated correctly given a particular position.
     */
    @Test
    public void checkNumOfAdjacentBombsOne() {
        assertEquals(3, gameboard.numberOfAdjacentBombs(54, List.of(43, 65, 44, 22, 85)));
    }

    /**
     * Tests that the adjacent bomb count is calculated correctly given a particular position.
     */
    @Test
    public void checkNumOfAdjacentBombsTwo() {
        assertEquals(0, gameboard.numberOfAdjacentBombs(18, List.of(43, 65, 44, 22, 85)));
    }


}
