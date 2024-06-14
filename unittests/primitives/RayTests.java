package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    /**
     * Test method for {@link primitives.Ray#findClosestPoint(List)}.
     */
    @Test
    void testFindClosestPoint() {
        final Ray ray=new Ray(new Point(0,0,4),new Vector(0,0,1));
        final Point p001=new Point(0,0,1);
        final Point p002=new Point(0,0,2);
        final Point p003=new Point(0,0,3);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Closest point is the middle point
        assertEquals(p003, ray.findClosestPoint(List.of(p001,p003,p002)),
                "Closest point is the middle point");

        // =============== Boundary Values Tests ==================
        //TC02: List is empty
        assertNull(ray.findClosestPoint(List.of()), "List is empty");
        //TC03: Closest point is the first point
        assertEquals(p003, ray.findClosestPoint(List.of(p003, p001,p002)),
                "Closest point is the first point");
        //TC04: Closest point is the last point
        assertEquals(p003, ray.findClosestPoint(List.of(p001,p002, p003)),
                "Closest point is the last point");

    }
}