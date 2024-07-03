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

        //axis.headPoint + t * axis.vector (or simply the head point if the projection (t) is Zero)
        //normalize(given point - o)
        return point.subtract(axis.getPoint(t)).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}