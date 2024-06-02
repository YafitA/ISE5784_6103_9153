package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * class to present Cylinder
 */
public class Cylinder extends Tube {

    private final double height;

    /**
     * Constructs Cylinder object with the height, axis and radius
     *
     * @param height tube's height
     * @param axis   tube's axis
     * @param radius tube's radius
     */
    public Cylinder(double height, Ray axis, double radius) {
        super(axis, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        //axis = p0 + dv
        Point p0 = axis.getHead();
        Vector v = axis.getDirection();

        //if it's the base's center point return  v
        if (point.equals(p0)) return v;

        //vector from the middle
        Vector u = point.subtract(p0);
        double t = v.dotProduct(u);

        //check if the point is on one of the bases
        //if the vectors (basePoint - Point) and the tube's ray vector are horizontal - the point is on the base
        if (isZero(t) || isZero(t - height)) return v;

        //else - point is on wrapper
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //todo: bonus
        return null;
    }
}