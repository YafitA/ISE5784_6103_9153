package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class to present a plane
 */
public class Plane extends Geometry {

    /**
     * point on the plane
     */
    private final Point q;

    /**
     * normal to the plane
     */
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        //in case p=q
        if (q.equals(ray.getHead())) return null;

        //check if denominator = 0
        double nv = normal.dotProduct(ray.getDirection());
        if (isZero(nv)) return null;

        //Calculate point t - where the ray hits the plane
        double t = (normal.dotProduct((q.subtract(ray.getHead())))) / nv;

        //there are points hitting the plane OR there aren't
        return alignZero(t) > 0 && alignZero(t - maxDistance) < 0 ? List.of(new GeoPoint(this, ray.getPoint(t))) : null;
    }
}