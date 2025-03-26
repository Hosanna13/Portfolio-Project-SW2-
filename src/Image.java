import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Image {
    // Image Dimensions
    private int width;
    private int height;

    // pixels[row][col][channel]: channel 0 = R, 1 = G, 2 = B. <- within image
    private int[][][] pixels;
    private int totalPixel;

    // Constructor: not default
    /*
     * This constructor creates an image of SIZE of width x height, where each
     * PIXEL starts with the same RGB color. NOTE: each pixel stores 3 values
     * (R,G,B)!! so each pixel for each index will have its own channel, like
     * [i][j] <- index, and then [color is channel] Image(width, height, int[]
     * initialColor) <- representation explaining initialColor, if its like blue
     * its represented as int[] Blue = [0, 0, 255]; this will make everything a
     * soild color blue/
     */

    public Image(int width, int height, int[] initialColor) {
        // Vaild Diemnsions Check
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Invaild Image Dimensions, dimensions must be postive ");
        }

        // Vaild Color check length == 3
        if (initialColor == null || initialColor.length != 3) {
            throw new IllegalArgumentException(
                    "Intial color must be an array of 3 RBG values.");
        }

        // Check if RBG values are within the vaild range (0 - 255)
        /*
         * for (int i = 0; i < 3; ++i) { initialColor[i] =
         * this.clip(initialColor[i]); }
         */
        // fix time Complexity O(n) -> O(1)
        initialColor[0] = this.clip(initialColor[0]);
        initialColor[1] = this.clip(initialColor[1]);
        initialColor[2] = this.clip(initialColor[2]);

        // stInance varaibles
        this.width = width;
        this.height = height;

        // create 3D pixel array (height x width x RGB channels)
        this.pixels = new int[height][width][3];

        // Create every pixel in teh image to the given color
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                this.pixels[row][col][0] = initialColor[0]; // Red Channel
                this.pixels[row][col][1] = initialColor[1]; // Green Channel
                this.pixels[row][col][2] = initialColor[2]; // Blue Channel
            }
        }
    }

    //Helper Method's
    //Called Clip
    /*
     * @param: val
     *
     * @return The value between 0 and 255.
     */
    private int clip(int val) {
        return Math.max(0, Math.min(255, val));
    }

    private int getRow(int indx) {
        return indx / this.width;
    }

    private int getCol(int indx) {
        return indx % this.width;
    }

    // totalPixel: returns # of pixels
    public void totalPixel() {
        this.totalPixel = this.width * this.height;
    }

    /*
     * KERNAL METHODS
     */

    // contains: makes sure pixel exist
    /*
     * @requires: index is 0 or postive number
     *
     * @param: index
     *
     * @ensures: if index of the pixel is there
     *
     */
    public boolean contains(int index) {
        int row = this.getRow(index);
        int col = this.getCol(index);
        // if  ( row < height && row >= 0 ) && ( col < width && col >= 0 )
        return (row >= 0 && row < this.height)
                && (col >= 0 && col < this.width);
    }

    /*
     * paint: paints on pixel
     */
    public void paint(int index, int[] color) {
        if (this.contains(index)) {
            int row = this.getRow(index);
            int col = this.getCol(index);
            this.pixels[row][col][0] = this.clip(color[0]);
            this.pixels[row][col][1] = this.clip(color[1]);
            this.pixels[row][col][2] = this.clip(color[2]);
        } else {
            try {
                throw new IllegalAccessException("Invaild Index"); // auto complete did the whole try catch, intially i had new
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * erase: erases pixel
     */
    public void erase(int index) {
        if (this.contains(index)) {
            int row = this.getRow(index);
            int col = this.getCol(index);
            this.pixels[row][col][0] = 0;
            this.pixels[row][col][1] = 0;
            this.pixels[row][col][2] = 0;
        }
    }

    /*
     * Save Image:saves image to file
     */

    /*
     * Print Image: prints image in readable format (testing / buidling)
     * discarded after save Image is completed
     */
    public void printImage() {
        for (int r = 0; r < this.height; ++r) {
            for (int c = 0; c < this.width; ++c) {
                int red = this.pixels[r][c][0];
                int green = this.pixels[r][c][1];
                int blue = this.pixels[r][c][2];
                // Prints Each Pixel
                System.out.printf("(%3d, %3d, %3d) ", red, green, blue);
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Save Image
     */
    public void saveImge(String filename) {
        // check if filename have .png
        if (!filename.contains("png")) {
            filename = filename + ".png";
        }

        // Buffered Image stores image in ram
        BufferedImage image = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_RGB);
        // last part determines the like the colorspace of the file
        for (int r = 0; r < this.height; ++r) {
            for (int c = 0; c < this.width; ++c) {
                int red = this.pixels[r][c][0];
                int green = this.pixels[r][c][1];
                int blue = this.pixels[r][c][2];
                int rgb = (red << 16) | (green << 8) | blue; // googled this
                image.setRGB(r, c, rgb);
            }
        }

        // Store that into file
        File output = new File(filename);
        // ImageIO.write(image, "png", output);
        try {
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as: " + filename);
        } catch (IOException e) { // this is needed for some reason to make ImageIO.write work ( i got like an error)
            System.out.println("Failed Image Not Saved");
            e.printStackTrace(); // paint had the same thing so :0
        }

    }

    /*
     * SECONDARY METHODS: randmizeImage, grayscale, guasuainBlur (hardest),
     * clearImage
     */
    /*
     * clearImage: Erases the entire image
     */
    public void clearImage() {
        // i: is the linear index of pixel (hopefully)
        this.totalPixel();
        for (int i = 0; i < this.totalPixel; ++i) {
            this.erase(i);
        }
    }
    /*
     * Randomize image:
     */

    public void randmizeImage() {
        Random rand = new Random();
        this.totalPixel();
        for (int i = 0; i < this.totalPixel; ++i) {
            int[] color = { rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256) };
            this.paint(i, color);
        }
    }
    /*
     * SetColor: sets entire image to given color
     *
     * @requires: size of color to be 3
     */

    public void setColor(int[] color) {
        Random rand = new Random();
        for (int i = 0; i < this.totalPixel; ++i) {
            this.paint(i, color);
        }
    }

    public static void main(String[] args) { // main to test
        // Test 0:  Constuctor & Printing Image + SaveImage
        int[] color = { 255, 255, 255 };
        Image img = new Image(1080, 1080, color);
        System.out.println("Test Constructor + Print Image");
        //img.printImage();
        img.saveImge(" Test 0:  Constuctor & Printing Image + SaveImage");

        // Test 1: Paint Index 0, blue?
        int[] blue = { 0, 0, 255 };
        System.out.println("Index 0: Blue");
        img.paint(0, blue);

        // check - contains check
        System.out.println(img.contains(0));
        // img.printImage();
        img.saveImge("Test 1: Paint Index 0, blue?");

        // Test 1a: Painting Out of Index (should be an error)
        img.paint(232, color); // index - exceeds # of pixels it should not work correcty

        // Test 2: Checking Clear Image
        System.out.println("Test 2: Checking Clear Image ");
        img.clearImage();
        //   img.printImage();
        img.saveImge("Test 2: Checking Clear Image ");

        // Test 3: Secondary Method RandomIze Image
        System.out.println("Test 3: Secondary Method RandomIze Image");
        img.randmizeImage();
        // img.printImage();
        img.saveImge("Test 3: Secondary Method RandomIze Image.png");

        // Test 4: Save Image
        System.out.println("Test 4: Save Image");
        img.saveImge("Test4.png");
        // Save the original image

        // img.saveImage("original.png");

        // Convert the image to grayscale and save it
        //img.tint();
        // img.saveImage("grayscale.png");

        // Apply Gaussian blur and save it
        //img.applyGaussianBlur();
        //img.saveImage("blurred.png");
    }

}
