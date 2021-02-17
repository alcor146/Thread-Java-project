import java.awt.image.BufferedImage;

public abstract class Image implements ImageInterface {

    protected int width;
    protected int height;
    protected BufferedImage img;


    public abstract int getHeight();
    public abstract int getWidth();
    public abstract BufferedImage getImage();

    Image() {
        width=0;
        height=0;
    };
    

}