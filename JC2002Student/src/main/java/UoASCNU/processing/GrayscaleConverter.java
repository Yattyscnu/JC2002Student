package UoASCNU.processing;

import UoASCNU.images.ImageFile;
import UoASCNU.images.Pixel;

import java.io.File;

/**
 * Convert a number of images to their grayscale version.
 *
 * @author Marco A. Palomino
 */

public class GrayscaleConverter {

    /**
     * Creates a new image that is a grayscale version of the original image.
     *
     * @param originalImage The image we want to convert to grayscale.
     * @return An image converted to grayscale.
     */
    public static ImageFile makeGray(ImageFile originalImage) {
        // I made a blank image of the same size as the original
        ImageFile resultingImage = new ImageFile(
                originalImage.getWidth(), originalImage.getHeight());
            for (Pixel pixel: originalImage.pixels()){
                int x=pixel.getX();
                int y=pixel.getY();
                int red=pixel.getRed();
                int green=pixel.getGreen();
                int blue=pixel.getBlue();
                int average=(red+green+blue)/3;
                Pixel newPixel=resultingImage.getPixel(x,y);
                newPixel.setRed(average);
                newPixel.setGreen(average);
                newPixel.setBlue(average);
            }
        return resultingImage;
    }
        // At the end, this method must return resultingImage, 
        // rather than null! Remember to fix this to 
        // return resultingImage;
        // End of makeGray(originalImage)


    /**
     * Allows the user to choose a collection of images to convert them to
     * grayscale. Then, it saves the new images but with new names.
     */
    public static void convertAndSave(File[] imageFiles) {
        for(File imageFile:imageFiles){
            ImageFile originalImage=new ImageFile(imageFile);
            ImageFile grayscaleImage=makeGray(originalImage);
            String originalFileName=imageFile.getName();
            String newFileName="gray-"+originalFileName;
            grayscaleImage.setFileName(newFileName);
            grayscaleImage.saveAs();
        }
    } // End of selectAndConvert()

} // End of class GrayscaleConverter
