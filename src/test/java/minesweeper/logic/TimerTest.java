package minesweeper.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import minesweeper.exception.MinesweeperException;
import minesweeper.exception.StorageException;
import minesweeper.storage.Storage;

/**
 * Unit tests for the CustomTimer class.
 */
@ExtendWith(MockitoExtension.class)
public class TimerTest {

    @Mock
    private Storage mockStorage;

    private CustomTimer customTimer;

    /**
     * Tests that the timer loads the offset from storage when a previous time exists.
     *
     * @throws MinesweeperException if timer initialisation fails
     */
    @Test
    void testLoadTime_constructor() throws MinesweeperException {
        doReturn(240L).when(mockStorage).loadTime();
        customTimer = new CustomTimer(mockStorage);
        assertEquals(240L, customTimer.getOffsetMillis());
    }

    /**
     * Tests that the timer defaults to zero offset when no previous time exists in storage.
     *
     * @throws MinesweeperException if timer initialisation fails
     */
    @Test
    void testNoLoadTime_constructor() throws MinesweeperException {
        doThrow(new StorageException("No Old Timing")).when(mockStorage).loadTime();
        customTimer = new CustomTimer(mockStorage);
        assertEquals(0L, customTimer.getOffsetMillis());
    }
}
