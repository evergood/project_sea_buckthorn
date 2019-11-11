package com.foxminded.chart.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ChartCombiner {
    Map<String, ChartLine> chart = new HashMap<>();

    public Map<String, ChartLine> getChart() throws IOException {
        return getData(chart);
    }

    public Map<String, ChartLine> getData(Map<String, ChartLine> chart) throws IOException {
        Files.lines(Paths.get("src/main/files/abbreviations.txt")).forEach((p) ->
                chart.put(p.split("_")[0], new ChartLine(p.split("_")[1], p.split("_")[2])));
        Files.lines(Paths.get("src/main/files/start.log")).forEach( (p) ->
                chart.computeIfPresent(p.substring(0,2), (k, v) -> {
                    try {
                         v.setStartTime(p.substring(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return v;
                }));
        Files.lines(Paths.get("src/main/files/end.log")).forEach( (p) ->
                chart.computeIfPresent(p.substring(0,2), (k, v) -> {
                    try {
                        v.setEndTime(p.substring(3));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return v;
                }));

        return chart;
    }
}
