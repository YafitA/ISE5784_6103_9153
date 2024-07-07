package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**
 * Represents an object of PointLight
 */
public class PointLight extends Light implements LightSource {

    /**
     * Light's position
     */
    protected final Point position;

    /**
     * The constant attenuation factor.
     * This factor determines the constant attenuation of the light.
     */
    private double kC = 1d;

    /**
     * The linear attenuation factor.
     * This factor determines the linear attenuation of the light.
     */
    private double kL = 0d;

    /**
     * The quadratic attenuation factor.
     * This factor determines the quadratic attenuation of the light.
     */
    private double kQ = 0d;

    /**
     * Create an object of type PointLight
     *
     * @param intensity Light's color intensity
     * @param position  Light's position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Setter for kC
     *
     * @param kC constant factor
     * @return the PointLight itself
     */
    public PointLight setKC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL
     *
     * @param kL linear factor
     * @return the PointLight itself
     */
    public PointLight setKL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter for kQ
     *
     * @param kQ quadratic factor
     * @return the PointLight itself
     */
    public PointLight setKQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        return intensity.scale(1d / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
