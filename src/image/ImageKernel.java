package image;

import components.standard.Standard;

/**
 * Image kernel component that defines all primary operations for an image.
 * (Note: by package-wide convention, all references are non-null.)
 *
 * @mathsubtypes <pre>
 * IMAGE is a finite 2D grid of pixels, where each pixel is an RGB color:
 *     each pixel = (r, g, b), with 0 <= r, g, b <= 255
 *  exemplar img
 *  constraint
 *     For all row, col within image bounds:
 *       each img.pixels[row][col] = [r, g, b] and values are clipped to [0, 255]
 * </pre>
 *
 * @mathmodel type ImageKernel is modeled by a 3D array [height][width][3] of
 *            RGB values
 *
 * @initially <pre>
 * (width, height, initialColor):
 *     ensures
 *         image is filled with initialColor
 *         and dimensions are width x height
 * </pre>
 *
 * @iterator <pre>
 * entries(~this.seen * ~this.unseen) = this
 * and |~this.seen * ~this.unseen| = |this|
 * </pre>
 */
public interface ImageKernel extends Standard<Image> {
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
      * @return the product of width and height
      * @ensures returns width * height
      */
     int getTotalPixel();

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
      *             the color channel value to clip
      * @return val if it's between 0 and 255, 0 if less than 0, or 255 if
      *         greater than 255
      * @ensures 0 <= result <= 255
      */
     int clip(int val);

     /**
      * Determines whether the given index corresponds to a valid pixel.
      *
      * @param index
      *             the linear index of the pixel
      * @return true if the index is within bounds; false otherwise
      * @requires index >= 0
      * @ensures returns true iff the index maps to a valid pixel in the image
      */
     boolean contains(int index);

     /**
      * Paints the pixel at the given index with the specified RGB color.
      *
      * @param color
      *             an array of 3 integers representing the RGB values
      * @requires color.length == 3
      * @updates this
      * @ensures all pixels in the image are set to the given color (each
      *          channel clipped to [0, 255])
      */
     void setColor(int[] color);

     /**
      * Saves the current image to a PNG file with the given filename.
      *
      * @param filename
      *             the name of the file to save the image to (".png" is
      *             appended if missing)
      * @requires filename is not null and not empty
      * @ensures the image is written to a file on disk in PNG format
      */
     void saveImage(String filename);

     /**
      * Replaces the current value of this Image with the value of the given
      * Image {@code o}.
      *
      * @param o
      *             the Image to copy from
      * @requires o is not null, o is not the same object as this, and o has the
      *           same dimensions (width and height) as this
      * @ensures this = o and o = #o
      */
     void copyFrom(Image o);

}
