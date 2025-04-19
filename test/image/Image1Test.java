package image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * JUnit tests for the Image1 kernel component.
 *
 * @author Hosanna
 */
public class Image1Test {
    @Test
    public void testClipWithinRange() {
        Image1 img = new Image1();
        assertEquals(100, img.clip(100));
    }

    @Test
    public void testClipBelowRange() {
        Image1 img = new Image1();
        assertEquals(0, img.clip(-10));
    }

    @Test
    public void testClipAboveRange() {
        Image1 img = new Image1();
        assertEquals(256, img.clip(999));
    }

    @Test
    public void testGetRowCol() {
        Image1 img = new Image1(4, 4, new int[] { 0, 0, 0 });
        assertEquals(2, img.getRow(10));
        assertEquals(2, img.getCol(10));
    }

    @Test
    public void testGetTotalPixel() {
        Image1 img = new Image1(3, 2, new int[] { 0, 0, 0 });
        assertEquals(6, img.getTotalPixel());
    }

    @Test
    public void testContains() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        assertTrue(img.contains(3));
        assertFalse(img.contains(4));
    }

    @Test
    public void testPaintAndErase() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.paint(1, new int[] { 123, 234, 45 });
        int[][][] pixels = img.getPixels();
        assertArrayEquals(new int[] { 123, 234, 45 }, pixels[0][1]);

        img.erase(1);
        assertArrayEquals(new int[] { 0, 0, 0 }, pixels[0][1]);
    }

    @Test
    public void testNewInstance() {
        Image1 img = new Image1();
        try {
            img.newInstance();
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

    @Test
    public void testTransferFrom() {
        Image1 img = new Image1();
        try {
            img.transferFrom(new Image1());
            fail("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }

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
        assertEquals(256, img.clip(999));
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

    @Test
    public void testSaveImage() {
        Image1 img = new Image1(2, 2, new int[] { 50, 100, 150 });
        img.saveImage("test_output_image");
        // Manual verification required or check that no exception was thrown
    }

    @Test
    public void testSetColorUpdatesAllPixels() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.setColor(new int[] { 100, 100, 100 });
        int[][][] px = img.getPixels();
        for (int r = 0; r < img.getHeight(); ++r) {
            for (int c = 0; c < img.getWidth(); ++c) {
                assertArrayEquals(new int[] { 100, 100, 100 }, px[r][c]);
            }
        }
    }

    @Test
    public void testGetWidth() {
        Image1 img = new Image1(5, 3, new int[] { 0, 0, 0 });
        assertEquals(5, img.getWidth());
    }

    @Test
    public void testGetHeight() {
        Image1 img = new Image1(5, 3, new int[] { 0, 0, 0 });
        assertEquals(3, img.getHeight());
    }

    @Test
    public void testGetTotalPixelSize() {
        Image1 img = new Image1(4, 2, new int[] { 0, 0, 0 });
        assertEquals(8, img.getTotalPixel());
    }

    @Test
    public void testGetPixelsReturnsReference() {
        Image1 img = new Image1(1, 1, new int[] { 123, 231, 12 });
        int[][][] px = img.getPixels();
        assertArrayEquals(new int[] { 123, 231, 12 }, px[0][0]);
    }

    @Test
    public void testCopyFromCopiesCorrectly() {
        Image img1 = new Image1(2, 2, new int[] { 111, 111, 111 });
        Image img2 = new Image1(2, 2, new int[] { 0, 0, 0 });
        img2.copyFrom(img1);
        assertArrayEquals(img1.getPixels(), img2.getPixels());
    }

    @Test
    public void testClearSetsAllPixelsBlack() {
        Image1 img = new Image1(2, 2, new int[] { 100, 100, 100 });
        img.clear();
        int[][][] px = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(new int[] { 0, 0, 0 }, px[r][c]);
            }
        }
    }

    @Test
    public void testClearImageSetsAllPixelsBlack() {
        Image1 img = new Image1(2, 2, new int[] { 200, 200, 200 });
        img.clearImage();
        int[][][] px = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(new int[] { 0, 0, 0 }, px[r][c]);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeWidth() {
        new Image1(-1, 5, new int[] { 0, 0, 0 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNegativeHeight() {
        new Image1(5, -1, new int[] { 0, 0, 0 });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullColor() {
        new Image1(5, 5, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorInvalidColorLength() {
        new Image1(5, 5, new int[] { 255, 255 }); // Only 2 channels
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPaintInvalidIndex() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.paint(5, new int[] { 255, 0, 0 }); // Index out of bounds
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPaintNullColor() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.paint(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPaintInvalidColorLength() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.paint(1, new int[] { 255 }); // Only one channel
    }

    @Test
    public void testSetColorExtremeValues() {
        Image1 img = new Image1(2, 2, new int[] { 0, 0, 0 });
        img.setColor(new int[] { -50, 1000, 300 });
        int[][][] px = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(new int[] { 0, 256, 256 }, px[r][c]);
            }
        }
    }

    @Test
    public void testCopyFromSameImageInstance() {
        Image1 img = new Image1(2, 2, new int[] { 10, 20, 30 });
        img.copyFrom(img); // Should not crash
        int[][][] px = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(new int[] { 10, 20, 30 }, px[r][c]);
            }
        }
    }
}