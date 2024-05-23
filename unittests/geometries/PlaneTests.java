package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    public void testGetNormal1() {
    Plane plane = new Plane(new Point(1,0,0), new Point(0,1,0), new Point(0, 0,1));
    assertEquals(new Vector(0, 0, 1),
            plane.getNormal(),
            "ERROR: GetNormal() does not work correctly");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal2() {
    }
}