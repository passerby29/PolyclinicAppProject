package com.example.polyclinic20;

public class Timetable {
    private String spec;
    private String name;
    private String time_from;
    private String time_to;

    public Timetable(String spec, String name, String time_from, String time_to) {
        this.name = name;
        this.spec = spec;
        this.time_from = time_from;
        this.time_to = time_to;
    }

    public String getSpec() {
        return spec;
    }
    public String getName(){
        return name;
    }
    public String getTime_from(){
        return time_from;
    }

    public String getTime_to() {
        return time_to;
    }
}
