package com.foxminded.chart.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChartCombiner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChartCombiner.class);

    private static final String UNDERSCORE = "_";
    private static final String VERTICAL_BAR = "|";
    private static final String SEPARATOR = "____________________________________________________________";

    public String outputChart(String filenameAbbreviation,
                              String filenameStart, String filenameEnd) {
        validate(filenameAbbreviation, "abbreviations.txt");
        validate(filenameStart, "start.log");
        validate(filenameEnd, "end.log");

        return formOutputData(sortChart(combineChart(filenameAbbreviation, filenameStart, filenameEnd)));
    }

    private String formOutputData (Map<String, ChartLine> chart) {
        AtomicInteger counter = new AtomicInteger(1);
        StringBuilder output = new StringBuilder();
        chart.forEach((key, value) -> {
            output.append(String.format("%-23s", new StringBuilder()
                    .append(counter.get())
                    .append('.')
                    .append(value
                            .getName())))
                    .append(VERTICAL_BAR)
                    .append(String.format("%-27s", value.getTeam()))
                    .append(VERTICAL_BAR)
                    .append(value.getLapTime())
                    .append("\n");
            counter.getAndIncrement();
            if (counter.get() == 16) {
                output.append(SEPARATOR).append("\n");
            }
        });

        return output.toString().trim();
    }

    private static void validate(String filename, String variableName) {
        String message;
        if (filename == null || filename.isEmpty()) {
            message = String.format("Path to file %s is null or empty", variableName);
        } else {
            if (Paths.get(filename).toFile().length() == 0) {
                message = String.format("File %s is empty", variableName);
            } else {
                return;
            }
        }
        LOGGER.error(message);
        throw new IllegalArgumentException(message);
    }

    private Map<String, ChartLine> sortChart(Map<String, ChartLine> chart) {
        return chart.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->
                        e1, LinkedHashMap::new));
    }

    private Map<String, ChartLine> combineChart(String filenameAbbrevation,
                                                String filenameStart, String filenameEnd) {
        Map<String, ChartLine> chart = new HashMap<>();
        getData(filenameAbbrevation).forEach((k, v) ->
                chart.put(k, new ChartLine(v.split(UNDERSCORE)[1], v.split(UNDERSCORE)[2])));
        getData(filenameStart).forEach((k, v) -> {
            try {
                chart.get(k).setStartTime(v);
            } catch (ParseException e) {
                LOGGER.warn(e.getMessage());
            }
        });
        getData(filenameEnd).forEach((k, v) -> {
            try {
                chart.get(k).setEndTime(v);
            } catch (ParseException e) {
                LOGGER.warn(e.getMessage());
            }
        });

        return chart;
    }

    private List<String> readFromFile(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getData(String path) {
        return readFromFile(path).stream().collect(Collectors.toMap((p) -> p.substring(0, 3), (p) -> p.substring(3)));
    }
}

