package com.foxminded.chart.operation;

public class ChartCombinerOutput {
    public static void main(String[] args) {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.outputChart("src/test/resources/abbreviations_ten_person.txt",
                "src/test/resources/start_ten_person.log", "src/test/resources/end_ten_person.log"));
    }
}

