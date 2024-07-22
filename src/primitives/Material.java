package primitives;

/**
 * Class Material
 */
public class Material {

    /**
     * Reflection factor
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Transparency factor
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Speculates factor
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Diffuseness factor
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The level of concentration of the lightning (the component specular).
     */
    public int nShininess = 0;

    // Parameters for glossy surface and diffusive glass affect
    /**
     * Number of rays for blur glass effect.
     */
    public int numOfBlurRays = 1;

    /**
     * Distance for blur glass effect.
     */
    public double blurGlassEffectDistance = 0;

    /**
     * Radius for blur glass effect.
     */
    public double blurGlassEffectRadius = 0;

    /**
     * setter in a builder design for Reflection factor
     *
     * @param kR Reflection factor
     * @return the new material
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter in a builder design for Reflection factor
     *
     * @param kR Reflection factor
     * @return the new material
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter in a builder design for Transparency factor
     *
     * @param kT Transparency factor
     * @return the new material
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter in a builder design for Transparency factor
     *
     * @param kT Transparency factor
     * @return the new material
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter in a builder design for Speculates factor
     *
     * @param kS Speculates factor
     * @return the Material itself
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter in a builder design for Speculates factor
     *
     * @param kS Speculates factor
     * @return the Material itself
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter in a builder design for Diffuseness factor
     *
     * @param kD Diffuseness factor
     * @return the Material itself
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter in a builder design for Diffuseness factor
     *
     * @param kD Diffuseness factor
     * @return the Material itself
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }


    /**
     * setter for nShininess
     *
     * @param nShininess the component specular
     * @return the Material itself
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Setter for numOfBlurRays.
     *
     * @param numOfBlurRays The number of rays to set.
     * @return This Material object.
     * @throws IllegalArgumentException if number is less than 1.
     */
    public Material setNumOfBlurRays(int numOfBlurRays) {
        if (numOfBlurRays < 1)
            throw new IllegalArgumentException("Illegal argument in setNumOfBlurRays");
        this.numOfBlurRays = numOfBlurRays;
        return this;
    }

    /**
     * Setter for blurGlassEffectDistance.
     *
     * @param blurGlassEffectDistance The distance to set.
     * @return This Material object.
     * @throws IllegalArgumentException if number is less than 0.
     */
    public Material setBlurGlassEffectDistance(double blurGlassEffectDistance) {
        if (blurGlassEffectDistance < 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassEffectDistance");
        this.blurGlassEffectDistance = blurGlassEffectDistance;
        return this;
    }

    /**
     * Setter for blurGlassEffectRadius.
     *
     * @param blurGlassEffectRadius The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if number is less than 0.
     */
    public Material setBlurGlassEffectRadius(double blurGlassEffectRadius) {
        if (blurGlassEffectRadius < 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassEffectRadius");
        this.blurGlassEffectRadius = blurGlassEffectRadius;
        return this;
    }

    /**
     * Sets the parameters for glossy surface and diffusive glass affect.
     *
     * @param numOfBlurRays           The number of rays to set for glossy surface and diffusive glass affect.
     * @param blurGlassEffectDistance The distance to set for glossy surface and diffusive glass affect.
     * @param blurGlassEffectRadius   The radius to set for glossy surface and diffusive glass affect.
     * @return The new material object.
     * @throws IllegalArgumentException if any of the parameters is invalid.
     */
    public Material setBlurGlass(int numOfBlurRays, double blurGlassEffectDistance, double blurGlassEffectRadius) {
        if (numOfBlurRays < 1 || blurGlassEffectDistance < 0 || blurGlassEffectRadius < 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlass");

        this.numOfBlurRays = numOfBlurRays;
        this.blurGlassEffectDistance = blurGlassEffectDistance;
        this.blurGlassEffectRadius = blurGlassEffectRadius;

        return this;
    }
}