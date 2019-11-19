package com.foxminded.chart.operation;

import java.time.Duration;
import java.time.LocalDateTime;

public class ChartLine implements Comparable<ChartLine> {
    private String name;
    private String teamName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ChartLine(String name, String teamName, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.teamName = teamName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ChartLine() {

    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return teamName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamName(String team) {
        this.teamName = team;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public int compareTo(ChartLine line) {
        return Duration.between(line.getEndTime(), line.getStartTime()).
                compareTo(Duration.between(this.getEndTime(), this.getStartTime()));
    }
}
