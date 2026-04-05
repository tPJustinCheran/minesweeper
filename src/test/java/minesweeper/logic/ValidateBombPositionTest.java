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

    @BeforeEach
    @SuppressWarnings("unused")
    void setup() throws MinesweeperException {
        gameboard = new Gameboard(mockTimer, mockStorage);
    }

    @Test
    public void errorValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43, List.of(32, 33, 34, 42, 44, 52, 53, 54));
        assertEquals(false, isValid);
    }

    @Test
    public void errorValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 11, 83));
        assertEquals(false, isValid);
    }

    @Test
    public void correctValidateCornerCell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 83));
        assertEquals(true, isValid);
    }

    @Test
    public void correctValidateCentreCell() {
        boolean isValid = gameboard.checkBombNeighbours(43,
                List.of(32, 33, 34, 42, 44, 53, 54, 24, 99, 84));
        assertEquals(true, isValid);
    }

    @Test
    public void checkNumOfAdjacentBombsOne() {
        assertEquals(3, gameboard.numberOfAdjacentBombs(54, List.of(43, 65, 44, 22, 85)));
    }

    @Test
    public void checkNumOfAdjacentBombsTwo() {
        assertEquals(0, gameboard.numberOfAdjacentBombs(18, List.of(43, 65, 44, 22, 85)));
    }


}
