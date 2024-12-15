package UoASCNU;

import UoASCNU.processing.GrayscaleConverter;
import java.io.File;

public class TestProcessing {
    public static void main(String[] args) {
        if (args.length > 0) {
            File[] imageFiles = new File[args.length];
            int counter = 0;

            // Iterating over the args array using a for
            // each loop.
            for (String argument : args) {
                imageFiles[counter++] = new File(argument);
            }

            GrayscaleConverter.convertAndSave(imageFiles);
        } else
            // Print statements
            System.out.println(
                    "No command line arguments found.");
    } // End of main
} // End of class TestProcessing
