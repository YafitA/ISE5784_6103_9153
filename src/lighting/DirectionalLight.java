package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class for directionalLight
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * Light's direction
     */
    private final Vector direction;

    /**
     * Construct an object of Light
     *
     * @param intensity The light's intensity color
     * @param direction Light's direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
