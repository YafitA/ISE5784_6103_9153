package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 *Test for ImageWriter class
 */
class ImageWriterTests {

    /**
     * Test method for
     * {@link ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        Color gridColor = new Color(java.awt.Color.magenta);
        Color backgroundColor=new Color(java.awt.Color.cyan);
        ImageWriter imageWriter=new ImageWriter("imageTest",800,500);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, i % 50 == 0 || j % 50 == 0 ? gridColor : backgroundColor);
            }
        }
        imageWriter.writeToImage();
    }

}