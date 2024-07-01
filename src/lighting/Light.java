package lighting;

import primitives.Color;

/**
 * Class representing Light
 */
abstract class Light {
    /**
     * The light's intensity color
     */
    protected final Color intensity;

    /**
     * Construct an object of Light
     *
     * @param intensity The light's intensity color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Get Intensity's value
     *
     * @return Intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
