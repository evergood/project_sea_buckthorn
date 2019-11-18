package com.foxminded.chart.operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChartCombinerTest {
    private final ChartCombiner combiner = new ChartCombiner();

    @Test
    void chartCombinerShouldThrowExceptionIfFilenameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart(null, "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("Path to file abbreviations.txt is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfFilenameIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("", "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("Path to file abbreviations.txt is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfFileIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations_empty.txt",
                        "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("File abbreviations.txt is empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForNineteenPerson() {
        final String expected =
                "1.Sebastian Vettel     |FERRARI                    |1:04.415\n" +
                        "2.Daniel Ricciardo     |RED BULL RACING TAG HEUER  |1:12.013\n" +
                        "3.Valtteri Bottas      |MERCEDES                   |1:12.434\n" +
                        "4.Lewis Hamilton       |MERCEDES                   |1:12.460\n" +
                        "5.Stoffel Vandoorne    |MCLAREN RENAULT            |1:12.463\n" +
                        "6.Kimi Raikkonen       |FERRARI                    |1:12.639\n" +
                        "7.Fernando Alonso      |MCLAREN RENAULT            |1:12.657\n" +
                        "8.Sergey Sirotkin      |WILLIAMS MERCEDES          |1:12.706\n" +
                        "9.Charles Leclerc      |SAUBER FERRARI             |1:12.829\n" +
                        "10.Sergio Perez        |FORCE INDIA MERCEDES       |1:12.848\n" +
                        "11.Romain Grosjean     |HAAS FERRARI               |1:12.930\n" +
                        "12.Pierre Gasly        |SCUDERIA TORO ROSSO HONDA  |1:12.941\n" +
                        "13.Carlos Sainz        |RENAULT                    |1:12.950\n" +
                        "14.Esteban Ocon        |FORCE INDIA MERCEDES       |1:13.028\n" +
                        "15.Nico Hulkenberg     |RENAULT                    |1:13.065\n" +
                        "____________________________________________________________\n" +
                        "16.Brendon Hartley     |SCUDERIA TORO ROSSO HONDA  |1:13.179\n" +
                        "17.Marcus Ericsson     |SAUBER FERRARI             |1:13.265\n" +
                        "18.Lance Stroll        |WILLIAMS MERCEDES          |1:13.323\n" +
                        "19.Kevin Magnussen     |HAAS FERRARI               |1:13.393";
        assertEquals(expected, combiner.outputChart("src/test/resources/abbreviations.txt",
                "src/test/resources/start.log", "src/test/resources/end.log"));
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForOnePerson() {
        final String expected = "1.Daniel Ricciardo     |RED BULL RACING TAG HEUER  |1:12.013";
        assertEquals(expected, combiner.outputChart("src/test/resources/abbreviations_one_person.txt",
                "src/test/resources/start_one_person.log",
                "src/test/resources/end_one_person.log"));
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForTenPerson() {
        final String expected = "1.Sebastian Vettel     |FERRARI                    |1:04.415\n" +
                "2.Daniel Ricciardo     |RED BULL RACING TAG HEUER  |1:12.013\n" +
                "3.Valtteri Bottas      |MERCEDES                   |1:12.434\n" +
                "4.Lewis Hamilton       |MERCEDES                   |1:12.460\n" +
                "5.Kimi Raikkonen       |FERRARI                    |1:12.639\n" +
                "6.Fernando Alonso      |MCLAREN RENAULT            |1:12.657\n" +
                "7.Sergio Perez         |FORCE INDIA MERCEDES       |1:12.848\n" +
                "8.Pierre Gasly         |SCUDERIA TORO ROSSO HONDA  |1:12.941\n" +
                "9.Carlos Sainz         |RENAULT                    |1:12.950\n" +
                "10.Esteban Ocon        |FORCE INDIA MERCEDES       |1:13.028";
        assertEquals(expected, combiner.outputChart("src/test/resources/abbreviations_ten_person.txt",
                "src/test/resources/start_ten_person.log",
                "src/test/resources/end_ten_person.log"));
    }

}
