package com.cptgummiball.lifepixelz.lifepixelz;

public class Grid {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private static final double RENEWAL_RATE = 0.1; // 10% of resources renew themselves per unit of time

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];

        // Initialize the cells and overgust X and Y coordinates
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(x, y); // Handover of the coordinates
            }
        }

        // Randomly place resources on the map
        generateResources();
    }

    public void generateResources() {
        for (int i = 0; i < (width * height) / 10; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (cells[x][y].getType().equals("empty")) {
                cells[x][y].setType("resource");
                cells[x][y].setAge(0); // Set resource age to 0
            }
        }
    }


    public void regenerateResources() {
        // Resources renew themselves with a certain probability
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                if (cell.getType().equals("resource") && cell.isConsumed()) {
                    // Renewal of the resource with a probability
                    if (Math.random() < RENEWAL_RATE) {
                        cell.resetConsumed();  // Reset the flag and the resource is available again
                        cell.setAge(0); // Resource renews itself
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
        // Increase and consume resource age
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = cells[x][y];
                if (cell.getType().equals("resource")) {
                    // Resource ages over time
                    cell.incrementAge();
                }
            }
        }
    }
}
