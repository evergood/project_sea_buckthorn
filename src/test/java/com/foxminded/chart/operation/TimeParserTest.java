package com.foxminded.chart.operation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeParserTest {
    @Test
    public void timeParserShouldReturnCorrectTime() {
        LocalDateTime expected = LocalDateTime.of(2018, 5, 24, 12, 5, 58, 778000000);
        assertEquals(TimeParser.parseTime("2018-05-24_12:05:58.778"), expected);
    }

    @Test
    public void timeParserShouldThrowExceptionWithZeroDigits() {
        Throwable exception = assertThrows(DateTimeParseException.class, () ->
                TimeParser.parseTime("0000-05-24_12:05:58.778"));
    }

    @Test
    public void timeParserShouldThrowExceptionWithAbsentDigits() {
        Throwable exception = assertThrows(DateTimeParseException.class, () ->
                TimeParser.parseTime("0000-05-24_12:05:58"));
    }
}
