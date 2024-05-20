package geometries;

import primitives.Point;

/**
 * class to present triangle
 */
public class Triangle extends Polygon {

    /**
     * Constructs triangle object with the 3 points
     * @param p1 the 1sd point
     * @param p2 the 2sd point
     * @param p3 the 3sd point
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(new Point[]{p1, p2, p3});
    }
}
