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
    private static final int GUSSAINDIVI = 2;
    /**
     * Sets all pixels in the image to black (0, 0, 0).
     *
     * @updates this
     * @ensures every pixel in this is set to black
     */
    @Override
    public void clearImage() {
        for (int i = 0; i < this.gettotalPixel(); ++i) {
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
        for (int i = 0; i < this.gettotalPixel(); ++i) {
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
        Random rand = new Random();
        for (int i = 0; i < this.gettotalPixel(); ++i) {
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
        if (!filename.contains("png")) {
            filename += ".png";
        }
        BufferedImage image = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_RGB);
        for (int r = 0; r < this.height; ++r) {
            for (int c = 0; c < this.width; ++c) {
                int red = this.pixels[r][c][0];
                int green = this.pixels[r][c][1];
                int blue = this.pixels[r][c][2];
                int rgb = (red << RED_BALANCE) | (green << GREEN_BALANCE)
                        | blue; // googled this
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

    /**
     * Prints the RGB values of each pixel in a formatted way.
     *
     * @ensures output is a grid of pixels showing their RGB values
     */
    @Override
    public void printImage() {
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
        result += "/n";

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
                || this.getHeight() != other.getWidth()) {
            return false;

        }

        // go through pixels
        for (int i = 0; i < this.gettotalPixel(); ++i) {
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
                            break;
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
        int[] color = { MIN_COLOR_VALUE, MIN_COLOR_VALUE, MIN_COLOR_VALUE };
        Image blurredImage = new Image(getWidth(), getHeight(), color);
        blurredImage.copyFrom(this); // Black Bar Issue

        for (int r = 1; r < this.getHeight(); ++r) {
            for (int c = 1; c < this.getWidth(); ++r) {
                // Average Each Color Value
                int redSum = this.getPixels()[r][c][0]
                        + this.gettotalPixel()[r - 1][j - 1][0];
                int greenSum = this.getPixels()[r][c][1]
                        + this.gettotalPixel()[r - 1][j - 1][1];
                int blueSum = this.getPixels()[r][c][2]
                        + this.gettotalPixel()[r - 1][j - 1][2];

                int redAvg = redSum / GUSSAINDIVI;
                int greenAvg = greenSum / GUSSAINDIVI;
                int blueAvg = blueSum / GUSSAINDIVI;

                // Set Current Pixel to the Result of the Blur
                blurredImage[r][c][0] = redAvg;
                bluredImage[r][c][1] = greenAvg;
                blurredImage[r][c][2] = blueAvg;
            }
        }

        // Repalce Values
        for (int r = 0; r < this.getHeight(); ++r) {
            for (int c = 0; c < this.getWidth(); ++r) {
                // Average Each Color Value
                this.getPixels()[r][c][0] = blurredImage[r][c][0];
                this.getPixels()[r][c][1] = blurredImage[r][c][1];
                this.getPixels()[r][c][2] = blurredImage[r][c][2];
            }
        }
    }
}
