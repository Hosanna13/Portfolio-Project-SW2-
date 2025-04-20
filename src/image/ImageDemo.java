package image;

/**
 * Demonstrates the usage of the Image component. Showcases all major
 * operations: paint, setColor, clear, randomize, blur, print, and save.
 */
public class ImageDemo {
    public static void main(String[] args) {
        System.out.println(
                "Creating a 1080x1080 image filled with (50, 100, 150):");
        Image1 img = new Image1(1080, 1080, new int[] { 50, 100, 150 });
        // img.printImage();
        img.saveImage("demo-constructor");

        System.out.println("Painting pixel at index 4 with (255, 0, 0):");
        img.paint(4, new int[] { 255, 0, 0 }); // middle pixel
        // img.printImage();
        img.saveImage("demo-paintIndex");

        System.out.println("Setting entire image to (0, 255, 0):");
        img.setColor(new int[] { 0, 255, 0 });
        // img.printImage();
        img.saveImage("demo-setColor");

        System.out.println("Randomizing the image:");
        img.randomizeImage();
        //img.printImage();
        img.saveImage("demo-randomize");

        System.out.println("Applying Gaussian blur:");
        img.GaussianBlur();
        //img.printImage();
        img.saveImage("demo-guassian");

        System.out.println("Clearing the image:");
        img.clearImage();
        //img.printImage();
        img.saveImage("demo-clear");

        System.out.println("Saving final image to 'demo-output.png'");
        img.saveImage("demo-output");
    }
}