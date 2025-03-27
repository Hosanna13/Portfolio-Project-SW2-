public interface Image {
    /**
     * Returns the row number of the pixel corresponding to the given index.
     *
     * @param index
     *            the linear index of the pixel
     * @return the row number where the pixel is located
     * @requires index >= 0
     * @ensures returns index / width
     */
    int getRow(int index);

    /**
     * Returns the column number of the pixel corresponding to the given index.
     *
     * @param index
     *            the linear index of the pixel
     * @return the column number where the pixel is located
     * @requires index >= 0
     * @ensures returns index % width
     */
    int getCol(int index);

    /**
     * Returns the total number of pixels in the image.
     *
     * @return the product of width and height
     * @ensures returns width * height
     */
    int gettotalPixel();

    /**
     * Returns the width of the image in pixels.
     *
     * @return the width
     * @ensures result > 0
     */
    int getWidth();

    /**
     * Returns the height of the image in pixels.
     *
     * @return the height
     * @ensures result > 0
     */
    int getHeight();

    /**
     * Returns the pixels in image.
     *
     * @return pixels
     */
    int[][][] getPixels();

    /**
     * Clips the given value to ensure it lies within the RGB range [0, 255].
     *
     * @param val
     *            the color channel value to clip
     * @return val if it's between 0 and 255, 0 if less than 0, or 255 if
     *         greater than 255
     * @ensures 0 <= result <= 255
     */
    int clip(int val);

    /**
     * Determines whether the given index corresponds to a valid pixel.
     *
     * @param index
     *            the linear index of the pixel
     * @return true if the index is within bounds; false otherwise
     * @requires index >= 0
     * @ensures returns true iff the index maps to a valid pixel in the image
     */
    boolean contains(int index);

    /**
     * Paints the pixel at the given index with the specified RGB color.
     *
     * @param index
     *            the linear index of the pixel
     * @param color
     *            an array of 3 integers representing the RGB values
     * @requires index >= 0 and color.length == 3
     * @ensures the pixel at the given index is updated to the given color
     *          (clipped between 0 and 255)
     */
    void paint(int index, int[] color);

    /**
     * Erases the pixel at the given index (sets it to black).
     *
     * @param index
     *            the linear index of the pixel
     * @requires index >= 0
     * @ensures the pixel at the given index is set to (0, 0, 0)
     */
    void erase(int index);

}
