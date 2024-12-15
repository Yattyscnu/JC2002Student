package UoASCNU.images;

/**
 * The <code>Pixel</code> class represents a colour as its component values of
 * red, green, blue, as well as alpha (for transparency).
 * <p>
 * Each of the component values of a pixel must have a value between 0 and
 * 255. If a value is given outside that range, it is changed to be within
 * that range. As such, a negative value would be set to 0 and a value greater
 * than 255 would be set to 255.
 *
 * @author Marco Palomino
 */

public class Pixel {
    static final int MAX_VALUE = 255;
    private int alpha = MAX_VALUE, red = 0, green = 0, blue = 0;

    /**
     * The x-coordinate of this pixel in the image.
     */
    private final int pixelX;

    /**
     * The y-coordinate of this pixel in the image.
     */
    private final int pixelY;

    /**
     * Constructor: Creates a Pixel from an integer value.
     *
     * @param i The integer value representing all the color components.
     * @param x The x-coordinate of this pixel in the image.
     * @param y The y-coordinate of this pixel in the image.
     */
    public Pixel(int i, int x, int y) {
        pixelX = x;
        pixelY = y;
        setValue(i);
    } // End of constructor


    /**
     * Returns the value of the pixel's blue component.
     *
     * @return The pixel's blue value within the range [0, 255].
     */
    public int getBlue() {
        return blue;
    } // End of getBlue()


    /**
     * Returns the value of the pixel's green component.
     *
     * @return The pixel's green value within the range [0, 255].
     */
    public int getGreen() {  
        return green;
    } // End of getGreen()


    /**
     * Returns the value of the pixel's red component.
     *
     * @return The pixel's red value within the range [0, 255].
     */
    public int getRed() {
        return red;
    } // End of getRed()


    /**
     * Returns the integer value of the pixel.
     *
     * @return the integer value of the pixel.
     */
    int getValue() {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    } // End of getValue ()

    /**
     * Resets the value of the pixel's blue component to the value passed as
     * a parameter. If the parameter is not in the range [0, 255], it is
     * changed to be in that range.
     *
     * @param b The blue value
     */
    public void setBlue(int b) {
        blue = guard(b);
    } // End of setBlue(g)

    /**
     * Resets the value of the pixel's green component to the value passed as
     * a parameter. If the parameter is not in the range [0, 255], it is
     * changed to be in that range.
     *
     * @param g The green value
     */
    public void setGreen(int g) {
       green = guard(g);
    } // End of setGreen(g)

    /**
     * Resets the value of the pixel's red component to the value passed as
     * a parameter. If the parameter is not in the range [0, 255], it is
     * changed to be in that range.
     *
     * @param r The red value
     */
    public void setRed(int r) {
        red = guard(r);
    } // End of setRed(int r)

    /**
     * Resets the pixel to an integer value.
     *
     * @param pixel The integer value representing all the color components.
     */
    public void setValue(int pixel) {
        alpha = (pixel >> 24) & 0xff;
        red = (pixel >> 16) & 0xff;
        green = (pixel >> 8) & 0xff;
        blue = (pixel) & 0xff;
    } // End of setValue (int pixel)

    /**
     * Returns the pixel's x-coordinate within the image.
     *
     * @return The x-coordinate of this pixel.
     */
    public int getX() {
        return pixelX;
    } // End of getX()

    /**
     * Returns the pixel's y-coordinate within the image.
     *
     * @return The y-coordinate of this pixel.
     */
    public int getY() {
        return pixelY;
    } // End of getY()

    /**
     * Ensures that the given value remains within the valid range for a
     * pixel. Each of the component values of a pixel must have a value
     * between 0 and 255. If a value is given outside that range, it is
     * changed to be within that range. As such, a negative value would be
     * set to 0 and a value greater than 255 would be set to 255.
     *
     * @param value The given value
     * @return A value within the valid limits.
     */
    private int guard(int value) {
        if (value<0){
            return 0;
        }else if (value>255){
            return 255;
        }else{
            return value;
        }
    } // End of guard(value)
} // End of class Pixel
