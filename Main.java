
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner; 

import javax.imageio.ImageIO;
import java.io.FileNotFoundException;


public class Main {
    public static void main(String[] args) throws IOException {


        StoredImage img = new StoredImage();

        Scanner scan= new Scanner(System.in);
		
		
		//Verificam daca se introduc numele fisierelor din linie de comanda
		//sau trebuie sa le citim de la tastatura

        String inputImage, zoomIn, zoomOut;


		if(args.length == 0) {
			System.out.println("Introduceti numele fisierului de intrare: ");
            inputImage = (scan.nextLine());

            while (!inputImage.endsWith(".bmp"))
            {
                System.out.println("extensie gresita"); // daca nu are extensia bmp, se asteapta un fisier care are
                inputImage = (scan.nextLine());
            }
		}else {
			inputImage = args[0];
		}
		if(args.length <= 1) {
			System.out.println("Introduceti numele fisierului de zoom in (incluzand extensia .bmp): ");
			zoomIn = scan.nextLine();

            while (!zoomIn.endsWith(".bmp"))
            {
                System.out.println("extensie gresita"); // daca nu are extensia bmp, se asteapta un fisier care are
                zoomIn = (scan.nextLine());
            }
		}else {
			zoomIn = args[1];
		}
        if(args.length <= 2) {
			System.out.println("Introduceti numele fisierului de zoom out (incluzand extensia .bmp): ");
			zoomOut = scan.nextLine();

            while (!zoomOut.endsWith(".bmp"))
            {
                System.out.println("extensie gresita"); // daca nu are extensia bmp, se asteapta un fisier care are
                zoomOut = (scan.nextLine());
            }
		}else {
			zoomOut = args[2];
		}

        scan.close();

        img.readFromFile(inputImage);

        File zoomedInFile = new File(zoomIn);
        File zoomedOutFile = new File(zoomOut);


        int factor = 3; // vreau ca imaginea sa fie de factor ori mai mare sau mai mica

        StoredImage zoomInToStoredImage = new StoredImage(img.getWidth() * factor, img.getHeight() * factor, img.getImage().getType()); //creez o instanta in care voi salva mai tarziu imaginea procesata
       
        Producer p1 = new Producer(zoomInToStoredImage, img.getImage(), factor, true); //true pentru zoom in
        Consumer c1 = new Consumer(zoomInToStoredImage);

        long startEnlargeTime = System.currentTimeMillis();

        p1.start(); //start proccesare pe primele 2 threaduri pentru zoom in
        c1.start(); //aici imi salvez imaginea rezultat
        
        try {
                c1.join();  //astept sa se termine procesarea primei imagini
            } catch (Exception e) {
                e.printStackTrace();
            }

        long estimatedTime2 = System.currentTimeMillis() - startEnlargeTime;
        System.out.println("Timpul de procesare pentru marirea imaginii este  " + estimatedTime2 + " milisecunde\n");


        long startScriereIn = System.currentTimeMillis();

        ImageIO.write(c1.getResult(), "bmp", zoomedInFile);             // PS: a se schimba path-ul catre folderul unde vrea imaginea procesata

        long scriereIn = System.currentTimeMillis() - startScriereIn;

        System.out.println("Timpul de scriere pentru marirea imaginii este  " + scriereIn + " milisecunde\n");


        StoredImage zoomOutToStoredImage = new StoredImage(img.getWidth() / factor, img.getHeight() / factor, img.getImage().getType());
        
        Producer p2 = new Producer(zoomOutToStoredImage,  img.getImage(), factor, false);   //false pentru zoom out
        Consumer c2 = new Consumer(zoomOutToStoredImage);

        long startMinimizeTime = System.currentTimeMillis();
        
        p2.start();
        c2.start();

        try {
            c2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //acelasi lucru pentru zoom out, dar astept sa se termine procesarea pentru zoom in
        //timpul de procesare include timpul de sleep al threadurilor 

        long estimatedTime3 = System.currentTimeMillis() - startMinimizeTime;
        System.out.println("Timpul de procesare pentru micsorarea imaginii este  " + estimatedTime3 + " milisecunde\n");


        long startScriereOut = System.currentTimeMillis();
        
        ImageIO.write(c2.getResult(), "bmp", zoomedOutFile);

        long scriereOut = System.currentTimeMillis() - startScriereOut;
        System.out.println("Timpul de scriere pentru micsorarea imaginii este  " + scriereOut + " milisecunde\n");

        saveTime(estimatedTime2, estimatedTime3, "text1.txt", "text2.txt", "text3.txt");
       
    }


    public static void saveTime(long timp1, long timp2, String... args) throws FileNotFoundException {
        String path = "C:\\copie_proiect\\";  //se va scrie path ul catre folderul unde vreau sa salvez fisierele cu timpii de procesare

        for (String files : args) {
            if (files.length() > 0) {
                try (PrintWriter out = new PrintWriter(path + files)) {
                    out.println(timp1 + " milisecunde");
                    out.println(timp2 + " milisecunde");
                }

            } else {
                try (PrintWriter out = new PrintWriter(path + "timp.txt")) {
                    out.println(timp1 + " milisecunde");
                    out.println(timp2 + " milisecunde");
                }
            }
        }
    }
}