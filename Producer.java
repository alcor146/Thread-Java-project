
import java.awt.image.BufferedImage;

   public  class Producer extends Thread {
        private StoredImage storedImage;
        private BufferedImage inputImage;
        private int factor;
        private boolean zoomIn;
        
        public Producer ( StoredImage storedImage, BufferedImage inputImage, int factor, boolean zoomIn) {
            this.storedImage = storedImage;
            this.inputImage = inputImage;
            this.factor = factor;
            this.zoomIn = zoomIn;
        }

       
       public void run () {
            System.out.println("Running " +getName() );   
            int W = inputImage.getWidth(); 
            try{
                for (int i = 0; i < 3; i++) {
                storedImage.setData(inputImage, i*W/3, (i+1)*W/3, factor, zoomIn);  //proceseaza si salveaza o treime din imagine
              
                System.out.println("Starea:RUN  " + getName() + "  Producatorul a procesat :\t" + (i+1) + "/3 din imagine la pasul" + (i+1));

                sleep((int) (1000));}

                }catch (InterruptedException e) {}

                System.out.println("Going dead "  + getName());
            }   

            
        }



