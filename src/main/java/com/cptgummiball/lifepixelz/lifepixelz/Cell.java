package com.cptgummiball.lifepixelz.lifepixelz;

public class Cell {
    private String type = "empty"; // empty by default
    private int age = 0; // Age of the resource (for consumption and renewal)
    private int x, y; // Position of the cell in the grid
    private boolean consumed = false; // Flag, whether the resource was used up

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age++;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean hasBuilding() {
        return type.equals("building");
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void consume() {
        this.consumed = true;
    }

    public void resetConsumed() {
        this.consumed = false;
    }

    // Getter for the coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


