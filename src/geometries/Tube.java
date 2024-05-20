package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * class to present Tube
 */
public class Tube extends RadialGeometry {

    /**
     * the tube's axis
     */
    protected final Ray axis;

    /**
     * Constructs tube object with the axis and radius
     *
     * @param axis tube's axis
     * @param r    tube's radius
     */
    public Tube(Ray axis, double r) {
        super(r);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
