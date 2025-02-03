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
    private List<String> pathHistory = new ArrayList<>();  // Speichert den Bewegungsweg
    private Set<String> rememberedResources = new HashSet<>(); // Speicher für bekannte Ressourcen

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
            energy += 20;  // Gewinnt Energie durch Konsum
            resources++;
            rememberedResources.add(cell.getX() + "," + cell.getY()); // Merken der Ressource
            cell.consume(); // Markiert die Ressource als verbraucht
            if (logTextArea != null) {
                Platform.runLater(() -> {
                    logTextArea.appendText("Pixel konsumiert Ressource bei (" + x + "," + y + ")\n");
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

        // Den Bewegungsweg speichern
        pathHistory.add("("+x+","+y+")");

            if (logTextArea != null) {
                Platform.runLater(() -> {
                logTextArea.appendText("Pixel bewegt sich zu (" + x + "," + y + ")\n");
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

    // Methode für "act", was z.B. das Handeln von Ressourcen umfasst
    public void act(Grid grid) {
        // Hier könnte das Handeln von Ressourcen oder ähnliches implementiert werden
        Cell cell = grid.getCell(x, y);
        if (cell != null && cell.getType().equals("resource")) {
            consumeResource(cell);
        }
    }

    // Methode für "build", um z.B. ein Lager zu bauen
    public void build(Grid grid) {
        Cell cell = grid.getCell(x, y);

        if (cell != null && cell.getType().equals("empty")) {
            // Prüfen, ob Ressourcen in der Nähe sind
            if (resources > 0) {
                boolean nearbyResources = hasNearbyResources(grid);
                boolean nearbyBuilding = hasNearbyBuilding(grid);

                // Wenn Ressourcen in der Nähe sind und noch kein Lager vorhanden ist
                if (nearbyResources && !nearbyBuilding) {
                    // Das Pixel entscheidet sich, ein Lager zu bauen
                    cell.setType("building");
                    resources--;  // Eine Ressource wird für den Bau verwendet

                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                            logTextArea.appendText("Pixel baut ein Lager bei (" + x + "," + y + "), da Ressourcen in der Nähe sind.\n");
                        });
                    }
                } else if (!nearbyResources) {
                    // Wenn keine Ressourcen in der Nähe sind, könnte es unnötig sein, ein Lager zu bauen
                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                            logTextArea.appendText("Pixel sieht keine Ressourcen in der Nähe und entscheidet sich, kein Lager zu bauen bei (" + x + "," + y + ").\n");
                        });
                    }
                }
            } else {
                // Wenn das Pixel keine Ressourcen hat, um ein Lager zu bauen
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                        logTextArea.appendText("Pixel hat nicht genug Ressourcen, um ein Lager zu bauen bei (" + x + "," + y + ").\n");
                    });
                }
            }
        }
    }

// Hilfsmethoden, um Ressourcen und Gebäude in der Nähe zu erkennen

    private boolean hasNearbyResources(Grid grid) {
        // Überprüfe die benachbarten Zellen auf Ressourcen
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                Cell cell = grid.getCell(nx, ny);
                if (cell != null && cell.getType().equals("resource")) {
                    return true;  // Es gibt Ressourcen in der Nähe
                }
            }
        }
        return false;  // Keine Ressourcen in der Nähe
    }

    private boolean hasNearbyBuilding(Grid grid) {
        // Überprüfe, ob in der Nähe ein Lager existiert
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                Cell cell = grid.getCell(nx, ny);
                if (cell != null && cell.getType().equals("building")) {
                    return true;  // Es gibt bereits ein Lager in der Nähe
                }
            }
        }
        return false;  // Kein Lager in der Nähe
    }

    // Methode für "withdrawFromLager", um Ressourcen aus einem Lager zu entnehmen
    public void withdrawFromLager(Grid grid) {
        // Beispiel für Entnahme aus einem Lager
        Cell cell = grid.getCell(x, y);
        if (cell != null && cell.getType().equals("building")) {
            resources++;
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                    logTextArea.appendText("Pixel entnimmt Ressourcen aus Lager bei (" + x + "," + y + ")\n");
                });
            }
        }
    }

    // Methode für den Kampf zwischen Pixeln
    public void fight(List<Pixel> pixels) {
        for (Pixel p : pixels) {
            if (this != p && isAdjacent(p)) {
                isFighting = true;
                // Beispiel: Einfacher Kampf, bei dem ein Pixel Energie verliert
                this.energy -= 10;
                p.energy -= 10;
                    if (logTextArea != null) {
                        Platform.runLater(() -> {
                        logTextArea.appendText("Pixel bei (" + x + "," + y + ") kämpft mit Pixel bei (" + p.getX() + "," + p.getY() + ")\n");
                    });
                }
            }
        }
    }

    // Methode, um zu überprüfen, ob zwei Pixel benachbart sind
    public boolean isAdjacent(Pixel p) {
        return (Math.abs(this.x - p.x) <= 1 && Math.abs(this.y - p.y) <= 1);
    }

    // Methode für die Paarung von Pixeln
    public void pair(Pixel p) {
        if (isAdjacent(p)) {
            // Beispiel für die Paarung: Pixel erzeugen ein neues Pixel (Nachwuchs)
            if (random.nextBoolean()) {
                Pixel offspring = new Pixel(this.x, this.y);
                // Nachwuchs kann auch Energie oder andere Eigenschaften haben
                if (logTextArea != null) {
                    Platform.runLater(() -> {
                    logTextArea.appendText("Pixel bei (" + x + "," + y + ") paart sich mit Pixel bei (" + p.getX() + "," + p.getY() + ")\n");
                });
            }
            }
        }
    }
}
