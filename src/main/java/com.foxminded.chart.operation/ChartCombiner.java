package com.foxminded.chart.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChartCombiner {
    private static final String LOW_LINE = "_";
    private static final String LINE = "|";
    private static final String SEPARATOR = "____________________________________________________________";
    private static final String ABBREVIATION_PATH = "src/main/files/abbreviations.txt";
    private static final String START_PATH = "src/main/files/start.log";
    private static final String END_PATH = "src/main/files/end.log";

    private Map<String, ChartLine> chart = new HashMap<>();

    public String outputChart() throws IOException {
        int counter = 1;
        StringBuilder output = new StringBuilder();
        for (Map.Entry<String, ChartLine> entry : sortChart(combineChart()).entrySet()) {
            output.append(String.format("%-23s", new StringBuilder()
                    .append(counter)
                    .append('.')
                    .append(entry.getValue()
                            .getName())))
                    .append(LINE)
                    .append(String.format("%-27s", entry.getValue().getTeam()))
                    .append(LINE)
                    .append(entry.getValue().getLapTime())
                    .append("\n");
            counter++;
            if (counter == 16) {
                output.append(SEPARATOR).append("\n");
            }
        }
        return output.toString().trim();
    }

    private Map<String, ChartLine> sortChart(Map<String, ChartLine> chart) {
        return chart.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) ->
                        e1, LinkedHashMap::new));
    }

    private Map<String, ChartLine> combineChart() throws IOException {
        getAbbreviationData(this.chart);
        getStartData(this.chart);
        getEndData(this.chart);
        return this.chart;
    }

    private void getAbbreviationData(Map<String, ChartLine> chart) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(ABBREVIATION_PATH))) {
            lines.forEach((p) ->
                    chart.put(p.split(LOW_LINE)[0], new ChartLine(p.split(LOW_LINE)[1], p.split(LOW_LINE)[2])));
        }
    }

    private void getStartData(Map<String, ChartLine> chart) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(START_PATH))) {
            lines.forEach((p) -> {
                try {
                    chart.get(p.substring(0, 3)).setStartTime(p.substring(4));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void getEndData(Map<String, ChartLine> chart) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(END_PATH))) {
            lines.forEach((p) -> {
                try {
                    chart.get(p.substring(0, 3)).setEndTime(p.substring(4));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

