package minesweeper.logic;

import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ValidateBombPlacementTest {
    @Mock
    private Storage storage;

    @Mock
    private CustomTimer customTimer;

    private Gameboard gameboard;

    @BeforeEach
    void setup() throws MinesweeperException {
        gameboard = new Gameboard(customTimer, storage);
    }

    @Test
    public void error_validate_centre_cell() {
        boolean isValid = gameboard.checkBombNeighbours(43, List.of(32, 33, 34, 42, 44, 52, 53, 54));
        assertEquals(false, isValid);
    }

    @Test
    public void error_validate_corner_cell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 11, 83));
        assertEquals(false, isValid);
    }

    @Test
    public void correct_validate_corner_cell() {
        boolean isValid = gameboard.checkBombNeighbours(0, List.of(1, 10, 83));
        assertEquals(true, isValid);
    }

    @Test
    public void correct_validate_centre_cell() {
        boolean isValid = gameboard.checkBombNeighbours(43,
                List.of(32, 33, 34, 42, 44, 53, 54, 24, 99, 84));
        assertEquals(true, isValid);
    }
}
