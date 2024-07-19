package primitives;

/**
 * Class Material
 */
public class Material {


    /**
     * The attenuation coefficient of diffusive
     */

    public Double3 kD = Double3.ZERO;
    /**
     * The attenuation coefficients of specular
     */

    public Double3 kS = Double3.ZERO;
    /**
     * The level of concentration of the lightning (the component specular).
     */
    public int nShininess = 0;

    /**
     * Attenuation coefficient of transparency
     */
    public Double3 kT = Double3.ZERO;

    /**
     * attenuation coefficient of reflection
     */
    public Double3 kR = Double3.ZERO;

    // Parameters for blur glass
    public int numOfRays = 1;
    public double blurGlassDistance = 1;
    public double blurGlassRadius = 1;

    /**
     * setter in a builder design for kR
     *
     * @param kR attenuation coefficient of reflection
     * @return the new material
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter in a builder design for kT
     *
     * @param kT Attenuation coefficient of transparency
     * @return the new material
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter in a builder design for kR
     *
     * @param kR attenuation coefficient of reflection
     * @return the new material
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter in a builder design for kT
     *
     * @param kT Attenuation coefficient of transparency
     * @return the new material
     */
    public Material setKT(double kT) {
        this.kT = new Double3(kT);
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
     * setter for kS
     *
     * @param kS component specular
     * @return the Material itself
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kD
     *
     * @param kD component diffusive
     * @return the Material itself
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for kS
     *
     * @param kS component specular
     * @return the Material itself
     */
    public Material setKS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for kD
     *
     * @param kD component diffusive
     * @return the Material itself
     */
    public Material setKD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the number of rays for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @return This Material object.
     * @throws IllegalArgumentException if numOfRays is less than 1.
     */
    public Material setNumOfRays(int numOfRays) {
        if (numOfRays < 1)
            throw new IllegalArgumentException("Illegal argument in setNumOfRays");
        this.numOfRays = numOfRays;
        return this;
    }

    /**
     * Sets the distance for blur glass rendering.
     *
     * @param blurGlassDistance The distance to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassDistance is less than or equal to 0.
     */
    public Material setBlurGlassDistance(double blurGlassDistance) {
        if (blurGlassDistance <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassDistance");
        this.blurGlassDistance = blurGlassDistance;
        return this;
    }

    /**
     * Sets the radius for blur glass rendering.
     *
     * @param blurGlassRadius The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if blurGlassRadius is less than or equal to
     *                                  0.
     */
    public Material setBlurGlassRadius(double blurGlassRadius) {
        if (blurGlassRadius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlassRadius");
        this.blurGlassRadius = blurGlassRadius;
        return this;
    }

    /**
     * Sets the parameters for blur glass rendering.
     *
     * @param numOfRays The number of rays to set.
     * @param distance  The distance to set.
     * @param radius    The radius to set.
     * @return This Material object.
     * @throws IllegalArgumentException if any of the parameters is invalid.
     */
    public Material setBlurGlass(int numOfRays, double distance, double radius) {
        if (numOfRays < 1 || distance <= 0 || radius <= 0)
            throw new IllegalArgumentException("Illegal argument in setBlurGlass");

        this.numOfRays = numOfRays;
        this.blurGlassDistance = distance;
        this.blurGlassRadius = radius;

        return this;
    }
}