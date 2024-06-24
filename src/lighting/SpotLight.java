package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

public class SpotLight extends PointLight{
    /**
     * Spotlight's direction
     */
    private Vector direction;

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    @Override
    public Color getIntensity(Point p) {
        double dotProduct=alignZero(this.direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(dotProduct>0?dotProduct:0);
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
        this.direction=direction;
    }


    public SpotLight setKc(double kC) {
        return (SpotLight)super.setKc(kC);
    }


    public SpotLight setKl(double kL) {
        return (SpotLight)super.setKl(kL);
    }

    //TODO

    public SpotLight setKq(double kQ) {
        return (SpotLight)super.setKl(kQ);
    }
}
