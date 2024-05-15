package geometries;

import primitives.Point;
import primitives.Vector;

import javax.lang.model.type.NullType;

/**
 * class to present a plane
 */
public class Plane implements Geometry {

    private final Point q;

    private final Vector normal;

    /**
     * gets 3 points and calculates the normal
     * @param p1 first point on plane
     * @param p2 second point on plane
     * @param p3 third point on plane
     */
    public Plane(Point p1, Point p2, Point p3) {
        this.q = p1;
        normal = null;
    }

    /**
     * Constructs plane object with the specified point and normal
     * @param q      point on the plane
     * @param normal normal to the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     *  returns the normal
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Plane other)
            return other.q.equals(q) && other.normal.equals(normal);
        return false;
    }

}