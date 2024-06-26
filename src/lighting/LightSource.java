package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface representing a Light Source
 */
public interface LightSource {
    /**
     * Calculate the light's Intensity at a point
     *
     * @param p point
     * @return Intensity
     */
    public Color getIntensity(Point p);

    /**
     * Calculate the vector from the given point to the light source's
     *
     * @param p the given point
     * @return a normalized vector from a given point to the light
     */
    public Vector getL(Point p);

}
