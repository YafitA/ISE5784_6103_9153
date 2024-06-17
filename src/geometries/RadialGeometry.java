package geometries;

/***
 * abstract class represents radial geometry
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * the shape's radius
     */
    protected final double radius;
    /**
     * the shape's squared radius
     */
    protected final double radiusSquared;

    /**
     * Constructs a radius object with the specified radius
     *
     * @param radius the shape's radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
        this.radiusSquared = radius * radius;
    }
}