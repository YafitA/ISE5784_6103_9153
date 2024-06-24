package primitives;

/**
 * Class Material
 */
public class Material {

    /**
     * The attenuation coefficients of the components
     * diffusive and specular of the model.
     */
    public Double3 kD=Double3.ZERO,kS=Double3.ZERO;

    /**
     * The level of concentration of the lightning (the component
     * specular).
     */
    public int nShininess=0;

    /**
     * setter for nShininess
     * @param nShininess    the component specular
     * @return this
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * setter for kS
     * @param kS    component specular
     * @return this
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kD
     * @param kD component diffusive
     * @return this
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter for kS
     * @param kS    component specular
     * @return this
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for kD
     * @param kD component diffusive
     * @return this
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }


}
