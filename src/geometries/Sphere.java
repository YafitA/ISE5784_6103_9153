package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * class to present Sphere
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs Sphere object with the center and radius
     * @param center the middle point
     * @param radius the shape's radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p){
        return null;
    }
}
