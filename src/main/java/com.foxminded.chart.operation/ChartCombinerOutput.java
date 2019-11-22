package com.foxminded.chart.operation;

public class ChartCombinerOutput {
    public static void main(String[] args) throws EmptyFileException {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.outputChart("src/test/resources/ten.person/abbreviations.txt",
                "src/test/resources/ten.person/start.log",
                "src/test/resources/ten.person/end.log"));
    }
}

