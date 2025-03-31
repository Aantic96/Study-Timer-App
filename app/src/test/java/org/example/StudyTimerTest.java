package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudyTimerTest {

    @Test
    void testFormatTime() {
        StudyTimer timer = new StudyTimer();

        assertEquals("Time: 00:00:00", timer.formatTime(0));
        assertEquals("Time: 00:01:00", timer.formatTime(60));
        assertEquals("Time: 01:01:01", timer.formatTime(3661));
    }

    @Test
    void testResetTimer() {
        StudyTimer timer = new StudyTimer();

        timer.startTimer();
        timer.pauseTimer();
        timer.resetTimer();

        assertEquals("Time: 00:00:00", timer.getFormattedTime());
        assertFalse(timer.isRunning());
    }
}
