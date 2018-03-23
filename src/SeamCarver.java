import edu.princeton.cs.algs4.Picture;

import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;

public class SeamCarver
{
    private Picture picture;
    private double[][] energies;
    private double[][] cumulativeEnergies;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.picture = picture;
        this.energies = new double[width()][height()];
        this.cumulativeEnergies = new double[width()][height()];

        for (int x = 0; x < width(); x++)
        {
            for (int y = 0; y < height(); y++)
            {
                energies[x][y] = energy(x, y);
            }
        }

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

    }

    public void removeVerticalSeam(int[] seam)
    {

    }
}
