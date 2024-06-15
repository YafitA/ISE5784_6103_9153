package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final static double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: ensure normal is correct
        final Point p000 = new Point(0, 0, 0);
        final Point p010 = new Point(0, 1, 0);
        final Point p001 = new Point(0, 0, 1);
        Triangle triangle = new Triangle(p000, p010, p001);
        final Vector exp = triangle.getNormal(p000);
        assertTrue(exp.equals(new Vector(1, 0, 0)) || exp.equals(new Vector(-1, 0, 0)),
                "ERROR: GetNormal() does not work correctly");
        //ensure that the normal is orthogonal to the vector between the points
        assertEquals(0, triangle.getNormal(p010).dotProduct(p000.subtract(p010)), DELTA);
        assertEquals(0, triangle.getNormal(p000).dotProduct(p000.subtract(p001)), DELTA);
        // ensure |result| = 1
        assertEquals(1, triangle.getNormal(new Point(0, 0, 0)).length(), DELTA, "ERROR: Normal is len not 1");
    }

    /**
     * est method for {@link geometries.Triangle#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {

        final Point p100 = new Point(1, 0, 0);
        final Triangle triangle = new Triangle(new Point(-1, 0, 0), p100, new Point(0, 2, 0));
        final Vector v001 = new Vector(0, 0, 1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is inside polygon/triangle (1 point)
        final var result1 = triangle.findIntersections(new Ray(new Point(0, 1, -1), v001));
        final var exp1 = List.of(new Point(0, 1, 0));
        assertEquals(1, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray's line is inside polygon/triangle");
        // TC02: Ray's line is Outside against edge (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, -1, -1), v001)),
                "Ray's line is Outside against edge");
        // TC03: Ray's line is Outside against vertex (0 points)
        assertNull(triangle.findIntersections(new Ray(new Point(0, 3, 0), v001)),
                "Ray's line is Outside against edge");

        // =============== Boundary Values Tests ==================
        // **** Group: the ray begins "before" the plane
        // TC04: Ray's line is on edge (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0, -1), v001)),
                "Ray's line is on edge");
        // TC05: Ray's line is in vertex (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(1, 0, -1), v001)),
                "Ray's line is in vertex");
        // TC05: Ray's line is on edge's continuation (0 point)
        assertNull(triangle.findIntersections(new Ray(new Point(2, 0, -1), v001)),
                "Ray's line is on edge's continuation");
    }
}