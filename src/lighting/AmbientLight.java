package lighting;

import primitives.Double3;
import primitives.Color;


/**
 * Class represents an ambient Light
 */
public class AmbientLight extends Light {

    /**
     * Ambient light color black
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Params constructor
     *
     * @param iA To original fill light
     * @param kA Fill light attenuation coefficient (Double3)
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * Params constructor
     *
     * @param iA To original fill light
     * @param kA Fill light attenuation coefficient (double)
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }

}
