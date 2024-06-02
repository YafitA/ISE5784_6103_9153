package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    @Test
    void testAdd() {
    }

    @Test
    void testFindIntersections() {
        final Sphere s = new Sphere(new Point(0, 1, 1), 0.5);
        final Triangle t = new Triangle(new Point(-1, 0, 0), new Point(1, 0, 0), new Point(0, 2, 0));
        final Plane p = new Plane(new Point(0, 0, 0.5), new Point(0, 2, 0.5), new Point(-1, 0, 0.5));
        final Vector v010 = new Vector(0, 1, 0);
        final Ray r001 = new Ray(new Point(0, 1, 1), v010);

        final Geometries geometries = new Geometries(s, t, p);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Empty collection
        Geometries emptyGeo = new Geometries();
        assertNull(emptyGeo.findIntersections(r001), "Empty collection");
        // TC02: Nobody is intersected
        final Ray r002 = new Ray(new Point(0, 0, 2), v010);
        assertNull(geometries.findIntersections(r002), "No body is intersected");
        // TC03: One body is intersected

        assertEquals(1, geometries.findIntersections(r001).size(), "1 body is intersected");
        // TC04: All bodies are intersecting
        final Ray r012 = new Ray(new Point(0, 1, 2), new Vector(0, 0, -1));
        assertEquals(4, geometries.findIntersections(r012).size(), "All bodies are intersected");
        // =============== Boundary Values Tests ==================
        // TC01: A few bodies are intersecting
        final Ray r01025 = new Ray(new Point(0, 0.25, 1), new Vector(0, 0, -1));
        assertEquals(2, geometries.findIntersections(r01025).size(), " a few bodies are intersected");


    }
}