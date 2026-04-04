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
public class TimerTest {
    @Mock
    private Storage mockStorage;

    private CustomTimer customTimer;

    @Test
    void testLoadTime_Constructor() throws MinesweeperException {
        doReturn(240L).when(mockStorage).loadTime();
        customTimer = new CustomTimer(mockStorage);
        assertEquals(240L, customTimer.getOffsetMillis());
    }

    @Test
    void testNoLoadTime_Constructor() throws MinesweeperException {
        doThrow(new StorageException("No Old Timing")).when(mockStorage).loadTime();
        customTimer = new CustomTimer(mockStorage);
        assertEquals(0L, customTimer.getOffsetMillis());
    }
}
