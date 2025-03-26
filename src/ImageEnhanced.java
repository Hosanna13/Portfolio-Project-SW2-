public interface ImageEnhanced extends ImageKernel {
    /**
     * Clears the entire image by setting all pixels to black.
     *
     * @clears this
     * @ensures all pixels in the image are set to (0, 0, 0)
     */
    void clearImage();

    /**
     * Randomizes the entire image by assigning each pixel a random RGB color.
     *
     * @updates this
     * @ensures each pixel in the image is assigned a random RGB color (each
     *          channel between 0 and 255)
     */
    void randomizeImage(); // Also make sure to fix this typo in your actual code

    /**
     * Sets the entire image to a specific RGB color.
     *
     * @param color
     *            an array of 3 integers representing the RGB values
     * @requires color.length == 3
     * @updates this
     * @ensures all pixels in the image are set to the given color (each channel
     *          clipped to [0, 255])
     */
    void setColor(int[] color);

    /**
     * Saves the current image to a PNG file with the given filename.
     *
     * @param filename
     *            the name of the file to save the image to (".png" is appended
     *            if missing)
     * @requires filename is not null and not empty
     * @ensures the image is written to a file on disk in PNG format
     */
    void saveImage(String filename);

    /**
     * Prints the RGB values of each pixel in the image to standard output.
     *
     * @ensures a formatted text representation of the image is printed
     */
    void printImage();

}