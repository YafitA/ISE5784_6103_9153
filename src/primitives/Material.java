package primitives;

/**
 * Class Material
 */
public class Material {

    /**
     * The attenuation coefficients of the components
     * diffusive and specular of the model.
     */
    public Double3 Kd = Double3.ZERO, kS = Double3.ZERO;

    /**
     * The level of concentration of the lightning (the component
     * specular).
     */
    public int nShininess = 0;

    /**
     * setter for nShininess
     *
     * @param nShininess the component specular
     * @return this
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter for kS
     *
     * @param kS component specular
     * @return this
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kD
     *
     * @param Kd component diffusive
     * @return this
     */
    public Material setKd(Double3 Kd) {
        this.Kd = Kd;
        return this;
    }

    /**
     * setter for kS
     *
     * @param kS component specular
     * @return this
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for kD
     *
     * @param Kd component diffusive
     * @return this
     */
    public Material setKd(double Kd) {
        this.Kd = new Double3(Kd);
        return this;
    }

}
