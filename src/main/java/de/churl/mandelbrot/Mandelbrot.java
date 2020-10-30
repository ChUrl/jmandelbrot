package de.churl.mandelbrot;

import java.io.BufferedReader;

public class Mandelbrot implements Runnable {

    private final ComplexArea ca;
    private final ImageArea ia;

    private final int iterations;
    private final int[][] image;

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public Mandelbrot(ComplexArea complexArea, ImageArea imageArea,
                      int iterations, int[][] image) {
        this.ca = complexArea;
        this.ia = imageArea;

        this.iterations = iterations;
        this.image = image;
    }

    /**
     * @param c Zahl, für welche die Farbe berechnet wird
     *
     * @return Eine Zahl von 0 bis iterations - 1 falls c nicht im Set, iterations sonst
     */
    private int getColorInSet(Complex c) {
        Complex z = Complex.ZERO();

        //        final double x = c.getRe();
        //        final double y = c.getIm();
        //        double re = 0;
        //        double im = 0;
        for (int i = 0; i < iterations; i++) {
            //            re = re * re - im * im + x;
            //            im = 2 * re * im + y;
            //
            //            if (re * re + im * im > 4) {
            //                return i;
            //            }

            z = z.square().add(c);
            if (z.abs() > 2) {
                return i;
            }
        }

        return iterations;
    }

    /**
     * Bildpunkt wird auf die komplexe Ebene abgebildet.
     * <p>
     * x und y beziehen sich hier auf das Gesamtbild, nicht den Bildausschnitt des Threads.
     *
     * @param x x-Koordinate in der Zahlenebene
     * @param y y-Koordinate in der Zahlenebene
     *
     * @return Zahl c in der komplexen Ebene
     */
    private Complex linearInterpolate(int x, int y) {
        double re = ca.getReMin() + x * (ca.getWidth() / ia.getTotalWidth());
        double im = ca.getImMin() + y * (ca.getHeight() / ia.getTotalHeight());

        return Complex.valueOf(re, im);
    }

    /**
     * x und y beziehen sich hier auf das Gesamtbild, nicht den "Thread-Ausschnitt".
     *
     * @param x x-Koordinate in der Zahlenebene
     * @param y y-Koordinate in der Zahlenebene
     */
    private void colorPixel(int x, int y, int color) {
        int colorRange = (int) (color * (255.0 / iterations));
        image[x][y] = 255 - colorRange;
    }

    /**
     * Bestimmt den Bildausschnitt, welchen der Thread berechnet.
     * <p>
     * Es wird geprüft, ob die komplexe Zahl c, welche von den Bildkoordinaten auf die
     * entsprechende Stelle im Mandelbrot-Set interpoliert wird, in diesem Set liegt.
     */
    @Override
    public void run() {
        for (int x = ia.getXLower(); x < ia.getXUpper(); x++) {
            for (int y = ia.getYLower(); y < ia.getYUpper(); y++) {
                Complex c = linearInterpolate(x, y);

                colorPixel(x, y, getColorInSet(c));
            }
        }
    }
}
