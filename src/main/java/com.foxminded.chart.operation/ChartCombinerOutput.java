package com.foxminded.chart.operation;

import java.io.IOException;
import java.text.ParseException;

public class ChartCombinerOutput {
    public static void main(String[] args) throws ParseException, IOException {
        ChartCombiner combiner = new ChartCombiner();
        System.out.println(combiner.getChart());
    }
}

