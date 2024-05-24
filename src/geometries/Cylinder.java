package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
    public Vector getNormal(Point point){
        //if the dot product between the vector of the axis direction
        //and the vector between the point and axis head are horizontal
        //the point is on the base
        if(Util.isZero(point.subtract(this.axis.getHead()).
           dotProduct(this.axis.getDirection())))
            return this.axis.getDirection();
        else //point is on wrapper
            return super.getNormal(point);
    }
}
