import edu.princeton.cs.algs4.Picture;

public class SeamCarver
{
    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.picture = picture;
    }

    // current picture
    public Picture picture()
    {
        return null;
    }

    // width of current picture
    public int width()
    {
        return this.picture.width();
    }

    // height of current picture
    public int height()
    {
        return this.picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y)
    {
        if (!isValidPixel(x, y)) throw new IllegalArgumentException();
        if (isPixelInBorder(x, y)) return 1000.00;
        int xGradient = calculateXGradient(x, y);
        int yGradient = calculateYGradient(x, y);
        return Math.sqrt(xGradient + yGradient);
    }

    private boolean isValidPixel(int x, int y)
    {
        return ((x >= 0 && x <= width() - 1) && (y >= 0 && y <= height() - 1));
    }

    private boolean isPixelInBorder(int x, int y)
    {
        return ((x == 0 || x == width() - 1) || (y == 0 || y == height() - 1));
    }

    private int calculateXGradient(int x, int y)
    {
        // Getting rgb's of right pixel
        int rgbRight = this.picture.getRGB(x + 1, y);
        int redXRight  = (rgbRight >> 16) & 0XFF;
        int greenXRight = (rgbRight >> 8) & 0XFF;
        int blueXRight  = (rgbRight) & 0xff;

        // Getting rgb's of left pixel
        int rgbLeft = this.picture.getRGB(x - 1, y);
        int redXLeft  = (rgbLeft >> 16) & 0XFF;
        int greenXLeft = (rgbLeft >> 8) & 0XFF;
        int blueXLeft  = (rgbLeft) & 0xff;


        return (int) (Math.pow(redXRight - redXLeft, 2) +
                Math.pow(greenXRight - greenXLeft, 2) +
                Math.pow(blueXRight - blueXLeft, 2));
    }

    private int calculateYGradient(int x, int y)
    {

        int rgbDown = this.picture.getRGB(x, y + 1);
        int redYDown  = (rgbDown >> 16) & 0XFF;
        int greenYDown = (rgbDown >> 8) & 0XFF;
        int blueYDown  = (rgbDown) & 0xff;

        int rgbUp = this.picture.getRGB(x, y - 1);
        int redYUp = (rgbUp >> 16) & 0XFF;
        int greenYUp = (rgbUp >> 8) & 0XFF;
        int blueYUp  = (rgbUp) & 0xff;

        return (int) (Math.pow(redYDown - redYUp, 2) +
                Math.pow(greenYDown - greenYUp, 2) +
                Math.pow(blueYDown - blueYUp, 2));
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()
    {
        return null;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        return null;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {

    }

    public void removeVerticalSeam(int[] seam)
    {

    }
}
