import java.util.Random;

public abstract class ImageSecondary implements ImageKernel {
    // Implement these using only kernel methods:

    // TODO - clearImage()
    @Override
    public void clearImage() {
        for (int i = 0; i < this.gettotalPixel(); ++i) {
            if (this.contains(i)) {
                this.erase(i);
            }
        }
    }

    // TODO  - randomizeImage()
    @Override
    public void randomizeImage() {
        Random rand = new Random();
        for (int i = 0; i < this.gettotalPixel(); ++i) {
            int[] color = { this.clip(rand.nextInt(256)),
                    this.clip(rand.nextInt(256)),
                    this.clip(rand.nextInt(256)) };
            this.paint(i, color);
        }
    }

    @Override
    public void setColor(int[] color) {
        Random rand = new Random();
        for (int i = 0; i < this.gettotalPixel(); ++i) {
            if (this.contains(i)) {
                this.paint(i, color);
            }
        }
    }

    // TODO - getPixel (maybe)

    // TODO - saveImage()
    @Override
    public void saveImage(String filename) {
        if (!filename.contains("png")) {
            filename += ".png";
        }

    }

    // TODO - printImage()
    @Override
    public void printImage() {

    }

    // Also implement:
    // - toString()
    @Override
    public String toString() {
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

    // TODO - equals(Object obj)
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o) {
            result = true;
        }

        Image other = (Image) o;

        if (this.getWidth() == other.getWidth()
                && this.getHeight() == other.getWidth()) {
            // go through pixels
            for (int i = 0; i < this.gettotalPixel(); ++i) {
                if (this.contains(i) && other.contains(i)) {
                    // 2 for loops row & col use that getpixel()[r][c][0] == other.getpixel()[r][c][0] // if that is true count
                    // if count == # of pixels result = true.
                }
            }

        }

    }
}
