import edu.princeton.cs.algs4.Picture;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SeamCarver
{
    private Picture picture;
    private Picture externalPicture;
    private int width;
    private int height;
    private int[][] pixels;
    private double[][] energies;
    private double[][] cumulativeEnergies;
    private boolean isPictureTransposed;
    private boolean calculatingVertical;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.picture = new Picture(picture);
        this.externalPicture = picture;
        this.width = picture.width();
        this.height = picture.height();
        this.pixels = new int[width()][height()];
        this.energies = new double[width()][height()];
        this.cumulativeEnergies = new double[width()][height()];
        isPictureTransposed = false;
        calculatingVertical = true;

        createPixels();
        calculateEnergies();
        initializeCumulativeEnergies();
    }

    private void createPixels()
    {
        for (int x = 0; x < this.picture.width(); x++)
        {
            for (int y = 0; y < this.picture.height(); y++)
            {
                this.pixels[x][y] = this.picture.getRGB(x, y);
            }
        }
    }

    private void initializeCumulativeEnergies()
    {
        for (int x = 0; x < width(); x++)
        {
            for (int y = 0; y < height(); y++)
            {
                if (y == 0)
                {
                    cumulativeEnergies[x][y] = 1000;  //TODO: suggest this change to the author of the algorithm
                }
                else
                {
                    cumulativeEnergies[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    private void calculateEnergies()
    {
        for (int x = 0; x < width(); x++)
        {
            for (int y = 0; y < height(); y++)
            {
                energies[x][y] = energy(x, y);
            }
        }
    }

    // current picture
    public Picture picture()
    {
        return this.externalPicture;
    }

    // width of current picture
    public int width()
    {
        if (isPictureTransposed)
            return this.height;
        else
            return this.width;
    }

    // height of current picture
    public int height()
    {
        if (isPictureTransposed)
            return this.width;
        else
            return this.height;
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
        int rgbRight = this.pixels[x + 1][y];
        int redXRight  = (rgbRight >> 16) & 0XFF;
        int greenXRight = (rgbRight >> 8) & 0XFF;
        int blueXRight  = (rgbRight) & 0xff;

        // Getting rgb's of left pixel
        int rgbLeft = this.pixels[x - 1][y];
        int redXLeft  = (rgbLeft >> 16) & 0XFF;
        int greenXLeft = (rgbLeft >> 8) & 0XFF;
        int blueXLeft  = (rgbLeft) & 0xff;

        return (int) (Math.pow(redXRight - redXLeft, 2) +
                Math.pow(greenXRight - greenXLeft, 2) +
                Math.pow(blueXRight - blueXLeft, 2));
    }

    private int calculateYGradient(int x, int y)
    {

        int rgbDown = this.pixels[x][y + 1];
        int redYDown  = (rgbDown >> 16) & 0XFF;
        int greenYDown = (rgbDown >> 8) & 0XFF;
        int blueYDown  = (rgbDown) & 0xff;

        int rgbUp = this.pixels[x][y - 1];
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
        calculatingVertical = false;
        if (!isPictureTransposed)
            transposePicture();

        int[] horizontalSeam = findVerticalSeam();
        calculatingVertical = true;
        return horizontalSeam;
    }

    private void transposePicture()
    {
        // Transpose energies
        double[][] tempArray = new double[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.energies[x], 0, tempArray[x], 0, energies[x].length);
        }

        this.energies = new double[height()][width()];
        for (int y = 0; y < height(); y++)
        {
            for (int x = 0; x < width(); x++)
            {
                this.energies[y][x] = tempArray[x][y];
            }
        }

        // Transpose pixels
        int[][] tempPixelsArray = new int[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.pixels[x], 0, tempPixelsArray[x], 0, pixels[x].length);
        }

        this.pixels = new int[height()][width()];
        for (int y = 0; y < height(); y++)
        {
            for (int x = 0; x < width(); x++)
            {
                this.pixels[y][x] = tempPixelsArray[x][y];
            }
        }


        isPictureTransposed = true;
        // Transpose cumuliativeEnergies
        this.cumulativeEnergies = new double[width()][height()];
        initializeCumulativeEnergies();
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        if (isPictureTransposed && calculatingVertical)
            transposePictureBack();
        // Fill cumulative energies
        for (int y = 0; y < height() - 1; y++)
        {
            for (int x = 0; x < width(); x++)
            {
                relax(x - 1, y + 1, x, y);
                relax(x, y + 1, x, y);
                relax(x + 1, y + 1, x, y);
            }
        }

        // Find the shortest cumulative energy in bottom row.
        double minBottom = Double.MAX_VALUE;
        int minX = -1;
        Stack<Integer> shortestPath = new Stack<>();
        for (int x = 0; x < width(); x++)
        {
            if (cumulativeEnergies[x][height() - 1] < minBottom)
            {
                minBottom = cumulativeEnergies[x][height() - 1];
                minX = x;
                shortestPath.push(minX);
            }
        }

        // Find shortest path from the shortest cumulative vertex in bottom row, working its way up the grid.
        for (int y = height() - 1; y > 0; y--)
        {
            int nextMinX = -1;
            double minEnergy = Double.MAX_VALUE;
            if (isValidPixel(minX - 1, y -1))
            {
                if (cumulativeEnergies[minX - 1][y - 1] < minEnergy)
                {
                    minEnergy = cumulativeEnergies[minX - 1][y - 1];
                    nextMinX = minX - 1;
                }
            }

            if (isValidPixel(minX, y -1))
            {
                if (cumulativeEnergies[minX][y - 1] < minEnergy)
                {
                    minEnergy = cumulativeEnergies[minX ][y - 1];
                    nextMinX = minX;
                }
            }

            if (isValidPixel(minX + 1, y -1))
            {
                if (cumulativeEnergies[minX + 1][y - 1] < minEnergy)
                {
                    minEnergy = cumulativeEnergies[minX + 1][y - 1];
                    nextMinX = minX + 1;
                }
            }

            minX = nextMinX;
            shortestPath.push(minX);
        }

        // Iterate through stack, put elements on array, return the array.
        int[] shortestPathArray = new int[shortestPath.size()];
        for (int i = 0; i < shortestPathArray.length; i++)
        {
            shortestPathArray[i] = shortestPath.pop();
        }

        return shortestPathArray;
    }

    private void transposePictureBack()
    {
        // Transpose energies
        double[][] tempArray = new double[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.energies[x], 0, tempArray[x], 0, energies[x].length);
        }

        this.energies = new double[height()][width()];
        for (int y = 0; y < height(); y++)
        {
            for (int x = 0; x < width(); x++)
            {
                this.energies[y][x] = tempArray[x][y];
            }
        }

        // Transpose pixels
        int[][] tempPixelsArray = new int[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.pixels[x], 0, tempPixelsArray[x], 0, this.pixels[x].length);
        }

        this.pixels = new int[height()][width()];
        for (int y = 0; y < height(); y++)
        {
            for (int x = 0; x < width(); x++)
            {
                this.pixels[y][x] = tempPixelsArray[x][y];
            }
        }


        isPictureTransposed = false;
        // Transpose cumuliativeEnergies
        this.cumulativeEnergies = new double[width()][height()];
        initializeCumulativeEnergies();
    }

    private void relax(int destX, int destY, int sourceX, int sourceY)
    {
        if (isValidPixel(destX, destY))
        {
            if (cumulativeEnergies[destX][destY] > (cumulativeEnergies[sourceX][sourceY] + energies[destX][destY]))
            {
                cumulativeEnergies[destX][destY] = cumulativeEnergies[sourceX][sourceY] + energies[destX][destY];
            }
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        List<Pixel> pixelsToRecalculate = new ArrayList<>();

        // Removing pixels from energy matrix
        // making a copy of energies
        if (isPictureTransposed)
        {
            transposePictureBack();
        }

        // Copying energies grid
        double[][] tempArray = new double[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.energies[x], 0, tempArray[x], 0, this.energies[x].length);
        }

        // Copying pixels grid
        int[][] tempPixelsArray = new int[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.pixels[x], 0, tempPixelsArray[x], 0, this.pixels[x].length);
        }

        this.energies = new double[width()][height() - 1];
        this.pixels = new int[width()][height() - 1];
        int seamIndex = 0;
        for (int x = 0; x < width(); x++)
        {
            // Removing seam in energies grid
            System.arraycopy(tempArray[x], 0, this.energies[x], 0, seam[seamIndex]);
            System.arraycopy(tempArray[x], seam[seamIndex] + 1, this.energies[x], seam[seamIndex], tempArray[x].length - (seam[seamIndex] + 1));

            // Removing seam in pixels grid
            System.arraycopy(tempPixelsArray[x], 0, this.pixels[x], 0, seam[seamIndex]);
            System.arraycopy(tempPixelsArray[x], seam[seamIndex] + 1, this.pixels[x], seam[seamIndex], tempPixelsArray[x].length - (seam[seamIndex] + 1));

            pixelsToRecalculate.add(new Pixel(seam[seamIndex] - 1, x)); // left
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x)); // right
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x - 1)); // up
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x + 1)); // down
            seamIndex++;
        }

        height--;

        // Recalculate energies around removals
        for (Pixel pixel : pixelsToRecalculate)
        {
            try
            {
                this.energies[pixel.x][pixel.y] = energy(pixel.x, pixel.y);
            }
            catch (IllegalArgumentException e)
            {
                // do nothing;
            }
            catch (ArrayIndexOutOfBoundsException a)
            {
                // do nothing
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        List<Pixel> pixelsToRecalculate = new ArrayList<>();

        // Removing pixels from energy matrix
        // making a copy of energies
        if (!isPictureTransposed)
        {
            transposePicture();
        }

        // Copying energies grid
        double[][] tempArray = new double[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.energies[x], 0, tempArray[x], 0, this.energies[x].length);
        }

        // Copying pixels grid
        int[][] tempPixelsArray = new int[width()][height()];
        for (int x = 0; x < width(); x++)
        {
            System.arraycopy(this.pixels[x], 0, tempPixelsArray[x], 0, this.pixels[x].length);
        }

        this.energies = new double[width()][height() - 1];
        this.pixels = new int[width()][height() - 1];
        int seamIndex = 0;
        for (int x = 0; x < width(); x++)
        {
            // Removing seam in energies grid
            System.arraycopy(tempArray[x], 0, this.energies[x], 0, seam[seamIndex]);
            System.arraycopy(tempArray[x], seam[seamIndex] + 1, this.energies[x], seam[seamIndex], tempArray[x].length - (seam[seamIndex] + 1));

            // Removing seam in pixels grid
            System.arraycopy(tempPixelsArray[x], 0, this.pixels[x], 0, seam[seamIndex]);
            System.arraycopy(tempPixelsArray[x], seam[seamIndex] + 1, this.pixels[x], seam[seamIndex], tempPixelsArray[x].length - (seam[seamIndex] + 1));

            pixelsToRecalculate.add(new Pixel(seam[seamIndex] - 1, x)); // left
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x)); // right
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x - 1)); // up
            pixelsToRecalculate.add(new Pixel(seam[seamIndex], x + 1)); // down
            seamIndex++;
        }

        width--;
        transposePictureBack();

        // Recalculate energies around removals
        for (Pixel pixel : pixelsToRecalculate)
        {
            try
            {
                this.energies[pixel.x][pixel.y] = energy(pixel.x, pixel.y);
            }
            catch (IllegalArgumentException e)
            {
                // do nothing;
            }
            catch (ArrayIndexOutOfBoundsException a)
            {
                // do nothing
            }
        }
    }


    private class Pixel
    {
        public int x;
        public int y;

        public Pixel(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

    }
}
