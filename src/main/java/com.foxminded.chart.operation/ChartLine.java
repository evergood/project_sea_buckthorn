package com.foxminded.chart.operation;

import java.time.Duration;
import java.time.LocalDateTime;

public class ChartLine implements Comparable<ChartLine> {
    private final String name;
    private final String teamName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    private ChartLine(Builder builder) {
        this.name = builder.name;
        this.teamName = builder.teamName;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
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

    @Override
    public int compareTo(ChartLine line) {
        return Duration.between(line.getEndTime(), line.getStartTime()).
                compareTo(Duration.between(this.getEndTime(), this.getStartTime()));
    }

    public static class Builder {
        private String name;
        private String teamName;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Builder() {

        }

        public ChartLine build() {
            return new ChartLine(this);
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTeamName(String teamName) {
            this.teamName = teamName;
            return this;
        }

        public Builder withStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }
    }
}
