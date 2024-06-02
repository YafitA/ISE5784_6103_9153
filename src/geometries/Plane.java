package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class to present a plane
 */
public class Plane implements Geometry {

    @SuppressWarnings("unused") // TODO remove after it will have been used
    private final Point q;

    private final Vector normal;

    /**
     * gets 3 points and calculates the normal
     *
     * @param p1 first point on plane
     * @param p2 second point on plane
     * @param p3 third point on plane
     * @throws IllegalArgumentException when there are pairs of convergent points or the points are co-linear
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;

        //normal calculation
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Constructs plane object with the specified point and normal
     *
     * @param q      point on the plane
     * @param normal normal to the plane
     */
    @SuppressWarnings("unused") // TODO remove after it will have been used
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * returns the normal
     *
     * @return normal
     */
    public Vector getNormal() {
        return this.normal;
    }

    @Override
    public Vector getNormal(Point p) {
        return this.normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //todo

        //in case p=q
        if(q.equals(ray.getHead())) return null;

        // double nv = n.dotProduct(v);
//        if (isZero(nv))
//            null;
        //Calculate point t - where the ray hits the plane
        double t = (this.normal.dotProduct((q.subtract(ray.getHead())))) / normal.dotProduct(ray.getDirection());

        if (t > 0) {
            Point p = ray.getHead().add(ray.getDirection().scale(t));
            return List.of(p);
        }

        //if t <= 0 there are no points hitting the plane
        return null;
    }
}