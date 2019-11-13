package com.foxminded.chart.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChartCombiner {
    private static final Logger logger = Logger.getLogger(ChartCombiner.class.getName());
    private static final String LOW_LINE = "_";
    private static final String LINE = "|";
    private static final String SEPARATOR = "____________________________________________________________";

    private Map<String, ChartLine> chart = new HashMap<>();

    public String outputChart(String filenameAbbrevation,
                              String filenameStart, String filenameEnd) throws IOException {
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

    private Map<String, ChartLine> sortChart(Map<String, ChartLine> chart) {
        return chart.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->
                        e1, LinkedHashMap::new));
    }

    private Map<String, ChartLine> combineChart(String filenameAbbrevation,
                                                String filenameStart, String filenameEnd) throws IOException {
        getAbbreviationData(this.chart, filenameAbbrevation);
        getStartData(this.chart, filenameStart);
        getEndData(this.chart, filenameEnd);
        return this.chart;
    }

    private void getAbbreviationData(Map<String, ChartLine> chart, String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach((p) ->
                    chart.put(p.split(LOW_LINE)[0], new ChartLine(p.split(LOW_LINE)[1], p.split(LOW_LINE)[2])));
        }
    }

    private void getStartData(Map<String, ChartLine> chart, String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach((p) -> {
                try {
                    chart.get(p.substring(0, 3)).setStartTime(p.substring(3));
                } catch (ParseException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }
            });
        }
    }

    private void getEndData(Map<String, ChartLine> chart, String path) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach((p) -> {
                try {
                    chart.get(p.substring(0, 3)).setEndTime(p.substring(3));
                } catch (ParseException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }
            });
        }
    }
}

