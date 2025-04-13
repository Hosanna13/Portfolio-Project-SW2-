/*
 * This test the kernal methods...
 */
package image;

public class Image1Test {
    // TODO - contructor
    @Test
    public void testContructorEmpty() {
        int[] black = { 0, 0, 0 };
        Image img = new Image1(1, 1, black);
        assertEquals(1, img.gettotalPixel());
    }

}
