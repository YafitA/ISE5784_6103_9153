package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

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

        var intersection = plane.findIntersections(ray);
        if (intersection == null) return null;

        Point ph = ray.getHead();
        Vector v = ray.getDirection();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Vector v1 = p1.subtract(ph);
        Vector v2 = p2.subtract(ph);
        Vector n1 = v1.crossProduct(v2).normalize();
        double t1 = alignZero(v.dotProduct(n1));
        if (t1 == 0) return null;

        Point p3 = vertices.get(2);
        Vector v3 = p3.subtract(ph);
        Vector n2 = v2.crossProduct(v3).normalize();
        double t2 = alignZero(v.dotProduct(n2));
        if (t1 * t2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double t3 = alignZero(v.dotProduct(n3));
        if (t1 * t3 <= 0) return null;

        return intersection;
    }
}