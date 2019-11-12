package com.foxminded.chart.operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartLine implements Comparable<ChartLine> {
    private String name;
    private String team;
    private Date startTime;
    private Date endTime;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");

    public ChartLine(String name, String team) {
        this.setName(name);
        this.setTeam(team);
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
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

    @Override
    public int compareTo(ChartLine line) {
        return Long.compare(this.getEndTime().getTime() - this.getStartTime().getTime(),
                line.getEndTime().getTime() - line.getStartTime().getTime());
    }
}
