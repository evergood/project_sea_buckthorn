package com.foxminded.chart.operation;

public class ChartCombinerOutput {
    public static void main(String[] args) throws EmptyFileException {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.outputChart("src/test/resources/abbreviations_sixteen_person.txt",
                "src/test/resources/start_sixteen_person.log", "src/test/resources/end_sixteen_person.log"));
    }
}

