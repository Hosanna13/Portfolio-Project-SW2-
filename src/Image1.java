
/*
 * Image1 is the kernel implementation of the Image component using a 3D array
 * of integers to store pixel RGB values. This class provides direct access
 * to image manipulation at the kernel level.
 *
 * @author Hosanan Otchere
 */
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Image1 is the kernel implementation of the Image component using a 3D integer
 * array to represent pixels in a 2D grid. Each pixel is an RGB triplet where
 * each channel value (red, green, blue) is clipped to the range [0, 255].
 *
 * <p>
 * This class directly implements all kernel methods for accessing and modifying
 * image data. The representation is: {@code pixels[height][width][3]} where the
 * third dimension holds the red, green, and blue channels in order.
 *
 * <p>
 * This implementation assumes all method inputs are non-null and valid
 * according to the contracts defined in ImageKernel.
 *
 * @convention - The pixels array must have dimensions [height][width][3] - Each
 *             color value must be in the range [0, 255] - Width and height must
 *             be greater than 0
 *
 * @correspondence - this = a 2D image grid where: this.pixels[r][c][0] = red
 *                 value of pixel (r, c) this.pixels[r][c][1] = green value of
 *                 pixel (r, c) this.pixels[r][c][2] = blue value of pixel (r,
 *                 c)
 *
 * @author Hosanan Otchere
 * @dotNumber otchere.13
 * @date 2025-04-04
 */
public class Image1 extends ImageSecondary {
    /*
     * Convention: - pixels is a 3D array of dimensions [height][width][3] -
     * Each pixel contains exactly 3 color channels (RGB), each in [0, 255] -
     * width > 0 and height > 0
     *
     * Correspondence: - this = a grid of pixels representing an image -
     * this.pixels[r][c][0] = red channel of pixel at (r, c) -
     * this.pixels[r][c][1] = green channel of pixel at (r, c) -
     * this.pixels[r][c][2] = blue channel of pixel at (r, c)
     */

    /**
     * The width of the image in pixels.
     */
    private int width;
    /*
     * The height of the image in pixels.
     */
    private int height;
    /*
     * The 3D array of pixel data where each pixel is represented as an RBG
     * triplet in the format [height][width][3]
     */
    private int[][][] pixels;

    /**
     * The total number of pixels in the image (width * height).
     */
    private int totalPixel;

    /**
     * Constructs a new Image1 object with the specified width, height, and
     * initial color for all pixels.
     *
     * @param width
     *            the width of the image (must be > 0)
     * @param height
     *            the height of the image (must be > 0)
     * @param initialColor
     *            the RGB color used to fill all pixels initially; must be an
     *            array of length 3
     * @requires width > 0, height > 0, and initialColor.length == 3
     * @ensures this.pixels is initialized to [height][width][3] with all values
     *          set to the given initialColor (clipped to [0, 255])
     */
    public Image1(int width, int height, int[] initialColor) {
        if (width <= 0 || height <= 0) { // vaild dimentions check
            throw new IllegalArgumentException(
                    "Invaild Image Dimensions, diemnsions must be postive.");
        }

        if (initialColor == null || initialColor.length != RGB_CHANNELS) {
            throw new IllegalArgumentException(
                    "Intial color must be an array of 3 RGB values.");
        }

        initialColor[0] = this.clip(initialColor[0]);
        initialColor[1] = this.clip(initialColor[1]);
        initialColor[2] = this.clip(initialColor[2]);

        this.width = width;
        this.height = height;

        this.pixels = new int[height][width][RGB_CHANNELS];

        for (int r = 0; r < height; ++r) {
            for (int c = 0; c < width; c++) {
                this.pixels[r][c][0] = initialColor[0]; // R
                this.pixels[r][c][1] = initialColor[1]; // G
                this.pixels[r][c][2] = initialColor[2]; //  B
            }
        }
    }

    @Override
    public final int clip(int val) {
        return Math.max(0, Math.min(MAX_COLOR_VALUE, val));
    }

    @Override
    public final int getRow(int indx) {
        return indx / this.width;
    }

    @Override
    public final int getCol(int indx) {
        return indx % this.width;
    }

    @Override
    public final int gettotalPixel() {
        return this.width * this.height;
    }

    /*
     * KENRAL METHODS
     */
    @Override
    public final void copyFrom(Image o) {
        for (int i = 0; i < this.gettotalPixel(); ++i) {
            if (this.contains(i) && o.contains(i)) {
                for (int r = 0; r < this.getHeight(); ++r) {
                    for (int c = 0; c < this.getWidth(); ++r) {
                        int red = this.getPixels()[r][c][0];
                        int green = this.getPixels()[r][c][1];
                        int blue = this.getPixels()[r][c][2];

                        int red1 = o.getPixels()[r][c][0];
                        int green1 = o.getPixels()[r][c][1];
                        int blue1 = o.getPixels()[r][c][2];
                    }
                }
            }
        }
    }

    @Override
    public final boolean contains(int index) {
        int row = this.getRow(index);
        int col = this.getCol(index);
        return (row >= 0 && row < this.height)
                && (col >= 0 && col < this.width);
    }

    @Override
    public final void paint(int index, int[] color) {
        if (this.contains(index)) {
            int row = this.getRow(index);
            int col = this.getCol(index);

            this.pixels[row][col][0] = this.clip(color[0]);
            this.pixels[row][col][1] = this.clip(color[1]);
            this.pixels[row][col][2] = this.clip(color[2]);
        } else {
            try {
                throw new IllegalAccessException("Invaild index ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public final void erase(int index) {
        if (this.contains(index)) {
            int row = this.getRow(index);
            int col = this.getCol(index);
            this.pixels[row][col][0] = 0;
            this.pixels[row][col][1] = 0;
            this.pixels[row][col][2] = 0;
        }
    }

    @Override
    public final void printImage() {
        for (int r = 0; r < this.height; ++r) {
            for (int c = 0; c < this.width; ++c) {
                int red = this.pixels[r][c][0];
                int green = this.pixels[r][c][1];
                int blue = this.pixels[r][c][0];
                System.out.printf("(%3d, %3d, %3d) ", red, green, blue);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public final void saveImage(String filename) {
        String outputName = filename;
        if (!outputName.contains("png")) {
            outputName += ".png";
        }
        BufferedImage image = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < this.height; ++r) {
            for (int c = 0; c < this.width; ++c) {
                int red = this.pixels[r][c][0];
                int green = this.pixels[r][c][1];
                int blue = this.pixels[r][c][2];
                int rgb = (red << RED_BALANCE) | (green << GREEN_BALANCE)
                        | blue;
                image.setRGB(r, c, rgb);
            }
        }
        File output = new File(filename);
        try {
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as: " + filename);
        } catch (IOException e) {
            System.out.println("Failed Image Not Saved");
            e.printStackTrace();
        }
    }

    @Override
    public final void clearImage() {
        this.totalPixel = this.gettotalPixel();
        for (int i = 0; i < this.totalPixel; ++i) {
            this.erase(i);
        }
    }

    @Override
    public final void randomizeImage() {
        Random rand = new Random();
        this.totalPixel = this.gettotalPixel();
        for (int i = 0; i < this.totalPixel; ++i) {
            int[] color = { rand.nextInt(MAX_COLOR_VALUE),
                    rand.nextInt(MAX_COLOR_VALUE),
                    rand.nextInt(MAX_COLOR_VALUE) };
            this.paint(i, color);
        }
    }

    @Override
    public final void setColor(int[] color) {
        Random rand = new Random();
        for (int i = 0; i < this.totalPixel; ++i) {
            this.paint(i, color);
        }
    }

}