package com.foxminded.chart.operation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("m:ss.SSS");

    public static LocalDateTime parseTime(String time) {
        return LocalDateTime.parse(time, DATE_FORMAT);
    }

    public static String getLapTime(ChartLine line) {
        return TIME_FORMAT.format(Duration.between(line.getStartTime(), line.getEndTime()).toMillis());
    }

}
