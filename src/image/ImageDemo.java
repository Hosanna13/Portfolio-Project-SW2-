package image;

/**
 * Demonstrates the usage of the Image component. Showcases all major
 * operations: paint, setColor, clear, randomize, blur, print, and save.
 */
public class ImageDemo {
    public static void main(String[] args) {
        System.out.println("Creating a 3x3 image filled with (50, 100, 150):");
        Image img = new Image1(3, 3, new int[] { 50, 100, 150 });
        img.printImage();

        System.out.println("Painting pixel at index 4 with (255, 0, 0):");
        img.paint(4, new int[] { 255, 0, 0 }); // middle pixel
        img.printImage();

        System.out.println("Setting entire image to (0, 255, 0):");
        img.setColor(new int[] { 0, 255, 0 });
        img.printImage();

        System.out.println("Randomizing the image:");
        img.randomizeImage();
        img.printImage();

        System.out.println("Applying Gaussian blur:");
        img.GaussianBlur();
        img.printImage();

        System.out.println("Clearing the image:");
        img.clearImage();
        img.printImage();

        System.out.println("Saving final image to 'demo-output.png'");
        img.saveImage("demo-output");
    }
}