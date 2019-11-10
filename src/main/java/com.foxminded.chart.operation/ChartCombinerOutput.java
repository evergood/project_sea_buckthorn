package com.foxminded.chart.operation;

import java.text.ParseException;

public class ChartCombinerOutput {
    public static void main(String[] args) throws ParseException {
        ChartLine line = new ChartLine();
        line.setName("Igor Anolievich");
        line.setTeam("Lada Racing Club");
        line.setStartTime("2018-05-24_12:02:58.917");
        line.setEndTime("2018-05-24_12:04:03.332");
        System.out.println(line.toString());
    }
}

