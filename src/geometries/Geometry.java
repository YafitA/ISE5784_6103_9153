package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * interface to represent Geometry
 */
public interface Geometry {
    /**
     * returns the normal to the geometry at the given point
     *
     * @param point on the geometric body
     * @return vertical normal to the point
     */
    public Vector getNormal(Point point);
}
