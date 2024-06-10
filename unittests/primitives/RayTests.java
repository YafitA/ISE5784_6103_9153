package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Ray class
 */
public class RayTests {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    public void testGetPoint() {

        final Point head = new Point(1, 2, 3);
        final Vector direction = new Vector(1, 0, 0);
        final Ray ray = new Ray(head, direction);

        // ============ Equivalence Partitions Tests ==============
        //TC01: positive distance
        Point expectedPositive = new Point(3, 2, 3);
        assertEquals(expectedPositive, ray.getPoint(2), "Positive distance test failed");

        //TC02: negative distance
        Point expectedNegative = new Point(-1, 2, 3);
        assertEquals(expectedNegative, ray.getPoint(-2), "Negative distance test failed");

        // =============== Boundary Values Tests ==================
        //TC03: distance = 0
        double tZero = 0;
        assertEquals(head, ray.getPoint(tZero), "Zero distance test failed");
    }
}