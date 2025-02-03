package com.cptgummiball.lifepixelz.lifepixelz;

public class Grid {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private static final double RENEWAL_RATE = 0.1; // 10% der Ressourcen erneuern sich pro Zeiteinheit

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        // Initialisiere die Zellen und übergib x und y Koordinaten
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y); // Übergabe der Koordinaten
            }
        }

        // Zufällig Ressourcen auf der Karte platzieren
        generateResources();
    }

    public void generateResources() {
        for (int i = 0; i < (width * height) / 10; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (cells[x][y].getType().equals("empty")) {
                cells[x][y].setType("resource");
                cells[x][y].setAge(0); // Ressourcenalter auf 0 setzen
            }
        }
    }


    public void regenerateResources() {
        // Ressourcen erneuern sich mit einer gewissen Wahrscheinlichkeit
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                if (cell.getType().equals("resource") && cell.isConsumed()) {
                    // Erneuerung der Ressource mit einer Wahrscheinlichkeit
                    if (Math.random() < RENEWAL_RATE) {
                        cell.resetConsumed();  // Setzt das Flag zurück und die Ressource ist wieder verfügbar
                        cell.setAge(0); // Ressource erneuert sich
                    }
                }
            }
        }
    }

    public Cell getCell(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cells[x][y];
        }
        return null;
    }

    public void updateResources() {
        // Ressourcenalter erhöhen und verbrauchen
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                if (cell.getType().equals("resource")) {
                    // Ressource altert mit der Zeit
                    cell.incrementAge();
                }
            }
        }
    }
}
