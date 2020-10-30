package de.churl.mandelbrot;

public final class ComplexArea {

    private final double reMin;
    private final double reMax;
    private final double imMin;
    private final double imMax;

    private ComplexArea(double reMin, double reMax, double imMin, double imMax) {
        this.reMin = reMin;
        this.reMax = reMax;
        this.imMin = imMin;
        this.imMax = imMax;
    }

    /**
     * Bounds for the complete Mandelbrot-Set: x[-2, 1] and y[-1, 1].
     * <p>
     * Requires ImageRatio of 2:3.
     */
    public static ComplexArea DEFAULT() {
        return new ComplexArea(-2, 1, -1, 1);
    }

    public static ComplexArea DEFAULT_RATIO(int width, int height) {
        double ratio = (double) width / height;
        double complexHeight = DEFAULT().getWidth() / ratio;
        double imMax = complexHeight - Math.abs(DEFAULT().imMin);

        return new ComplexArea(DEFAULT().reMin, DEFAULT().reMax, DEFAULT().imMin, imMax);
    }

    /**
     * Lower bound for real ("x") component.
     */
    public double getReMin() {
        return reMin;
    }

    /**
     * Upper bound for real ("x") component.
     */
    public double getReMax() {
        return reMax;
    }

    /**
     * Lower bound for imaginary ("y") component.
     */
    public double getImMin() {
        return imMin;
    }

    /**
     * Upper bound for imaginary ("y") component.
     */
    public double getImMax() {
        return imMax;
    }

    public double getWidth() {
        return Math.abs(reMax - reMin);
    }

    public double getHeight() {
        return Math.abs(imMax - imMin);
    }

    /**
     * Calculate imMax from reMin/reMax and constant ratio.
     */
    private ComplexArea normalize(double reMin, double reMax, double imMin) {
        double ratio = getWidth() / getHeight();
        double complexHeight = Math.abs(reMax - reMin) / ratio;
        double imMax = complexHeight - Math.abs(imMin);

        return new ComplexArea(reMin, reMax, imMin, imMax);
    }

    /**
     * reMin und reMax werden gleichförmig angepasst. Eine Stufe sind 15% Zoom.
     */
    public ComplexArea incZoom() {
        return normalize(reMin + 0.075 * getWidth(), reMax - 0.075 * getWidth(),
                         imMin + 0.075 * getHeight());
    }

    /**
     * reMin und reMax werden gleichförmig angepasst. Eine Stufe sind 15% Zoom.
     */
    public ComplexArea decZoom() {
        return normalize(reMin - 0.075 * getWidth(), reMax + 0.075 * getWidth(),
                         imMin - 0.075 * getHeight());
    }

    public ComplexArea panLeft() {
        return new ComplexArea(reMin - 0.075 * getWidth(), reMax - 0.075 * getWidth(),
                               imMin, imMax);
    }

    public ComplexArea panRight() {
        return new ComplexArea(reMin + 0.075 * getWidth(), reMax + 0.075 * getWidth(),
                               imMin, imMax);
    }

    public ComplexArea panUp() {
        return new ComplexArea(reMin, reMax,
                               imMin - 0.075 * getHeight(), imMax - 0.075 * getHeight());
    }

    public ComplexArea panDown() {
        return new ComplexArea(reMin, reMax,
                               imMin + 0.075 * getHeight(), imMax + 0.075 * getHeight());
    }
}
