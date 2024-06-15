package geometries;


import primitives.*;


import java.util.List;

import static primitives.Util.alignZero;

/**
 * class to present Sphere
 */
public class Sphere extends RadialGeometry {

    /**
     * the middle point
     */
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

        //Ray starts at the center of sphere
        if (ray.getHead().equals(this.center)) {
            return List.of(ray.getPoint(this.radius));
        }

        Vector u = this.center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = this.radiusSquared - dSquared;
        if (alignZero(thSquared) <= 0)// No intersections
            return null;

        double th = Math.sqrt(thSquared);
        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        return t1 <= 0
                ? List.of(ray.getPoint(t2))
                : List.of(ray.getPoint(t1), ray.getPoint(t2));
    }
}