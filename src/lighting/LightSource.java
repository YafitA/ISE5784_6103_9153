package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface representing a Light Source
 */
public interface LightSource {
    /**
     * Get light intensity at a point I (L)
     * @param p point
     * @return Intensity
     */
    public Color getIntensity(Point p);

    /**
     * get vector L
     * @param p point
     * @return Vector L
     */
    public Vector getL(Point p);

}
