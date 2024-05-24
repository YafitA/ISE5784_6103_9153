package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTests {

    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure normal is correct
        Triangle triangle = new Triangle(new Point(0, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        assertEquals(new Vector(1, 0, 0),
                triangle.getNormal(new Point(0, 0, 0)),
                "ERROR: GetNormal() does not work correctly");
        // ensure |result| = 1
        assertEquals(1, triangle.getNormal(new Point(0, 0, 0)).length(), DELTA, "ERROR: Normal is len not 1");

    }
}