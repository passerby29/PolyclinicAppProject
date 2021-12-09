package com.example.polyclinic20;

public class Workers {
    private String spec;
    private int photo;
    private String name;

    public Workers(String spec, String name, int photo){
        this.name = name;
        this.spec = spec;
        this.photo = photo;
    }

    public String getSpec(){return spec;}
    public String getName(){return name;}
    public int getPhoto(){return photo;}
}
