package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class to present Cylinder
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * Constructs Cylinder object with the height, axis and radius
     *
     * @param height tube's height
     * @param axis   tube's axis
     * @param radius tube's radius
     */
    public Cylinder(double height, Ray axis, double radius) {
        super(axis, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
