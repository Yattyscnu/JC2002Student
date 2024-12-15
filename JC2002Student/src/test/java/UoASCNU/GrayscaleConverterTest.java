package UoASCNU;

import UoASCNU.processing.GrayscaleConverter;
import UoASCNU.images.ImageFile;
import UoASCNU.images.Pixel;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GrayscaleConverterTest {

    private ImageFile imageFile;

    @BeforeEach
    public void setup() {
        imageFile = new ImageFile(new File("src/test/resources/images/bald_eagle.jpg"));
    }

    @Test
    public void testPixelSetAndGetMethods() {
        // Stage 1: Tests for Pixel class getters and setters
        Pixel pixel = new Pixel(0, 0, 0);
        pixel.setRed(100);
        pixel.setGreen(150);
        pixel.setBlue(200);
        
        assertEquals(100, pixel.getRed());
        assertEquals(150, pixel.getGreen());
        assertEquals(200, pixel.getBlue());
    }

    @Test
    public void testImageFileDimensions() {
        // Stage 2: Tests for ImageFile width and height methods
        assertEquals(500, imageFile.getWidth()); // Assuming sample image has width of 300
        assertEquals(348, imageFile.getHeight()); // Assuming sample image has height of 200
    }

    @Test
    public void testMakeGray() {
        // Stage 3: Test grayscale conversion logic
        ImageFile grayImage = GrayscaleConverter.makeGray(imageFile);
        Pixel originalPixel = imageFile.getPixel(10, 10);
        Pixel grayPixel = grayImage.getPixel(10, 10);

        int average = (originalPixel.getRed() + originalPixel.getGreen() + originalPixel.getBlue()) / 3;

        assertEquals(average, grayPixel.getRed());
        assertEquals(average, grayPixel.getGreen());
        assertEquals(average, grayPixel.getBlue());
    }

    @Test
    public void testConvertAndSave() {
        // Stage 4: Test saving functionality
        File[] imageFiles = { new File("src/test/resources/images/bald_eagle.jpg") };
        GrayscaleConverter.convertAndSave(imageFiles);

        File savedImage = new File("target/output/gray/gray-bald_eagle.jpg");
        assertTrue(savedImage.exists());

        // Clean up test output
        savedImage.delete();
    }
}