package image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

/**
 * JUnit tests for secondary methods in the Image component.
 *
 * Tests clearImage, setColor, randomizeImage, GaussianBlur.
 *
 * @author Hosanna
 */
public class ImageSecondaryTest {
    //TODO constucrtor test
    //TODO

    @Test
    public void testClearImage() {
        Image img = new Image1(2, 2, new int[] { 50, 100, 150 });
        img.clearImage();
        int[][][] pixels = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(new int[] { 0, 0, 0 }, pixels[r][c]);
            }
        }
    }

    @Test
    public void testSetColor() {
        Image img = new Image1(2, 2, new int[] { 0, 0, 0 });
        int[] newColor = { 120, 180, 240 };
        img.setColor(newColor);
        int[][][] pixels = img.getPixels();
        for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                assertArrayEquals(newColor, pixels[r][c]);
            }
        }
    }

    @Test
    public void testRandomizeImage_changesPixels() {
        Image img = new Image1(2, 2, new int[] { 0, 0, 0 });
        int[][][] before = this.deepCopy(img.getPixels());
        img.randomizeImage();
        int[][][] after = img.getPixels();

        boolean changed = false;
        outer: for (int r = 0; r < 2; ++r) {
            for (int c = 0; c < 2; ++c) {
                if (!Arrays.equals(before[r][c], after[r][c])) {
                    changed = true;
                    break outer;
                }
            }
        }
        assertTrue("At least one pixel should change after randomizeImage()",
                changed);
    }

    @Test
    public void testGaussianBlur_blursTopLeftPair() {
        Image1 img = new Image1(2, 2, new int[] { 100, 150, 200 });
        img.paint(3, new int[] { 200, 100, 50 }); // change bottom-right pixel
        img.GaussianBlur();

        int[][][] pixels = img.getPixels();
        int[] topLeft = pixels[1][1];
        assertTrue("Pixel values should be blurred (not identical to original)",
                !(topLeft[0] == 200 && topLeft[1] == 100 && topLeft[2] == 50));
    }

    // helper method to clone 3D array
    private int[][][] deepCopy(int[][][] original) {
        int[][][] copy = new int[original.length][][];
        for (int i = 0; i < original.length; ++i) {
            copy[i] = new int[original[i].length][];
            for (int j = 0; j < original[i].length; ++j) {
                copy[i][j] = Arrays.copyOf(original[i][j],
                        original[i][j].length);
            }
        }
          return copy;
    }
}