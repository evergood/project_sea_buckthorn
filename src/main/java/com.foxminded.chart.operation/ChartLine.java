package com.foxminded.chart.operation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChartLine {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("m:ss.SSS");
    private String name;
    private String team;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ChartLine(String name, String team) {
        this.setName(name);
        this.setTeam(team);
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    String getLapTime() {
        return TIME_FORMAT.format(Duration.between(this.getStartTime(), this.getEndTime()).toMillis());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setStartTime(String startTime) throws ParseException {
        this.startTime = LocalDateTime.parse(startTime, DATE_FORMAT);
    }

    public void setEndTime(String endTime) throws ParseException {
        this.endTime = LocalDateTime.parse(endTime, DATE_FORMAT);
    }


    @Override
    public int compareTo(ChartLine line) {
        return Long.compare(Duration.between(line.getEndTime(), line.getStartTime()).toMillis(),
                Duration.between(this.getEndTime(), this.getStartTime()).toMillis());
    }
}
