package geometries;

//import jdk.internal.access.JavaTemplateAccess;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
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

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {

        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray hits plane
        assertEquals(List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray does not hit plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane (not on it)
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: Ray on plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC16: Orthogonal ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC17: Ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

        // TC18: Ray from plane's Q point
        assertNull(pl.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0))),
                "Must not be plane intersection");
    }

    /**
     * Test method for {@link geometries.Plane#findGeoIntersectionsHelper(Ray, double)}.
     */
    @Test
    void testFindGeoIntersectionsHelper() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        double maxDistance = 10.0;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray hits plane
        assertEquals(List.of(new Intersectable.GeoPoint(pl, new Point(1, 0, 0))),
                pl.findGeoIntersectionsHelper(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)), maxDistance),
                "Bad plane intersection");

        // TC02: Ray does not hit plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0)), maxDistance),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane (not on it)
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1)), maxDistance),
                "Must not be plane intersection");

        // TC12: Ray on plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(0, 0.5, 0.5), new Vector(0, 1, -1)), maxDistance),
                "Must not be plane intersection");

        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Intersectable.GeoPoint(pl, new Point(1d / 3, 1d / 3, 1d / 3))),
                pl.findGeoIntersectionsHelper(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1)), maxDistance),
                "Bad plane intersection");

        // TC14: Orthogonal ray out of plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1)), maxDistance),
                "Must not be plane intersection");

        // TC15: Orthogonal ray out of plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1)), maxDistance),
                "Must not be plane intersection");

        // TC16: Orthogonal ray from plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1)), maxDistance),
                "Must not be plane intersection");

        // TC17: Ray from plane
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0)), maxDistance),
                "Must not be plane intersection");

        // TC18: Ray from plane's Q point
        assertNull(pl.findGeoIntersectionsHelper(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0)), maxDistance),
                "Must not be plane intersection");
    }
}