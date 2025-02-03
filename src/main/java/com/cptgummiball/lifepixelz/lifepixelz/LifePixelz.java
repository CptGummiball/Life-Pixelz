package com.cptgummiball.lifepixelz.lifepixelz;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class LifePixelz extends Application {
    private static final int CELL_SIZE = 10;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private Grid grid;
    private List<Pixel> pixels;
    private Canvas canvas;
    private Random random = new Random();
    private Map<List<Pixel>, Color> teamColors = new HashMap<>();
    private TextArea logTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        grid = new Grid(WIDTH, HEIGHT);
        pixels = new ArrayList<>();
        canvas = new Canvas(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);

        logTextArea = new TextArea();
        logTextArea.setEditable(false);
        logTextArea.setWrapText(true);

        // Stellen Sie sicher, dass der ScrollPane das TextArea enthält und die richtige Größe hat
        ScrollPane scrollPane = new ScrollPane(logTextArea);
        scrollPane.setFitToWidth(true);  // ScrollPane passt sich der Breite an
        scrollPane.setPrefWidth(300);  // Maximale Breite des ScrollPane
        scrollPane.setPrefHeight(HEIGHT * CELL_SIZE);  // Höhe des ScrollPane auf die gleiche wie das Canvas setzen

        BorderPane root = new BorderPane();
        root.setCenter(new StackPane(canvas));  // Canvas in der Mitte des Layouts
        root.setRight(scrollPane);  // ScrollPane nach rechts verschieben

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LifePixelz Simulation");
        primaryStage.show();

        // Initialisierung der Pixel
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            pixels.add(new Pixel(x, y));
        }

        new Thread(this::runSimulation).start();
    }

    private void runSimulation() {
        while (true) {
            update();
            render();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        List<Pixel> toRemove = new ArrayList<>();
        for (Pixel p : pixels) {
            if (p.getLogTextArea() == null) {
                p.setLogTextArea(logTextArea);
            }
            p.move(grid);
            p.act(grid);
            p.build(grid);
            p.withdrawFromLager(grid);
            p.fight(pixels);

            // Paaren
            for (Pixel other : pixels) {
                if (other != p && p.isAdjacent(other)) {
                    p.pair(other);
                }
            }

            if (!p.isAlive()) {
                toRemove.add(p);
            }
        }

        pixels.removeAll(toRemove);
        if (pixels.size() < 100) {
            pixels.add(new Pixel(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }

        grid.updateResources();  // Ressourcen aktualisieren
        grid.regenerateResources();  // Ressourcen erneuern
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Cell cell = grid.getCell(x, y);
                if (cell.getType().equals("resource")) {
                    gc.setFill(Color.GREEN);
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                } else if (cell.hasBuilding()) {
                    gc.setFill(Color.BROWN);
                    gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        for (Pixel p : pixels) {
            gc.setFill(Color.BLUE);
            gc.fillOval(p.getX() * CELL_SIZE, p.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}


