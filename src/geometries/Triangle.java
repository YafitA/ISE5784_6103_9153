package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

/**
 * class to present triangle
 */
public class Triangle extends Polygon {

    /**
     * Constructs triangle object with the 3 points
     *
     * @param p1 the 1sd point
     * @param p2 the 2sd point
     * @param p3 the 3sd point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Point ph = ray.getHead();
        Vector v1 = p1.subtract(ph);
        Vector v2 = p2.subtract(ph);
        Vector v3 = p3.subtract(ph);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        Vector v = ray.getDirection();
        double t1 = v.dotProduct(n1);
        double t2 = v.dotProduct(n2);
        double t3 = v.dotProduct(n3);

        //if all dotProduct are > 0 then point is inside
        if (Util.alignZero(t1) > 0 && Util.alignZero(t2) > 0
                && Util.alignZero(t3) > 0) {
            Plane pl = new Plane(p1, p2, p3);
            return plane.findIntersections(ray);
            //super.findIntersections(ray);
        }

        //if all dotProduct are < 0 then point is inside
        if (Util.alignZero(t1) < 0 && Util.alignZero(t2) < 0
                && Util.alignZero(t3) < 0) {
            Plane pl = new Plane(p1, p2, p3);
            return plane.findIntersections(ray);
            //super.findIntersections(ray);
        }

        //else point does not exist
        return null;
    }
}