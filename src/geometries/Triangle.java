package geometries;

import primitives.Point;
import primitives.Ray;

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
        //todo: bonus
        return List.of();
    }
}
