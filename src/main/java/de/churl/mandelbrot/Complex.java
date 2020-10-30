package de.churl.mandelbrot;

public final class Complex {

    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex ZERO() {
        return new Complex(0, 0);
    }

    public static Complex valueOf(double re, double im) {
        return new Complex(re, im);
    }

    public double abs() {
        return Math.hypot(re, im);
    }

    public Complex add(Complex c) {
        double reSum = re + c.re;
        double imSum = im + c.im;

        return new Complex(reSum, imSum);
    }

    public Complex square() {
        double reSquare = re * re;
        double imSquare = im * im;

        return new Complex(reSquare - imSquare, 2 * re * im);
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }
}
