import java.awt.image.BufferedImage;

public class StoredImage extends ImageReader {
    private boolean available = false ;

    public StoredImage(int w, int h, int type) {
        super(w, h, type);
    }

    public StoredImage() {
        super();
    }

   

    public synchronized void setData(BufferedImage pixels, int i, int j, int factor, boolean zoomIn){
        while ( available ) {
            try {
                wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
         available = true;

         if(zoomIn == true) {   //daca facem zoom in

            i = i * factor; //dimensiunile noii matrici sunt de factor ori mai mari
            j = j * factor;

            for(int y = 0; y < height; y++)     //pentru fiecare pixel din noua imagine
                for(int x = i; x < j; x++)
                    img.setRGB(x, y, pixels.getRGB(x / factor , y / factor));  //ii dam valoarea RGB a pixeleleui corespunzator algoritmului si documentatiei
         }

         else { //daca facem zoom out
            int originalH = height * factor;    //imaginea este de factor ori mai mica

            for(int y = 0; y < originalH; y++)  //pentru fiecare pixel din imaginea initiala
                for(int x = i; x < j; x++)
                    img.setRGB(x / factor, y / factor, pixels.getRGB(x, y));    //luam ovaloare corespunzotoare lui si pe restul le ignoram
         }
     
            notifyAll();
         
        }

        public synchronized BufferedImage getData(BufferedImage pixels, int i, int j) {
            while ( !available ) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            available = false;
           

            for(int y = 0; y < height; y++)
                for(int x = i; x < j; x++)
                    pixels.setRGB(x, y, img.getRGB(x, y));  //copiez imaginea procesata si o stochez in Consumer
                                                            //e mai mult ca sa arat funtionalitatea
            notifyAll();
            return pixels;
        }
        
    }
