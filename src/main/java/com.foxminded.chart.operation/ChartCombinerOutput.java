package com.foxminded.chart.operation;

import java.io.IOException;

public class ChartCombinerOutput {
    public static void main(String[] args) throws IOException {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.outputChart("src/main/resources/abbreviations.txt",
                "src/main/resources/start.log", "src/main/resources/end.log"));
    }
}

