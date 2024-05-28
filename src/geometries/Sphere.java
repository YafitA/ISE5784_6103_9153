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
        Vector u = this.center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());
        double d = Math.sqrt(ray.getDirection().lengthSquared()- tm * tm);
        if(d>=this.radius)
            return null;
        double th = Math.sqrt(this.radius*this.radius-d*d);
        double t1=tm+th;
        double t2=tm-th;
        Point p1= this.center.add(ray.getDirection().scale(t1));
        if(t2<0){
            return  List.of(p1);
        }
        Point p2=this.center.add(ray.getDirection().scale(t2));
        return List.of(p1,p2);
    }
}