package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * class to present Tube
 */
public class Tube extends RadialGeometry {

    /**
     * the tube's axis
     */
    protected final Ray axis;

    /**
     * Constructs tube object with the axis and radius
     *
     * @param axis tube's axis
     * @param r    tube's radius
     */
    public Tube(Ray axis, double r) {
        super(r);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        //axis.vector * (given point - axis.headPoint)
        double t = axis.getDirection().dotProduct(point.subtract(axis.getHead()));

        //axis.headPoint + t * axis.vector
        Point o = axis.getHead().add(axis.getDirection().scale(t));

        //normalize(given point - o)
        return point.subtract(o).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //todo: bonus
        return List.of();
    }
}