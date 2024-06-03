package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
public class SphereTests {

    private final static Point p001 = new Point(0, 0, 1);
    private final static Point p100 = new Point(1, 0, 0);

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: a simple test
        Sphere s = new Sphere(p001, 2);
        assertEquals(new Vector(0.7071067811865475, 0.0, -0.7071067811865475),
                s.getNormal(p100), "ERROR: getNormal() is wrong");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp1 = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p300 = new Point(3, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        final Vector vm100 = new Vector(-1, 0, 0);
        final Vector v100 = new Vector(1, 0, 0);
        final Vector v010 = new Vector(0, 1, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();

        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp1, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(new Point(1, 0.5, 0), new Vector(-1.5, 0, 0)))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        final var exp2 = List.of(new Point(0.1339745962155, 0.5, 0));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(exp2, result2, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), v010)),
                "Ray starts after the sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)

        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result3 = sphere.findIntersections(new Ray(p200, vm100));
        assertEquals(1, result3.size(), "Wrong number of points");
        assertEquals(List.of(Point.ZERO), result3, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, v100)),
                "Ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result4 = sphere.findIntersections(new Ray(p300, vm100))
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                .toList();
        final var exp4 = List.of(Point.ZERO, p200);
        assertEquals(2, result4.size(), "Wrong number of points");
        assertEquals(exp4, result4, "Ray's line goes through the center and starts before the sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result5 = sphere.findIntersections(new Ray(p200, vm100));
        final var exp5 = List.of(Point.ZERO);
        assertEquals(1, result5.size(), "Wrong number of points");
        assertEquals(exp5, result5, "Ray's line goes through the center and starts at sphere and goes inside");

        // TC15: Ray starts inside (1 point)
        final var result6 = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), v100));
        final var expP200 = List.of(p200);
        assertEquals(1, result6.size(), "Wrong number of points");
        assertEquals(expP200, result6, "Ray's line goes through the center and Ray starts inside");

        // TC16: Ray starts at the center (1 point)
        final var result7 = sphere.findIntersections(new Ray(p100, v100));
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(expP200, result7, "Ray's line goes through the center and Ray starts at center");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, v100)),
                "Ray's line goes through the center and starts at sphere and goes outside");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p300, v100)),
                "Ray's line goes through the center and starts after sphere");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, -0.5, 0), v010)),
                "Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(p200, v010)), "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(2, 0.5, 0), v010)),
                "Ray starts after the tangent point");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(p300, new Vector(0, 2, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}