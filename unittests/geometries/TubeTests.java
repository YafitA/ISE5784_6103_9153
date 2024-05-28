package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
public class TubeTests {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        Tube tube = new Tube(new Ray(Point.ZERO, new Vector(1, 0, 0)), 10);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Normal calculation
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(1, 1, 0)),
                "ERROR: getNormal() is wrong");

        // =============== Boundary Values Tests ==================
        //TC01: When connecting the point to the top of the beam
        //of the axis of the cylinder makes a right angle with the axis -
        // the point "is in front of the head of the horn" when (P-P0) is orthogonal to v
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 10, 0)),
                "ERROR: (P-P0) is orthogonal to v");
    }
}