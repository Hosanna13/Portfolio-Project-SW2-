package image;

/**
 * Abstract class implementing secondary methods for the Image component
 * using only kernel methods. This class includes convenience methods like
 * clearing, printing, saving, and manipulating the image.
 *
 * These methods do not define or modify the underlying representation—they
 * rely solely on the kernel operations defined in ImageKernel.
 *
 * All values are assumed to be within the valid RGB range [0, 255], and the
 * structure is assumed to be non-null and correctly initialized.
 */
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ImageSecondary is an abstract class that provides secondary operations for
 * the Image component. These methods are built strictly using kernel methods
 * defined in the ImageKernel interface.
 *
 * <p>
 * This includes methods for clearing, printing, setting color, saving, applying
 * effects like Gaussian blur, and more. The class does not modify the
 * underlying representation directly but instead operates through the kernel’s
 * public API.
 *
 * <p>
 * By using this structure, all image behavior is layered and modular, making it
 * easy to extend or swap out the internal image representation.
 *
 * @mathmodel type Image is modeled by a 2D array of pixels where each pixel is
 *            a triple (R, G, B) with each component in [0, 255]
 *
 * @initially <pre>
 * (width, height, initialColor):
 *   ensures all pixels are set to initialColor
 * </pre>
 *
 * @convention - All pixel access is done through kernel methods - No fields or
 *             internals are modified directly
 *
 * @correspondence - This class’s behavior matches that of an abstract image
 *                 editor using RGB pixel values.
 */

public abstract class ImageSecondary implements Image {
    private static final int MIN_COLOR_VALUE = 0;
    private static final int MAX_COLOR_VALUE = 255;
    private static final int RGB_CHANNELS = 3;
    private static final int RED_BALANCE = 16;
    private static final int GREEN_BALANCE = 8;
    private static final int GAUSSIAN_DIVISOR = 2;

    /**
     * Sets all pixels in the image to black (0, 0, 0).
     *
     * @updates this
     * @ensures every pixel in this is set to black
     */
    @Override
    public void clearImage() {
        for (int i = 0; i < this.getTotalPixel(); ++i) {
            if (this.contains(i)) {
                this.erase(i);
            }
        }
    }

    /**
     * Sets every pixel in the image to a random RGB value.
     *
     * @updates this
     * @ensures each pixel is painted with a random color where 0 <= R, G, B <=
     *          255
     */
    @Override
    public void randomizeImage() {
        Random rand = new Random();
        for (int i = 0; i < this.getTotalPixel(); ++i) {
            int[] color = { this.clip(rand.nextInt(MAX_COLOR_VALUE)),
                    this.clip(rand.nextInt(MAX_COLOR_VALUE)),
                    this.clip(rand.nextInt(MAX_COLOR_VALUE)) };
            this.paint(i, color);
        }
    }

    /**
     * Sets every pixel in the image to the given RGB color.
     *
     * @param color
     *            the RGB color array to apply
     * @updates this
     * @requires color.length == 3
     * @ensures every pixel is painted with the provided color
     */
    @Override
    public void setColor(int[] color) {
        for (int i = 0; i < this.getTotalPixel(); ++i) {
            if (this.contains(i)) {
                this.paint(i, color);
            }
        }
    }

    /**
     * Saves the image to the specified file name.
     *
     * @param filename
     *            the name to save the image under
     * @ensures image is saved as a .png file
     */
    @Override
    public void saveImage(String filename) {
        String outputName = filename;
        if (!filename.contains("png")) {
            outputName += ".png";
        }
        BufferedImage image = new BufferedImage(this.getWidth(),
                this.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < this.getHeight(); ++r) {
            for (int c = 0; c < this.getWidth(); ++c) {
                int[][][] pixels = this.getPixels();
                int red = pixels[r][c][0];
                int green = pixels[r][c][1];
                int blue = pixels[r][c][2];
                int rgb = (red << RED_BALANCE) | (green << GREEN_BALANCE)
                        | blue; // googled this
                image.setRGB(c, r, rgb);
            }
        }
        File output = new File(outputName);
        try {
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as: " + outputName);
        } catch (IOException e) {
            System.out.println("Failed Image Not Saved");
            e.printStackTrace();
        }
    }

