package geometries;


import primitives.*;


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
        //Ray starts at the center of sphere
        if(ray.getHead().equals(this.center)) {
            return List.of(ray.getPoint(this.radius));
        }
        Vector u = this.center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());

        double d = Math.sqrt(u.lengthSquared()- tm * tm);
        if(d>=this.radius)// No intersections
            return null;
        double th = Math.sqrt(this.radius*this.radius-d*d);
        double t1=tm+th;
        double t2=tm-th;

        boolean t1Valid = Util.alignZero(t1) > 0;
        boolean t2Valid = Util.alignZero(t2) > 0;

        if (t1Valid && t2Valid) {
            // Both intersection points are valid
            Point p1 = ray.getHead().add(ray.getDirection().scale(t1));
            Point p2 = ray.getHead().add(ray.getDirection().scale(t2));
            return List.of(p1, p2);
        } else if (t1Valid) {
            // Only the first intersection point is valid
            Point p1 = ray.getHead().add(ray.getDirection().scale(t1));
            return List.of(p1);
        } else if (t2Valid) {
            // Only the second intersection point is valid
            Point p2 = ray.getHead().add(ray.getDirection().scale(t2));
            return List.of(p2);
        } else {
            // No valid intersection points
            return null;
        }
    }
}