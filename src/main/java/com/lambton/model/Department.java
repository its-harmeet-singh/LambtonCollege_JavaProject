package com.lambton.model;

public class Department {
    private int id;
    private String name;
    private String location;

    public Department() { }

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Department(int id, String name, String location) {
        this(name, location);
        this.id = id;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
