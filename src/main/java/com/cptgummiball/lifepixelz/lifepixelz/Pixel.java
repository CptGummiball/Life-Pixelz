package com.cptgummiball.lifepixelz.lifepixelz;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.util.*;

public class Pixel {
    private int x, y;
    private int energy = 100;
    private int resources = 0;
    private boolean hasLager;
    private boolean isFighting;
    private Random random = new Random();
    private TextArea logTextArea;
    private List<String> pathHistory = new ArrayList<>();  // Saves the movement path
    private Set<String> rememberedResources = new HashSet<>(); // Storage for known resources

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLogTextArea(TextArea logTextArea) {
        if (this.logTextArea == null) {
            this.logTextArea = logTextArea;
        }
    }

    public TextArea getLogTextArea() {
        return logTextArea;
    }

    public void consumeResource(Cell cell) {
        if (cell.getType().equals("resource") && energy < 100 && !rememberedResources.contains(cell.getX() + "," + cell.getY()) && !cell.isConsumed()) {
            energy += 20;  // Wins energy through consumption
            resources++;
            rememberedResources.add(cell.getX() + "," + cell.getY()); // Remember the resource
            cell.consume(); // Marks the resource as consumed
            if (logTextArea != null) {
                Platform.runLater(() -> {
                    logTextArea.appendText("Pixel consumes resource at (" + x + "," + y + ")\n");
                });
            }
        }
    }

    public void move(Grid grid) {
        int oldX = x, oldY = y;
        int direction = random.nextInt(4);
        x += (direction == 0 ? 1 : direction == 1 ? -1 : 0);
        y += (direction == 2 ? 1 : direction == 3 ? -1 : 0);

        if (grid.getCell(x, y) == null) {
            x = oldX;
            y = oldY;
        }

        // Saves movement path
        pathHistory.add("("+x+","+y+")");

            if (logTextArea != null) {
                Platform.runLater(() -> {
                logTextArea.appendText("Pixel moves to (" + x + "," + y + ")\n");
            });
        }
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public boolean isFighting() {
        return isFighting;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void rememberResource(int x, int y) {
        rememberedResources.add(x + "," + y);
    }

    // Method for "act", which includes the action of resources
    public void act(Grid grid) {
        Cell cell = grid.getCell(x, y);
        if (cell != null && cell.getType().equals("resource")) {
            consumeResource(cell);
        }
    }

    // Method to build a warehouse
    public void build(Grid grid) {
        Cell cell = grid.getCell(x, y);

        if (cell != null && cell.getType().equals("empty")) {
            // Check whether there are resources nearby
            if (resources > 0) {
                boolean nearbyResources = hasNearbyResources(grid);
                boolean nearbyBuilding = hasNearbyBuilding(grid);

                // If there are resources nearby and there is no warehouse yet
                if (nearbyResources && !nearbyBuilding) {
                    // The pixel decides to build a warehouse
                    cell.setType("building");
                    resources--;  // A resource is used for construction

                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                            logTextArea.appendText("Pixel builds Warehouse at (" + x + "," + y + "), because resources are nearby.\n");
                        });
                    }
                } else if (!nearbyResources) {
                    // If there are no resources nearby, it could be unnecessary to build a warehouse
                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                            logTextArea.appendText("Pixel sees no resources nearby and decides not to build a warehouse at (" + x + "," + y + ").\n");
                        });
                    }
                }
            } else {
                // If the pixel has no resources to build a warehouse
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                        logTextArea.appendText("Pixel doesn't have enough resources to build a warehouse at (" + x + "," + y + ").\n");
                    });
                }
            }
        }
    }

// Auxiliary methods to recognize resources and buildings nearby

    private boolean hasNearbyResources(Grid grid) {
        // Check the neighboring cells on resources
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                Cell cell = grid.getCell(nx, ny);
                if (cell != null && cell.getType().equals("resource")) {
                    return true;  // There are resources nearby
                }
            }
        }
        return false;  // No resources nearby
    }

    private boolean hasNearbyBuilding(Grid grid) {
        // Check whether there is a camp nearby
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                Cell cell = grid.getCell(nx, ny);
                if (cell != null && cell.getType().equals("building")) {
                    return true;  // There is already a warehouse nearby
                }
            }
        }
        return false;  // No warehouse nearby
    }

    // Method to remove resources from a warehouse
    public void withdrawFromLager(Grid grid) {
        Cell cell = grid.getCell(x, y);
        if (cell != null && cell.getType().equals("building")) {
            resources++;
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                    logTextArea.appendText("Pixel takes resources from warehouse at (" + x + "," + y + ")\n");
                });
            }
        }
    }

    // Method for the fight between pixels
    public void fight(List<Pixel> pixels) {
        for (Pixel p : pixels) {
            if (this != p && isAdjacent(p)) {
                isFighting = true;
                this.energy -= 10;
                p.energy -= 10;
                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                        logTextArea.appendText("Pixel at (" + x + "," + y + ") is fighting with Pixel at (" + p.getX() + "," + p.getY() + ")\n");
                    });
                }
            }
        }
    }

    // Method for checking whether two pixels are neighboring beard
    public boolean isAdjacent(Pixel p) {
        return (Math.abs(this.x - p.x) <= 1 && Math.abs(this.y - p.y) <= 1);
    }

    // Method for mating pixels
    public void pair(Pixel p) {
        if (isAdjacent(p)) {
            // Pixels create a new pixel
            if (random.nextBoolean()) {
                Pixel offspring = new Pixel(this.x, this.y);
                // new pixel can also have energy or other properties
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                    logTextArea.appendText("Pixel at (" + x + "," + y + ") mating with Pixel at (" + p.getX() + "," + p.getY() + ")\n");
                });
            }
            }
        }
    }
}
