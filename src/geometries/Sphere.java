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

        this.boundingBox = new BoundingBox(center.add(new Vector(1, 1, 1).scale(radius)),
                center.add(new Vector(-1, -1, -1).scale(radius)));
    }

    @Override
    public Vector getNormal(Point p) {

        return p.subtract(center).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

        // there is no intersections
        if (!this.boundingBox.intersectionBox(ray))
            return null;

        //Ray starts at the center of sphere
        if (ray.getHead().equals(this.center))
            return List.of(new GeoPoint(this, ray.getPoint(this.radius)));

        Vector u = this.center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDirection());
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = this.radiusSquared - dSquared;
        if (alignZero(thSquared) <= 0) // No intersections
            return null;

        double th = Math.sqrt(thSquared);
        double t2 = alignZero(tm + th);
        //if one of the t's are negative there are no intersections
        //t2 > t1
        if (t2 <= 0) return null;

        double t1 = alignZero(tm - th);
        //if t1 is greater the maxDistance both t1 t2 are not needed (maxDistance=<t1<t2)
        if (alignZero(t1 - maxDistance) >= 0) return null;

        if (t1 <= 0)
            return alignZero(t2 - maxDistance) >= 0
                    //t1<0<maxSize<t2
                    ? null
                    //t1<0<t2<maxSize
                    : List.of(new GeoPoint(this, ray.getPoint(t2)));
        return alignZero(t2 - maxDistance) >= 0
                //0<t1<maxDistance<t2
                ? List.of(new GeoPoint(this, ray.getPoint(t1)))
                //0<t1<t2<maxSize
                : List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
    }
}