package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    private final double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: the first and second point are equal
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(4, 5, 8)),
                "ERROR: Constructor receive two equal points");
        //TC02: The points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)),
                "ERROR: Constructor receive three points that are on the same line");

    }

    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure normal is correct
        Point p000 = new Point(0, 0, 0);
        Point p010 = new Point(0, 1, 0);
        Point p001 = new Point(0, 0, 1);
        Plane plane = new Plane(p000, p010, p001);
        assertEquals(new Vector(1, 0, 0),
                plane.getNormal(), "ERROR: GetNormal() does not work correctly");
        //ensure that the normal is orthogonal to the vector between the points
        assertEquals(0, plane.getNormal().dotProduct(p000.subtract(p010)), DELTA);
        assertEquals(0, plane.getNormal().dotProduct(p000.subtract(p001)), DELTA);
        // ensure |result| = 1
        assertEquals(1, plane.getNormal().length(), DELTA, "ERROR: Normal is len not 1");
    }
}