package geometries;

import primitives.Color;
import primitives.Material;
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
     * The geometry's material
     */
    private Material material = new Material();

    /**
     * gets emission's value
     *
     * @return emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * getter for Material
     *
     * @return geometry's material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set Emission
     *
     * @param emission New emission color
     * @return updated geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * setter for Material
     *
     * @param material The geometry's material
     * @return updated geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
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
