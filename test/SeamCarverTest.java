import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SeamCarverTest
{
    @Test
    public void testEnergy()
    {
        File file = new File(getClass().getResource("3x4.png").getFile());
        Picture picture = new Picture(file);
        SeamCarver seamCarver = new SeamCarver(picture);
        assertEquals(1000.00, seamCarver.energy(0, 0), 0.01);
        assertEquals(1000.00, seamCarver.energy(1, 0), 0.01);
        assertEquals(1000.00, seamCarver.energy(2, 0), 0.01);
        assertEquals(1000.00, seamCarver.energy(0, 1), 0.01);
        assertEquals(228.53, seamCarver.energy(1, 1), 0.01);
        assertEquals(1000.00, seamCarver.energy(2, 1), 0.01);
        assertEquals(1000.00, seamCarver.energy(0, 2), 0.01);
        assertEquals(228.09, seamCarver.energy(1, 2), 0.01);
        assertEquals(1000.00, seamCarver.energy(2, 2), 0.01);
        assertEquals(1000.00, seamCarver.energy(0, 3), 0.01);
        assertEquals(1000.00, seamCarver.energy(1, 3), 0.01);
        assertEquals(1000.00, seamCarver.energy(2, 3), 0.01);
    }
}
