package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight {


    /**
     * Spotlight's direction
     */
    private Vector direction;
    /**
     * Narrowness of spotlight
     */
    private int narrowness = 1;

    @Override
    public Color getIntensity(Point p) {
        double scalar = alignZero(this.direction.dotProduct(getL(p)));
        return scalar <=0? Color.BLACK : super.getIntensity(p).scale(Math.pow(scalar,narrowness));
    }

    /**
     * Create an object of type PointLight
     *
     * @param intensity Light's intensity
     * @param position  Light's position
     * @param direction Spotlight's direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    @Override
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    @Override
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKl(kQ);
    }

    public SpotLight setNarrowBeam(int narrowness) {
        this.narrowness = narrowness;
        return this;
    }
}
