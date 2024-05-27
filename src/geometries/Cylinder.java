package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
        //check if the point is on one of the bases
        //if the vectors (basePoint - Point) and the tube's ray vector are horizontal - the point is on the base

        //get the base Point of the first base
        //point = axis.head + high * axis.vector
        Point basePoint1 = this.axis.getHead().add(this.axis.getDirection().scale(this.height));

        //get the base Point of the second base
        //point = axis.head
        Point basePoint2 = this.axis.getHead();

        if (point.equals(basePoint1) || Util.isZero(basePoint1.subtract(point).dotProduct(this.axis.getDirection())))
            return this.axis.getDirection();

        if (point.equals(basePoint2) || Util.isZero(basePoint2.subtract(point).dotProduct(this.axis.getDirection())))
            return this.axis.getDirection();
        //else - point is on wrapper
        return super.getNormal(point);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //todo: bonus
        return List.of();
    }
}