package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * class SpotLight
 */
public class SpotLight extends PointLight {


    /**
     * Spotlight's direction
     */
    private final Vector direction;

    /**
     * Narrowness of spotlight
     */
    private double narrowBeam = 1;


    /**
     * Create an object of type PointLight
     *
     * @param intensity Light's intensity
     * @param position  Light's position
     * @param direction Spotlight's direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKC(double kC) {
        return (SpotLight) super.setKC(kC);
    }

    @Override
    public SpotLight setKL(double kL) {
        return (SpotLight) super.setKL(kL);
    }

    @Override
    public SpotLight setKQ(double kQ) {
        return (SpotLight) super.setKQ(kQ);
    }

    @Override
    public Color getIntensity(Point p) {
        final double dirL = alignZero(direction.dotProduct(super.getL(p)));
        return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dirL, narrowBeam));
    }

    /**
     * Setter for NarrowBeam
     *
     * @param narrowBeam Narrowness of spotlight
     * @return the SpotLight itself
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
