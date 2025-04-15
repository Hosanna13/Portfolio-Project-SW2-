package image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit tests for the Image1 kernel component.
 *
 * @author Hosanna
 */
public class Image1Test {

    @Test
    public void testGetRow_col0_in3x3Image() {
        Image img = new Image1(3, 3, new int[] { 0, 0, 0 });
        assertEquals(0, img.getRow(0)); // top-left pixel
        assertEquals(1, img.getRow(3)); // second row, first column
    }

    @Test
    public void testGetCol_index5_in3x3Image() {
        Image img = new Image1(3, 3, new int[] { 0, 0, 0 });
        assertEquals(2, img.getCol(5)); // row 1, col 2
    }

    @Test
    public void testClipValueInRange() {
        Image img = new Image1();
        assertEquals(100, img.clip(100));
        assertEquals(0, img.clip(-10));
        assertEquals(255, img.clip(999));
    }

    @Test
    public void testPaintAndGetPixels() {
        Image img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.paint(2, new int[] { 10, 20, 30 });
        int[][][] px = img.getPixels();
        assertArrayEquals(new int[] { 10, 20, 30 }, px[1][0]); // index 2 maps to row 1, col 0
    }

    @Test
    public void testErasePixel() {
        Image img = new Image1(2, 2, new int[] { 255, 255, 255 });
        img.erase(3); // bottom-right
        int[][][] px = img.getPixels();
        assertArrayEquals(new int[] { 0, 0, 0 }, px[1][1]);
    }

    @Test
    public void testContains_validAndInvalid() {
        Image img = new Image1(2, 2, new int[] { 0, 0, 0 });
        assertTrue(img.contains(3)); // valid
        assertFalse(img.contains(4)); // invalid (2x2 = 4 total pixels, last valid index = 3)
    }

    @Test
    public void testCopyFrom() {
        Image img1 = new Image1(2, 2, new int[] { 100, 150, 200 });
        Image img2 = new Image1(2, 2, new int[] { 0, 0, 0 });

        img2.copyFrom(img1);
        assertArrayEquals(img1.getPixels(), img2.getPixels());
    }

    @Test
    public void testGetTotalPixel_2x3() {
        Image img = new Image1(2, 3, new int[] { 0, 0, 0 });
        assertEquals(6, img.getTotalPixel());
    }

}