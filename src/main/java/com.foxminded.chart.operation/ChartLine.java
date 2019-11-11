package com.foxminded.chart.operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartLine {
    private String abbreviation;
    private String name;
    private String team;
    private Date startTime;
    private Date endTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setStartTime(String startTime) throws ParseException {
        this.startTime = dateFormat.parse(startTime);
    }

    public void setEndTime(String endTime) throws ParseException {
        this.endTime = dateFormat.parse(endTime);
    }

    public String toString() {
        char SEPARATOR = '|';
        SimpleDateFormat timeFormat = new SimpleDateFormat("m:ss.SSS");
        return new StringBuilder()
                .append(String.format("%-20s", name))
                .append(SEPARATOR)
                .append(String.format("%-27s", team))
                .append(SEPARATOR)
                .append(timeFormat.format(endTime.getTime() - startTime.getTime()))
                .toString();
    }
}
