package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTest {

    private final double DELTA = 0.000001;

    /**
     * Test method for {@link primitives.Vector#Vector()}.
     */
    @Test
    public void testConstructor(){

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        //TC: Create vector zero
        assertThrows(IllegalArgumentException.class,
                ()-> new Vector(0, 0, 0),
         "ERROR: zero vector does not throw an exception");

        assertThrows(IllegalArgumentException.class,
                ()-> new Vector(Double3.ZERO),
                "ERROR: zero vector does not throw an exception");



    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct() {
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
    }
}