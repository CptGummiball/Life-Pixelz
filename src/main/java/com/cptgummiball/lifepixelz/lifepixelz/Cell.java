package com.cptgummiball.lifepixelz.lifepixelz;

public class Cell {
    private String type = "empty"; // Standardmäßig leer
    private int age = 0; // Alter der Ressource (für Verbrauch und Erneuerung)
    private int x, y; // Position der Zelle im Grid
    private boolean consumed = false; // Flag, ob die Ressource verbraucht wurde

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

    // Getter für die Koordinaten
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


