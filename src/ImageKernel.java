public interface ImageKernel {
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
