package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
public class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        Cylinder cylinder = new Cylinder(2, new Ray(new Point(0, 0, -1), new Vector(0, 0, 1)), 2);
        Vector v = cylinder.axis.getDirection();

        // ============ Equivalence Partitions Tests ==============
        //TC01: Point on the round surface
        assertEquals(cylinder.getNormal(new Point(0, 2, 0)), new Vector(0, 1, 0),
                "ERROR: getNormal() is wrong for point on the wrapper ");

        //TC02: Point on the first base
        assertEquals(v, cylinder.getNormal(new Point(0, 1, 1)),
                "ERROR: getNormal() is wrong for point on the top base ");

        //TC03: Point on the second base
        assertEquals(v, cylinder.getNormal(new Point(0, 1, -1)),
                "ERROR: getNormal() is wrong for point on the bottom base ");

        // ============ Equivalence Partitions Tests ==============
        //TC04: in the center of first base
        assertEquals(v, cylinder.getNormal(new Point(0, 0, 1)),
                "ERROR: getNormal() is wrong for middle point on the top base ");

        //TC05: in the center of second base
        assertEquals(v, cylinder.getNormal(new Point(0, 0, -1)),
                "ERROR: getNormal() is wrong for point on the bottom base ");

        //todo: optional add tc for points on connection between base and wrap
    }
}