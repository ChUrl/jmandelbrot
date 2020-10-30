package de.churl.mandelbrot;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MainViewController {

    private static final int THREAD_COUNT = 32;
    // Use ratio of 2:3 for optimal result
    private static final int WIDTH = 768;
    private static final int HEIGHT = 512;
    private static final int ITERATIONS = 100;

    private final int[][] render = new int[WIDTH][HEIGHT];
    private final WritableImage image = new WritableImage(WIDTH, HEIGHT);
    private final PixelWriter writer = image.getPixelWriter();

    private final Mandelbrot[] runnables = new Mandelbrot[THREAD_COUNT];
    private final Thread[] threads = new Thread[THREAD_COUNT];

    @FXML
    public ImageView mandelbrotView;

    private ComplexArea ca = ComplexArea.DEFAULT_RATIO(WIDTH, HEIGHT);

    public void initialize() {
        mandelbrotView.requestFocus();

        render();

        mandelbrotView.setImage(image);
    }

    public void handleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            ca = ca.incZoom();
        } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            ca = ca.decZoom();
        }

        render();
    }

    public void handleKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.LEFT) {
            ca = ca.panLeft();
        } else if (keyEvent.getCode() == KeyCode.UP) {
            ca = ca.panUp();
        } else if (keyEvent.getCode() == KeyCode.RIGHT) {
            ca = ca.panRight();
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            ca = ca.panDown();
        }

        render();
    }

    private void renderWithThreads() {
        // Init Threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            runnables[i] = new Mandelbrot(ca, ImageArea.VERTICAL(WIDTH, HEIGHT, THREAD_COUNT, i),
                                          ITERATIONS, render);
            threads[i] = new Thread(runnables[i]);
        }

        // Start threads
        for (Thread thread : threads) {
            thread.start();
        }

        try {
            // Wait for termination
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        long start = System.currentTimeMillis();
        renderWithThreads();
        long end = System.currentTimeMillis();
        System.out.println("Render Duration: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                writer.setColor(x, y, Color.grayRgb(render[x][y]));
            }
        }
        end = System.currentTimeMillis();
        System.out.println("Draw Duration: " + (end - start) + "ms\n");
    }
}
