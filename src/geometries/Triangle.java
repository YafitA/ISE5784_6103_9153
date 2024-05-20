package geometries;

import primitives.Point;

/**
 * class to present triangle
 */
public class Triangle extends Polygon {

    /**
     * Constructs triangle object with the specified list of points
     *
     * @param vertices list of vertices according to their order by edge path
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(new Point[]{p1, p2, p3});
    }
}
