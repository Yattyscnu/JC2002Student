package UoASCNU.images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.util.Arrays;


/**
 * The <code>ImageFile</code> class represents an image as a grid of
 * <code>Pixel</code> objects which allows access to all of them, using
 * the method <code>pixels</code>. These pixels can then be iterated over
 * using a <code>for</code> loop.
 *
 * @author Marco Palomino
 *
 */

 /**
 * The ImageFile class represents an image file with its pixel data, file details, 
 * and dimensions. It uses a BufferedImage to manage image data.
 */
public class ImageFile {
    private Pixel[] myPixels;
    private BufferedImage myImage;

    // The file name info or empty if no file yet
    private String myFileName;
    private String myPath;

    private int width;
    private int height;

    /**
     * Constructor: Creates an <code>ImageFile</code> object from a file
     * given as a parameter.
     *
     * @param file A file which we will use to create a new image.
     */
    public ImageFile(File file) {
        init(file);
    } // End of Constructor

    public ImageFile(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new RuntimeException(
                    "ImageFile: Width and height values must be positive ["
                            + width + "x" + height + "]");
        } else {
            this.width = width;
            this.height = height;
            init("", getBlankImage(width, height));
        }
    } // End of Constructor


    /**
     * Creates an image of width w and height h with black pixels.
     *
     * @param width  The width of the image
     * @param height The height of the image
     * @return An image of width w and height h with black pixels.
     */
    private BufferedImage getBlankImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    } // End of getBlankImage(width, height)


    /**
     * Returns the file name associated with this image file.
     *
     * @return The name of the file used to create this image or an empty
     * string if it was created as a sized image.
     */
    public String getFileName() {
        return myFileName;
    } // End of getFileName()


    /**
     * Returns the height of the image in pixels.
     *
     * @return The image's height in pixels.
     */
    public int getHeight() {
        return myImage.getHeight();
    } // End of getHeight()

    /**
     * Reads an image from a file and updates the pixels array.
     *
     * @param fileName The name of the file containing the image that will
     *                 be read to update the pixels array.
     * @return A BufferedImage
     * @throws RuntimeException If anything goes wrong, an exception will
     *                          be thrown.
     */
    private BufferedImage getImageFromFile(String fileName)
            throws RuntimeException {
        try {
            File file = new File(fileName);
            BufferedImage image = ImageIO.read(file);
            while (image.getWidth(null) < 0) {
                // wait for size to be known
            }
            return image;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } // End of getImageFromFile(fileName)

    /**
     * Returns the pixel at the (x, y) coordinates passed as a parameter.
     *
     * @param x The column position of the pixel
     * @param y The row position of the pixel
     * @return The Pixel at the given (x, y) coordinates
     */
    public Pixel getPixel(int x, int y) {
        // System.out.printf("get %d %d\n",x,y);
        return myPixels[y * myImage.getWidth() + x];
    } // End of getPixel(x, y)

    /**
     * Returns the width of the image in pixels.
     *
     * @return The image's width in pixels
     */
    public int getWidth() {
        return myImage.getWidth();
    } // End of getWidth()

    /**
     * Maps the image into the array of pixels
     *
     * @param image The image that will be mapped.
     * @return A Pixel array
     */
    private Pixel[] imageToPixels(Image image) {
        int w = myImage.getWidth();
        int h = myImage.getHeight();
        int[] pixels = new int[w * h];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            System.err.println("Interrupted waiting for pixels!");
            return null;
        }
        if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
            System.err.println("Image fetch wrong or aborted.");
            return null;
        }
        // System.out.printf("creating pixels %d\n", pixels.length);
        return integersToPixels(pixels, w, h);
    }

    /**
     * Creates an image from the given file
     *
     * @param f The file containing the image
     */
    private void init(File f) {
        try {
            String path = f.getCanonicalPath();
            init(path, getImageFromFile(path));
        } catch (Exception e) {
            throw new RuntimeException(
                    "ImageFile: Unable to find " + f);
        }
    } // End of init(f)

    /**
     * Associates the given image with the given file name
     *
     * @param fileName The file name for the image
     * @param image    The image whihc will be associated with the file name
     */
    private void init(String fileName, BufferedImage image) {
        try {
            setPath(fileName);
            myImage = image;
            //myDisplay = new ImageFrame(fileName);
            myPixels = imageToPixels(myImage);

            this.width = image.getWidth();
            this.height = image.getHeight();

            System.out.println("width = " + this.width);
            System.out.println("height = " + this.height);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ImageFile: This is not an image file " + fileName);
        }
    } // End of init(fileName, image)


    /**
     * Maps an array of integers to an array of pixels.
     *
     * @param pixels An array of pixels.
     * @param width  The width of the image.
     * @param height The height of the image.
     * @return The final array of pixels.
     */
    private Pixel[] integersToPixels(int[] pixels, int width, int height) {
        if (pixels == null) {
            throw new RuntimeException(String.format(
                    "ImageFile: No pixels for %d %d\n", width, height));
        }
        Pixel[] pix = new Pixel[pixels.length];
        // System.out.printf("creating %d pixels on %d
        // %d\n",pix.length,width,height);
        for (int i = 0; i < pixels.length; i++) {
            // System.out.printf("pix at %d %d %d\n", i/width,i%width,i);
            pix[i] = new Pixel(pixels[i], i % width, i / width);
        }
        // System.out.printf("returning %d\n", pix.length);
        return pix;
    } // End of intsToPixels(pixels,width,height)


    /**
     * Provides access to this image one pixel at a time.
     *
     * @return An <code>Iterable</code> that will allow access to each pixel
     * in this image
     */
    public Iterable<Pixel> pixels() {
        if (myPixels == null) {
            throw new RuntimeException(
                    "ImageFile: File is not ready to iterate over pixels");
        }
        return Arrays.asList(myPixels);
    } // End of pixels()


    /**
     * Maps an array of pixels to an array of integers.
     *
     * @param pixels The array of pixels received as an argument.
     * @return An array of integers.
     */
    private int[] pixelsToIntegers(Pixel[] pixels) {
        int[] pix = new int[pixels.length];
        for (int i = 0; i < pixels.length; i++)
            pix[i] = pixels[i].getValue();
        return pix;
    } // End of pixelsToIntegers(pixels)


    /**
     * Saves the image as a JPEG using its current file name or opens a
     * file selection dialog box to allow the user to choose a name if no
     * file name set (for example, if this image was created as a blank
     * sized image).
     *
     * @throws RuntimeException Throws an exception if the current file name
     *                          cannot be accessed.
     */
    public void save() throws RuntimeException {
        if (myFileName.isEmpty()) {
            saveAs();
        }
        try {
            updateImage();
            File file = new File(myPath + myFileName);
            ImageIO.write(myImage, "jpg", file);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ImageFile: Unable to save image to a file.", e);
        }
    } // End of save()


    /**
     * Saves the image as a JPEG by opening a file selection dialog box to
     * allow the user to choose the new name for the file.
     *
     * @throws RuntimeException Throws an exception if no file is selected
     *                          by the user
     */
    public void saveAs() throws RuntimeException {
//        File f = FileDialogSelectionBox.saveFile(
//                ImageIO.getWriterFileSuffixes());
        File f = new File(myPath + myFileName);
        if (f == null) {
            throw new RuntimeException(
                    "ImageFile: No file chosen to save.");
        } else {
            try {
                setPath(f.getCanonicalPath());
                save();
            } catch (Exception e) {
                // This should never happen because we got the file from
                // a dialog box.
                throw new RuntimeException(
                        "ImageFile: unable to save image to " + f, e);
            } // End of catch (Exception e)
        } // End of else
    } // End of saveAs()


    /**
     * Sets the file name associated with this image. This is useful, for
     * example, when saving the results of changes to this image in a
     * different file than the original.
     *
     * @param name The new name for the file.
     */
    public void setFileName(String name) {
        if (!name.isEmpty()) {
            myFileName = name;
            //myDisplay.setTitle(myFileName);
        }
    } // End of setFileName(name)


    /**
     * Breaks the path into the root and simple file name
     *
     * @param fileName The string which will be separated into root path
     *                 and file name.
     */
    private void setPath(String fileName) {
        int index = fileName.lastIndexOf(File.separator);
        if (index == -1) {
            myPath = "";
        } else {
            myFileName = fileName.substring(index + 1);
            myPath = fileName.substring(0, index + 1);
        }
    } // End of setPath(fileName)


    /**
     * Updates the image to reflect the new pixel values.
     */
    private void updateImage() {
        int width = myImage.getWidth();
        int height = myImage.getHeight();
        myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        myImage.setRGB(0, 0, width, height, pixelsToIntegers(myPixels), 0, width);
    }  // End of updateImage()
} // End of public ImageFile()
