package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for primitives.Vector class
 */
public class VectorTest {

    private final static double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     */
    @Test
    public void testConstructor1() {
        // =============== Boundary Values Tests ==================
        //TC01: Create vector zero
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "ERROR: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#Vector(primitives.Double3)}.
     */
    @Test
    public void testConstructor2() {
        // =============== Boundary Values Tests ==================
        //TC01: Create vector zero
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(Double3.ZERO),
                "ERROR: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        //TC01: test length squared
        assertEquals(9.0, v4.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
        Vector v4 = new Vector(1, 2, 2);
        // ============ Equivalence Partitions Tests ==============
        //TC01: test length
        assertEquals(3.0, v4.length(), DELTA, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2 = new Vector(-2, -4, -6);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test of +
        assertEquals(v1Opposite, v1.add(v2), "ERROR: Vector + Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: Result of + is vector zero
        assertThrows(IllegalArgumentException.class,
                () -> v1.add(v1Opposite),
                "ERROR: Vector + itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Point)}.
     */
    @Test
    public void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        /// ============ Equivalence Partitions Tests ==============
        //TC01: Test of -
        assertEquals(new Vector(3, 6, 9), v1.subtract(v2), "ERROR: Vector - Vector does not work correctly");

        // =============== Boundary Values Tests ==================
        //TC02: Result of - is vector zero
        assertThrows(IllegalArgumentException.class,
                () -> v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        /// ============ Equivalence Partitions Tests ==============
        // TC01: test scale result
        assertEquals(v2, v1.scale(-2), "ERROR: Scale() wrong value");

        // =============== Boundary Values Tests ==================
        // TC01: test scale result equal zero vector
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "ERROR: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        /// ============ Equivalence Partitions Tests ==============
        // TC01: test Dot-Product
        assertEquals(-28, v1.dotProduct(v2), DELTA,
                "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // TC02: test Dot-Product for orthogonal vectors
        assertEquals(0, v1.dotProduct(v3), DELTA,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);

        /// ============ Equivalence Partitions Tests ==============
        //TC01: test Cross-Product result
        Vector vr = v1.crossProduct(v3);
        assertEquals(vr.length(), v1.length() * v3.length(), DELTA,
                "ERROR: crossProduct() wrong result length");

        assertEquals(0, vr.dotProduct(v3), DELTA,
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0, vr.dotProduct(v1), DELTA,
                "ERROR: crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================
        //TC03: test Cross-Product equal zero vector
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize() {

        /// ============ Equivalence Partitions Tests =============
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        //TC01: test vector normalization vs vector length and cross-product
        assertEquals(1, u.length(), DELTA, "ERROR: the normalized vector is not a unit vector");

        //TC02: test that the vectors are co-lined
        assertThrows(IllegalArgumentException.class,
                () -> v.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        //TC03: test that the normalized vector is not opposite to the original one
        assertFalse(v.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");

    }
}