package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for primitives.Point class
 */

class PointTests {

    /**
     * First point for tests
     */
    private static final Point p1 = new Point(1, 2, 3);
    /**
     * Second point for tests
     */
    private static final Point p2 = new Point(2, 4, 6);
    /**
     * Third point for tests
     */
    private static final Point p3 = new Point(2, 4, 5);
    /**
     * Vector for tests
     */
    private static final Vector v1 = new Vector(1, 2, 3);
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in assertEquals
     */
    private static final double DELTA = 0.00001;

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Adds vector v1 to p1
        assertEquals(p1.add(v1), p2, "ERROR: (point1 + vector) != point2 and does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: subtracts p1 from p2
        assertEquals(p2.subtract(p1), v1, "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: subtracts point from itself
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: distance between p1 to p3
        assertEquals(3, p1.distance(p3), DELTA, "distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC01: distance between p1 to p1
        assertEquals(0, p1.distance(p1), DELTA, "point distance to itself is not zero");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: distance squared between p1 to p3
        assertEquals(9, p1.distanceSquared(p3), DELTA, "squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC01: distance squared between p1 to p1
        assertEquals(0, p1.distanceSquared(p1), DELTA, "point distance to itself is not zero");
    }
}