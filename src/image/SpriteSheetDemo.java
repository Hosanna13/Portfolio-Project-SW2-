package image;

public class SpriteSheetDemo {
    public static void main(String[] args) {

        // Create a new image with the specified dimensions
        Image1 img = new Image1(16, 16, new int[] { 255, 255, 255 });
        img.paint(55, new int[] { 167, 166, 167 });
        img.paint(56, new int[] { 250, 250, 250 });
        img.paint(70, new int[] { 91, 91, 91 });
        img.paint(71, new int[] { 255, 85, 124 });
        img.paint(72, new int[] { 142, 49, 62 });
        img.paint(73, new int[] { 217, 217, 217 });
        img.paint(86, new int[] { 55, 8, 17 });
        img.paint(87, new int[] { 190, 29, 58 });
        img.paint(88, new int[] { 255, 64, 95 });
        img.paint(89, new int[] { 205, 186, 188 });
        img.paint(101, new int[] { 2, 2, 2 });
        img.paint(102, new int[] { 237, 7, 50 });
        img.paint(103, new int[] { 246, 12, 52 });
        img.paint(104, new int[] { 150, 13, 43 });
        img.paint(105, new int[] { 255, 75, 106 });
        img.paint(106, new int[] { 252, 251, 252 });
        img.paint(116, new int[] { 243, 243, 243 });
        img.paint(117, new int[] { 236, 98, 122 });
        img.paint(118, new int[] { 246, 12, 52 });
        img.paint(119, new int[] { 89, 0, 0 });
        img.paint(120, new int[] { 246, 12, 52 });
        img.paint(121, new int[] { 246, 12, 52 });
        img.paint(122, new int[] { 72, 0, 3 });
        img.paint(123, new int[] { 254, 254, 254 });
        img.paint(132, new int[] { 44, 44, 44 });
        img.paint(133, new int[] { 233, 17, 58 });
        img.paint(134, new int[] { 110, 0, 7 });
        img.paint(135, new int[] { 246, 12, 52 });
        img.paint(136, new int[] { 233, 9, 55 });
        img.paint(137, new int[] { 181, 31, 59 });
        img.paint(138, new int[] { 255, 65, 98 });
        img.paint(139, new int[] { 177, 177, 177 });
        img.paint(147, new int[] { 2, 2, 2 });
        img.paint(148, new int[] { 31, 170, 86 });
        img.paint(149, new int[] { 238, 255, 244 });
        img.paint(150, new int[] { 175, 11, 25 });
        img.paint(151, new int[] { 249, 11, 55 });
        img.paint(152, new int[] { 247, 12, 55 });
        img.paint(153, new int[] { 173, 15, 30 });
        img.paint(154, new int[] { 239, 255, 243 });
        img.paint(155, new int[] { 31, 170, 86 });
        img.paint(156, new int[] { 249, 249, 249 });
        img.paint(163, new int[] { 13, 13, 13 });
        img.paint(164, new int[] { 43, 111, 72 });
        img.paint(165, new int[] { 35, 170, 93 });
        img.paint(166, new int[] { 31, 170, 86 });
        img.paint(167, new int[] { 219, 255, 235 });
        img.paint(168, new int[] { 221, 255, 234 });
        img.paint(169, new int[] { 31, 170, 86 });
        img.paint(170, new int[] { 35, 171, 91 });
        img.paint(171, new int[] { 41, 107, 69 });
        img.paint(172, new int[] { 245, 245, 245 });
        img.paint(181, new int[] { 0, 0, 0 });
        img.paint(182, new int[] { 27, 133, 72 });
        img.paint(183, new int[] { 27, 133, 72 });
        img.paint(184, new int[] { 27, 133, 72 });
        img.paint(185, new int[] { 32, 132, 73 });
        img.paint(186, new int[] { 0, 0, 0 });

        // Save the generated sprite sheet
        img.saveImage("sprite_sheet_demo2");

        // Apply Gussaion Blur
        img.GaussianBlur();
        img.saveImage("sprite_sheet_demo_blurred");
    }
}