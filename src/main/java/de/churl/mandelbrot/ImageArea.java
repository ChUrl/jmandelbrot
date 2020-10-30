package de.churl.mandelbrot;

public final class ImageArea {

    private final int totalWidth;
    private final int totalHeight;
    private final int areaWidth;
    private final int areaHeight;
    private final int xLower;
    private final int yLower;

    private ImageArea(int totalWidth, int totalHeight,
                      int areaWidth, int areaHeight,
                      int xLower, int yLower) {
        this.totalWidth = totalWidth;
        this.totalHeight = totalHeight;
        this.areaWidth = areaWidth;
        this.areaHeight = areaHeight;
        this.xLower = xLower;
        this.yLower = yLower;
    }

    /**
     * The total image is sliced vertically in n parts of the same size, where n is the threadCount.
     * SubArea Bounds are determined by the threadNum.
     */
    public static ImageArea VERTICAL(int width, int height, int threadCount, int threadNum) {
        return new ImageArea(width, height,
                             width / threadCount, height,
                             (width / threadCount) * threadNum, 0);
    }

    /**
     * Total image width
     */
    public int getTotalWidth() {
        return totalWidth;
    }


    /**
     * Total image height
     */
    public int getTotalHeight() {
        return totalHeight;
    }

    /**
     * Lower X-Bounds for Image-Area
     */
    public int getXLower() {
        return xLower;
    }


    /**
     * Upper X-Bounds for Image-Area
     */
    public int getXUpper() {
        return xLower + areaWidth;
    }


    /**
     * Lower Y-Bounds for Image-Area
     */
    public int getYLower() {
        return yLower;
    }


    /**
     * Upper Y-Bounds for Image-Area
     */
    public int getYUpper() {
        return yLower + areaHeight;
    }
}
