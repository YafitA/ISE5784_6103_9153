package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RayTests {

    @Test
    public void testGetPoint() {

        Point head = new Point(1, 2, 3);
        Vector direction = new Vector(1, 0, 0);
        Ray ray = new Ray(head, direction);

        // ============ Equivalence Partitions Tests ==============
        //TC01: positive distance
        double tPositive = 2;
        Point expectedPositive = new Point(3, 2, 3);
        assertEquals(expectedPositive, ray.getPoint(tPositive), "Positive distance test failed");

        //TC02: negative distance
        double tNegative = -2;
        Point expectedNegative = new Point(-1, 2, 3);
        assertEquals(expectedNegative, ray.getPoint(tNegative), "Negative distance test failed");

        // =============== Boundary Values Tests ==================
        //TC03: distance = 0
        double tZero = 0;
        assertThrows(IllegalArgumentException.class, () -> ray.getPoint(tZero), "Zero distance test failed");
    }
}