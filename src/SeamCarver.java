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
        int xGradient = calculateXGradient(x, y);
        int yGradient = calculateYGradient(x, y);
        return Math.sqrt(xGradient + yGradient);
    }

    private boolean isValidPixel(int x, int y)
    {
        return (x >= 0 && y <= width() - 1);
    }

    private int calculateXGradient(int x, int y)
    {
        int redXRight = 0; int greenXRight = 0; int blueXRight = 0;
        int redXLeft = 0; int greenXLeft = 0; int blueXLeft = 0;

        if (isValidPixel(x + 1, y))
        {
            int rgb = this.picture.getRGB(x + 1, y);
            redXRight  = (rgb >> 16) & 0XFF;
            greenXRight = (rgb >> 8) & 0XFF;
            blueXRight  = (rgb) & 0xff;
        }

        if (isValidPixel(x - 1, y))
        {
            int rgb = this.picture.getRGB(x - 1, y);
            redXLeft  = (rgb >> 16) & 0XFF;
            greenXLeft = (rgb >> 8) & 0XFF;
            blueXLeft  = (rgb) & 0xff;
        }

        return (int) (Math.pow(redXRight - redXLeft, 2) +
                Math.pow(greenXRight - greenXLeft, 2) +
                Math.pow(blueXRight - blueXLeft, 2));
    }

    private int calculateYGradient(int x, int y)
    {
        int redYUp = 0; int greenYUp = 0; int blueYUp = 0;
        int redYDown = 0; int greenYDown = 0; int blueYDown = 0;

        if (isValidPixel(x, y + 1))
        {
            int rgb = this.picture.getRGB(x, y + 1);
            redYDown  = (rgb >> 16) & 0XFF;
            greenYDown = (rgb >> 8) & 0XFF;
            blueYDown  = (rgb) & 0xff;
        }

        if (isValidPixel(x, y - 1))
        {
            int rgb = this.picture.getRGB(x, y - 1);
            redYUp = (rgb >> 16) & 0XFF;
            greenYUp = (rgb >> 8) & 0XFF;
            blueYUp  = (rgb) & 0xff;
        }

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
