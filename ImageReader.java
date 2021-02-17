import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageReader extends Image {


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return img;
    }

    ImageReader(){ //constructor fara parametrii
        super();
    }

    ImageReader(int w, int h, BufferedImage image){ //constructor cu parametrii
        height = h;
        width = w;
        img = image;
    }

    ImageReader(int w, int h, int type){  //constructor fara bufferimage
        width = w;
        height = h;
        img = new BufferedImage(w, h, type);
    }

    String findFilePath() // metoda folosita pentru citirea de la tastatura a numelui fisierului
    {
        String filePath = null; // numele fisierului de intrare
        long t1 = System.currentTimeMillis();

        try {
            String location = "C:";
            InputStreamReader input = new InputStreamReader(System.in); //input de la tastatura
            BufferedReader reader = new BufferedReader(input);  //eficienta citire
            System.out.println("Introdu numele imaginii: (.bmp)");
            filePath = (reader.readLine());
           
            filePath = location + filePath;

        }

        catch (IOException e)
        {
            System.out.println("A dat gres");
        }

        long t2 = System.currentTimeMillis();

        long t3= t2-t1;
        System.out.println("Timp de executie pentru procesul de gasire a fisierului: "+t3+ " milisecunde");
        return filePath; // metoda va returna numele fisierului
    }

    void readFromFile (String s) //metoda folosita pentru citirea imaginii din fisier
    {
        long t1 = System.currentTimeMillis();
        try
        {
            File input = new File(s); //s este numele fisierului
            img = ImageIO.read(input);
            width = img.getWidth();
            height = img.getHeight();
        }

        catch (IOException e)
        {
            System.out.println("Fisierul nu exista ");
            System.exit(0);
        }

        long t2 = System.currentTimeMillis();
        long t3= t2-t1;
        System.out.println("Timp de executie pentru citirea din fisier: "+t3+ " milisecunde");

    }

}