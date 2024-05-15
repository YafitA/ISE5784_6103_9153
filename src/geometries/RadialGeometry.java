package geometries;

public abstract class RadialGeometry {
    /** radius */
    protected final double radius;

    /**
     * Constructs a radius object with the specified radius
     * @param radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}