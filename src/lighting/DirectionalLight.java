package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    /**
     * Light's direction
     */
    private Vector direction;

    /**
     * Construct an object of Light
     * @param intensity The light's intensity color
     * @param direction Light's direction
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
