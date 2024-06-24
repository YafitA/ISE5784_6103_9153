package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Represents an object of PointLight
 */
public class PointLight extends Light implements LightSource {

    /**
     * Light's position
     */
    protected Point position;

    /**
     * 3 factors
     */
    private double kC=1,kL=0,kQ=0;

    /**
     * Create an object of type PointLight
     * @param intensity Light's intensity
     * @param position  Light's position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Set Kc
     * @param kC factor
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Set Kl
     * @param kL factor
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Set Kq
     * @param kQ factor
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dSq=position.distanceSquared(p);
        return super.getIntensity().scale(1d/(kC + kL *Math.sqrt(dSq)  + kQ * dSq));
    }

    @Override
    public Vector getL(Point p) {
        return position.subtract(p).normalize();
    }
}
