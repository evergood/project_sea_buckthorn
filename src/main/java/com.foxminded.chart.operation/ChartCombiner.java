package com.foxminded.chart.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChartCombiner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChartCombiner.class);

    private static final String UNDERSCORE = "_";
    private static final String VERTICAL_BAR = "|";
    private static final String SEPARATOR = "------------------------------------------------------------";
    private static final UnaryOperator<String> NAME_PARSER = a -> a.substring(4);
    private static final Function<String, LocalDateTime> TIME_PARSER = a ->
            TimeParser.parseTime(a.substring(3));

    public String outputChart(String filenameAbbreviation,
                              String filenameStart, String filenameEnd) {
        validate(filenameAbbreviation, "abbreviations.txt");
        validate(filenameStart, "start.log");
        validate(filenameEnd, "end.log");

        final Map<String, ChartLine> abbreviationToChartLine = combineChart(filenameAbbreviation,
                filenameStart, filenameEnd);
        return formOutputData(sortChart(abbreviationToChartLine));
    }

    private static String formOutputData(Map<String, ChartLine> abbreviationToCharLine) {
        AtomicInteger counter = new AtomicInteger(1);
        StringBuilder output = new StringBuilder();
        abbreviationToCharLine.forEach((key, value) -> formOutputChartLine(counter, output, value));

        return output.toString().trim();
    }

    private static void formOutputChartLine(AtomicInteger counter, StringBuilder output, ChartLine chartLine) {
        output.append(String.format("%-23s", combineFirstColumn(counter, chartLine)))
                .append(VERTICAL_BAR)
                .append(String.format("%-27s", chartLine.getTeam()))
                .append(VERTICAL_BAR)
                .append(TimeParser.getLapTime(chartLine))
                .append("\n");
        counter.getAndIncrement();
        if (counter.get() == 16) {
            output.append(SEPARATOR).append("\n");
        }
    }

    private static StringBuilder combineFirstColumn(AtomicInteger counter, ChartLine chartLine) {
        return new StringBuilder()
                .append(counter.get())
                .append('.')
                .append(chartLine.getName());
    }

    private static void validate(String filename, String variableName) {
        if (filename == null || filename.isEmpty()) {
            String message = String.format("Path to file [%s] is null or empty", variableName);
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        final File file = Paths.get(filename).toFile();

        if (!file.exists()) {
            String message = String.format("File [%s] does not exist at specified path", variableName);
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        if (file.length() == 0) {
            String message = String.format("File [%s] is empty", variableName);
            LOGGER.error(message);
            throw new EmptyFileException(String.format("File [%s] is empty", variableName));
        }
    }

    private static Map<String, ChartLine> sortChart(Map<String, ChartLine> abbreviationToChartLine) {
        return abbreviationToChartLine.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->
                        e1, LinkedHashMap::new));
    }

    private static Map<String, ChartLine> combineChart(String filenameAbbreviation,
                                                       String filenameStart, String filenameEnd) {

        final Map<String, String> abbreviationToNames = groupIntoMapAbbreviationToGeneric(filenameAbbreviation,
                NAME_PARSER);
        final Map<String, LocalDateTime> abbreviationToStartTime = groupIntoMapAbbreviationToGeneric(filenameStart,
                TIME_PARSER);
        final Map<String, LocalDateTime> abbreviationToEndTime = groupIntoMapAbbreviationToGeneric(filenameEnd,
                TIME_PARSER);

        Map<String, ChartLine> abbreviationToChartLine = new HashMap<>();
        abbreviationToNames.forEach((k, v) -> {
            final String[] words = abbreviationToNames.get(k).split(UNDERSCORE);
            abbreviationToChartLine.put(k,
                    new ChartLine.Builder()
                            .withName(words[0])
                            .withTeamName(words[1])
                            .withStartTime(abbreviationToStartTime.get(k))
                            .withEndTime(abbreviationToEndTime.get(k))
                            .build()
            );
        });

        return abbreviationToChartLine;
    }

    private static List<String> readFromFile(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static <T> Map<String, T> groupIntoMapAbbreviationToGeneric(String path,
                                                                        Function<String, T> parser) {
        return readFromFile(path).stream().collect(Collectors.toMap((p) -> p.substring(0, 3), parser));
    }
}

