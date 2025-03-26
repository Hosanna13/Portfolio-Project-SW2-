public interface Image1 extends ImageKernel {
    void clearIamge();

    void randmizeImage();

    void setColor(int[] color);

    void saveImage(String filename);
}