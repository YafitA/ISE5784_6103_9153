package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class to present Sphere
 */
public class Sphere extends RadialGeometry {

    private final Point center;

    /**
     * Constructs Sphere object with the center and radius
     *
     * @param center the middle point
     * @param radius the shape's radius
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        return p.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return List.of();
    }
}