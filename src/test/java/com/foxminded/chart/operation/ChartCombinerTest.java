package com.foxminded.chart.operation;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChartCombinerTest {
    private final ChartCombiner combiner = new ChartCombiner();

    @Test
    void chartCombinerShouldThrowExceptionIfAbbreviationFilenameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart(null, "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("Path to file [abbreviations.txt] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfStartFilenameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt", null,
                        "src/test/resources/end.log"));
        assertEquals("Path to file [start.log] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfEndFilenameIsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt",
                        "src/test/resources/start.log", null));
        assertEquals("Path to file [end.log] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfAbbreviationFilenameIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("", "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("Path to file [abbreviations.txt] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfStartFilenameIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt", "",
                        "src/test/resources/end.log"));
        assertEquals("Path to file [start.log] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfEndFilenameIsEmpty() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt",
                        "src/test/resources/start.log", ""));
        assertEquals("Path to file [end.log] is null or empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfAbbreviationFileIsEmpty() {
        Throwable exception = assertThrows(EmptyFileException.class, () ->
                combiner.outputChart("src/test/resources/empty/abbreviations.txt",
                        "src/test/resources/start.log",
                        "src/test/resources/end.log"));
        assertEquals("File [abbreviations.txt] is empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfStartFileIsEmpty() {
        Throwable exception = assertThrows(EmptyFileException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt",
                        "src/test/resources/empty/start.log",
                        "src/test/resources/end.log"));
        assertEquals("File [start.log] is empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldThrowExceptionIfENdFileIsEmpty() {
        Throwable exception = assertThrows(EmptyFileException.class, () ->
                combiner.outputChart("src/test/resources/abbreviations.txt",
                        "src/test/resources/start.log",
                        "src/test/resources/empty/end.log"));
        assertEquals("File [end.log] is empty", exception.getMessage());
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForNineteenPerson() {
        final String expected = readFromFile("src/test/resources/expected.txt");
        assertEquals(expected, generateResult(""));
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForOnePerson() {
        final String expected = readFromFile("src/test/resources/one.person/expected.txt");
        assertEquals(expected, generateResult("one.person"));
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForTenPerson() {
        final String expected = readFromFile("src/test/resources/ten.person/expected.txt");
        assertEquals(expected, generateResult("ten.person"));
    }

    @Test
    void chartCombinerShouldReturnCorrectChartForSixteenPerson() {
        final String expected = readFromFile("src/test/resources/sixteen.person/expected.txt");
        assertEquals(expected, generateResult("sixteen.person"));
    }

    private String generateResult(String rootPackage) {
        return combiner.outputChart("src/test/resources/" + rootPackage + "/abbreviations.txt",
                "src/test/resources/" + rootPackage + "/start.log",
                "src/test/resources/" + rootPackage + "/end.log");
    }

    private String readFromFile(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
