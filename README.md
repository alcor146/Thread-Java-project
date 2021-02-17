# Thread-Java-project
A small image processing project using the pixel replication method to resize an image

ImageInterface – makes sure i always override the getHeight() and getWidth methods
Image implements ImageInterface – an abstract class where I keep the class variables (maybe I should have made them private)

ImageReader extends Image – I keep the mothod of reading the data from the BMP format image

StoredImage extends Image reader – I use it to store the image that will be processed

setData – I store the image after aplying the algorithm or enlarging/shrinking the image by replicating/omiting pixels 
getData – I get the result after the algorithm

Consumer and Producer – the threads where I process the image part by part

Main
saveTime – I save the read, process, write times for the image in n files