    /**
     * Prints the RGB values of each pixel in a formatted way.
     *
     * @ensures output is a grid of pixels showing their RGB values
     */
    @Override
    public void printImage() {
        int[][][] pixels = this.getPixels();
        for (int r = 0; r < this.getHeight(); ++r) {
            for (int c = 0; c < this.getWidth(); ++c) {
                int red = pixels[r][c][0];
                int green = pixels[r][c][1];
                int blue = pixels[r][c][2]; // was incorrectly using red again
                System.out.printf("(%3d, %3d, %3d) ", red, green, blue);
            }
            System.out.println();
        }
    }

    /**
     * Returns a string representation of the image showing all pixels as (R, G,
     * B) values in a grid format.
     *
     * @return a string representation of the image
     * @ensures toString = formatted pixel values by row
     */
    @Override
    public String toString() { // human reable part - SImplfy
        String result = "";
        for (int r = 0; r < this.getHeight(); ++r) {
            for (int c = 0; c < this.getWidth(); ++r) {
                int red = this.getPixels()[r][c][0];
                int green = this.getPixels()[r][c][1];
                int blue = this.getPixels()[r][c][2];

                result += String.format("(%3d, %3d, %3d) ", red, green, blue);
            }
            result += "\n";
        }
        result += "\n";

        return result;
    }

    /**
     * Compares this image with another object to see if they are equal.
     *
     * @param o
     *            the object to compare
     * @return true if the other image has the same pixels and dimensions
     * @ensures equals = true iff all pixel values and dimensions match
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            return true;
        }

        if (!(o instanceof Image)) {
            return false;
        }

        Image other = (Image) o;

        if (this.getWidth() != other.getWidth()
                || this.getHeight() != other.getHeight()) {
            return false;

        }

        // go through pixels
        for (int i = 0; i < this.getTotalPixel(); ++i) {
            if (this.contains(i) && other.contains(i)) {
                for (int r = 0; r < this.getHeight(); ++r) {
                    for (int c = 0; c < this.getWidth(); ++r) {
                        int red = this.getPixels()[r][c][0];
                        int green = this.getPixels()[r][c][1];
                        int blue = this.getPixels()[r][c][2];

                        int red1 = other.getPixels()[r][c][0];
                        int green1 = other.getPixels()[r][c][1];
                        int blue1 = other.getPixels()[r][c][2];

                        if (red != red1 || green != green1 || blue != blue1) {
                            return false;
                        }
                    }
                }

            }
        }
        return true;
    }

    /**
     * Applies a basic blur effect to the image by averaging pixel values with
     * neighboring pixels.
     *
     * @updates this
     * @ensures each pixel is replaced with the average of itself and neighbors
     */
    @Override
    public void GaussianBlur() {
        //  Create Copy of the Image
        Image blurredImage = new Image1(this.getWidth(), this.getHeight(),
                new int[] { 0, 0, 0 });

        // blurredImage.copyFrom(this); // Black Bar Issue

        int[][][] px = this.getPixels();

        for (int r = 1; r < this.getHeight(); ++r) {
            for (int c = 1; c < this.getWidth(); ++c) {
                int[] avgColor = new int[3];

                // Average each channel with top-left neighbor
                avgColor[0] = (px[r][c][0] + px[r - 1][c - 1][0])
                        / GAUSSIAN_DIVISOR;
                avgColor[1] = (px[r][c][1] + px[r - 1][c - 1][1])
                        / GAUSSIAN_DIVISOR;
                avgColor[2] = (px[r][c][2] + px[r - 1][c - 1][2])
                        / GAUSSIAN_DIVISOR;

                int index = r * this.getWidth() + c;
                blurredImage.paint(index, avgColor);
            }
        }

        // Repalce Values
        this.copyFrom(blurredImage);
    }
}
