package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * interface to represent Geometry
 */
public abstract class Geometry extends Intersectable {

    /**
     * Emission color
     */
    protected Color emission = Color.BLACK;

    /**
     * gets emission's value
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * set Emission
     * @param emission  New emission color
     * @return this
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * returns the normal to the geometry at the given point
     *
     * @param point on the geometric body
     * @return vertical normal to the point
     */
    public abstract Vector getNormal(Point point);
}
