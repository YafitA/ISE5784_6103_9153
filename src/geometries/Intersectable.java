package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * interface to present intersect
 */
public interface Intersectable {

    /**
     * finds the points that intersect with the ray and the shape
     * @param ray that intersect with the shape
     * @return list of points
     */
    List<Point> findIntersections(Ray ray);
}
