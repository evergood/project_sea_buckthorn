package com.foxminded.chart.operation;

public class ChartCombinerOutput {
    public static void main(String[] args) throws EmptyFileException {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.outputChart("src/test/resources/abbreviations.txt",
                "src/test/resources/start.log",
                "src/test/resources/end.log"));
    }
}

