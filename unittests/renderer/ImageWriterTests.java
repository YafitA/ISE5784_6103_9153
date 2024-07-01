package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import static java.awt.Color.*;

/**
 * Test for ImageWriter class
 */
class ImageWriterTests {

    /**
     * Test method for
     * {@link ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        final int nX = 801, nY = 501, step = 50;
        //Create a 800x500 imageWriter
        ImageWriter imageWriter = new ImageWriter("imageTest", nX, nY);

        //Colors to color the grid and the background
        Color gridColor = new Color(MAGENTA);
        Color bgColor = new Color(CYAN);
        //coloring the image
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                imageWriter.writePixel(i, j, i % step == 0 || j % step == 0 ? gridColor : bgColor);
            }
        }

        //producing the image file
        imageWriter.writeToImage();
    }
}