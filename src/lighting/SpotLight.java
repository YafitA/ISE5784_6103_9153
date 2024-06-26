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
    private Vector direction;

    /**
     * Narrowness of spotlight
     */
    private int narrowBeam = 1;

    @Override
    public Color getIntensity(Point p) {
        double dotProduct = alignZero(direction.dotProduct(getL(p)));
        return super.getIntensity().scale(dotProduct > 0 ? Math.pow(dotProduct, narrowBeam) : 0);
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

    /**
     * Setter for NarrowBeam
     * @param narrowBeam Narrowness of spotlight
     * @return the SpotLight itself
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
