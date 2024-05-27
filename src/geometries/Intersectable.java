package geometries;

/**
 * interface to present intersect
 */

import primitives.Point;
import primitives.Ray;
import java.util.List;

public interface Intersectable {

    /**
     * finds the points that intersect with the ray and the shape
     * @param ray that intersect with the shape
     * @return list of points
     */
    List<Point> findIntersections(Ray ray);
}
