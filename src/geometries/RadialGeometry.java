package geometries;

/***
 * abstract class represents radial geometry
 */
public abstract class RadialGeometry implements Geometry {

    /**
     * the shape's radius
     */
    protected final double radius;

    /**
     * Constructs a radius object with the specified radius
     *
     * @param radius the shape's radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}