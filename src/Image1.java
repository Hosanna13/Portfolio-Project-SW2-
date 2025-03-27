import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Image1 implements ImageKernel {
    private int width;
    private int height;
    private int[][][] pixels;
    private int totalPixel;

    public Image1(int width, int height, int[] intialColor) {
        if (width <= 0 || height <= 0) { // vaild dimentions check
            throw new IllegalArgumentException(
                    "Invaild Image Dimensions, diemnsions must be postive.");
        }

        if (intialColor == null || intialColor.length != 3) {
            throw new IllegalArgumentException(
                    "Intial color must be an array of 3 RGB values.");
        }

        intialColor[0] = this.clip(intialColor[0]);
        intialColor[1] = this.clip(intialColor[1]);
        intialColor[2] = this.clip(intialColor[2]);

        this.width = width;
        this.height = height;

        this.pixels = new int[height][width][3];

        for (int r = 0; r < height; ++r) {
            for (int c = 0; c < width; c++) {
                this.pixels[r][c][0] = intialColor[0]; // R
                this.pixels[r][c][1] = intialColor[1]; // G
                this.pixels[r][c][2] = intialColor[2]; //  B
            }
        }
    }

    @Override
    public int clip(int val) {
        return Math.max(0, Math.min(255, val));
    }

    @Override
    public int getRow(int indx) {
        return indx / this.width;
    }

    @Override
    public int getCol(int indx) {
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
                int rgb = (red << 16) | (green << 8) | blue; // googled this
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
            int[] color = { rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256) };
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