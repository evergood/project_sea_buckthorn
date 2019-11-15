package com.foxminded.chart.operation;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

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

    private static final String LOW_LINE = "_";
    private static final String LINE = "|";
    private static final String SEPARATOR = "____________________________________________________________";

    public String outputChart(String filenameAbbrevation,
                              String filenameStart, String filenameEnd) {
        validate(filenameAbbrevation, filenameStart, filenameEnd);
        AtomicInteger counter = new AtomicInteger(1);
        StringBuilder output = new StringBuilder();
        sortChart(combineChart(filenameAbbrevation, filenameStart, filenameEnd)).forEach((key, value) -> {
            output.append(String.format("%-23s", new StringBuilder()
                    .append(counter.get())
                    .append('.')
                    .append(value
                            .getName())))
                    .append(LINE)
                    .append(String.format("%-27s", value.getTeam()))
                    .append(LINE)
                    .append(value.getLapTime())
                    .append("\n");
            counter.getAndIncrement();
            if (counter.get() == 16) {
                output.append(SEPARATOR).append("\n");
            }
        });

        return output.toString().trim();
    }

    private void validate(String filenameAbbrevation,
                          String filenameStart, String filenameEnd) {
        if (filenameAbbrevation == null) {
            throw new IllegalArgumentException("Abbreviation file is null");
        }
        if (filenameStart == null) {
            throw new IllegalArgumentException("Start log is null");
        }
        if (filenameEnd == null) {
            throw new IllegalArgumentException("End log is null");
        }
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
        getAbbreviationData(chart, filenameAbbrevation);
        getStartData(chart, filenameStart);
        getEndData(chart, filenameEnd);
        return chart;
    }

    private List<String> readFromFile(String path) {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            return lines.collect(Collectors.toCollection(LinkedList::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAbbreviationData(Map<String, ChartLine> chart, String path) {
        readFromFile(path).forEach((p) ->
                chart.put(p.split(LOW_LINE)[0], new ChartLine(p.split(LOW_LINE)[1], p.split(LOW_LINE)[2])));
    }

    private void getStartData(Map<String, ChartLine> chart, String path) {
        readFromFile(path).forEach((p) -> {
            try {
                chart.get(p.substring(0, 3)).setStartTime(p.substring(3));
            } catch (ParseException e) {
                LOGGER.warn(e.getMessage());
            }
        });
    }

    private void getEndData(Map<String, ChartLine> chart, String path) {
        readFromFile(path).forEach((p) -> {
            try {
                chart.get(p.substring(0, 3)).setEndTime(p.substring(3));
            } catch (ParseException e) {
                LOGGER.warn(e.getMessage());
            }
        });
    }
}

