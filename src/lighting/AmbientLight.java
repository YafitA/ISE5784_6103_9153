package lighting;

import primitives.Double3;
import primitives.Color;


/**
 * Class represents an ambient Light
 */
public class AmbientLight {

    /**
     * Ambient light intensity
     */
    final private Color intensity;

    /**
     * Ambient light color black
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Params constructor
     *
     * @param iA To original fill light
     * @param kA Fill light attenuation coefficient (Double3)
     */
    public AmbientLight(Color iA, Double3 kA) {
        intensity = iA.scale(kA);
    }

    /**
     * Params constructor
     *
     * @param iA To original fill light
     * @param kA Fill light attenuation coefficient (double)
     */
    public AmbientLight(Color iA, double kA) {
        intensity = iA.scale(kA);
    }

    /**
     * Get Intensity value
     *
     * @return Ambient light intensity
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
