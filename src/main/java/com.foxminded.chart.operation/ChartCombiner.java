package com.foxminded.chart.operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ChartCombiner {
    public void getData() throws IOException {
        Stream<String> data = Files.lines(Paths.get("abbreviations.txt"));

    }
}
