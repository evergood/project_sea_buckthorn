package com.foxminded.chart.operation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartLine implements Comparable<ChartLine> {
    private String name;
    private String team;
    private Date startTime;
    private Date endTime;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("m:ss.SSS");

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

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    String getLapTime() {
        return timeFormat.format(endTime.getTime() - startTime.getTime());
    }

    @Override
    public int compareTo(ChartLine line) {
        return Long.compare(this.getEndTime().getTime() - this.getStartTime().getTime(),
                line.getEndTime().getTime() - line.getStartTime().getTime());
    }


}
