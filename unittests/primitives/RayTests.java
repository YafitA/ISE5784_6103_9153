package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTests {

    @Test
    public void testGetPoint(){

        Point head = new Point(1, 2, 3);
        Vector direction = new Vector(4, 5, 6);
        Ray ray = new Ray(head, direction);

        // ============ Equivalence Partitions Tests ==============
        //TC01: positive distance
        double tPositive = 2.5;
        Point expectedPositive = new Point(2.1396057645963795,3.424507205745474,4.709408646894569);
        assertEquals(expectedPositive, ray.getPoint(tPositive), "Positive distance test failed");

        //TC02: negative distance
        double tNegative = -2.5;
        Point expectedNegative = new Point(-0.13960576459637952,0.5754927942545256,1.290591353105431);
        assertEquals(expectedNegative, ray.getPoint(tNegative), "Negative distance test failed");

        // =============== Boundary Values Tests ==================
        //TC03: distance = 0
        double tZero = 0;
        Point expectedZero = new Point(1, 2, 3);
        assertEquals(expectedZero, ray.getPoint(tZero), "Zero distance test failed");
    }
}