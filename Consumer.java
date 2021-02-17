
import java.awt.image.BufferedImage;

   public  class Consumer extends Thread {
        private StoredImage storedImage;
        private BufferedImage outputImage;

        public Consumer ( StoredImage storedImage) {
            this.storedImage = storedImage;
            outputImage = new BufferedImage(storedImage.getWidth(), storedImage.getHeight(), storedImage.getImage().getType());
        }

      

       public void run () {
            System.out.println("Running " + getName());

            int W = storedImage.getWidth(); 

           try{ 
            for (int i = 0; i < 3; i++) {


                outputImage = storedImage.getData(outputImage, i*W/3, (i+1)*W/3);   //copiaza o treime din imagine

                System.out.println("Starea:RUN  " + getName() + "  Consumatorul a primit :\t" + (i+1) + "/3 din imagine la pasul" + (i+1));

               sleep((int)(1000));
           }

            } catch (InterruptedException e) { }

            System.out.println("Going dead " + getName());
        }

        public BufferedImage getResult() {
            return outputImage;
        }

       
   }


