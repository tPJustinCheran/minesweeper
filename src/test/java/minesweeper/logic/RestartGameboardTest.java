package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import minesweeper.Storage;
import minesweeper.exception.MinesweeperException;

@ExtendWith(MockitoExtension.class)
public class RestartGameboardTest {
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
    public void checkBombPlacementsOnRestartGameboard() throws MinesweeperException {
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
    public void checkAdjacentBombsOnRestartGameboard() throws MinesweeperException {
        assertEquals(2, gameboard.getBox(34 / 10, 34 % 10).getAdjacentBombs());
        assertEquals(2, gameboard.getBox(44 / 10, 44 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(72 / 10, 72 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(70 / 10, 70 % 10).getAdjacentBombs());
        assertEquals(1, gameboard.getBox(68 / 10, 68 % 10).getAdjacentBombs());
        assertEquals(0, gameboard.getBox(97 / 10, 97 % 10).getAdjacentBombs());
    }


    // yet to do unit testing for ReloadGameboard, storesolution, storegame

    @Test
    public void checkSetFlag() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, true, mockStorage);
        assertEquals(true, gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    @Test
    public void checkUnsetFlag() throws MinesweeperException {
        gameboard.setFlagInGameboard(24, false, mockStorage);
        assertEquals(false, gameboard.getBox(24 / 10, 24 % 10).getFlag());
    }

    @Test
    public void checkRevealBox() throws MinesweeperException {
        gameboard.setFlagInGameboard(59, true, mockStorage);
        assertEquals(Gameboard.MoveResult.BOMB, gameboard.revealBoxInGameboard(54, mockStorage));
        assertThrows(MinesweeperException.class, () -> {
            gameboard.revealBoxInGameboard(59, mockStorage);
        });
        assertEquals(Gameboard.MoveResult.SAFE, gameboard.revealBoxInGameboard(60, mockStorage));
    }

    @Test
    public void checkWin_returnFalse_safeCellHidden() {
        gameboard
    }

}
