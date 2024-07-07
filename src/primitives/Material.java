package primitives;

/**
 * Class Material
 */
public class Material {


    /**
     * The attenuation coefficients of the components
     * diffusive and specular of the model.
     */
    public Double3 kD = Double3.ZERO, kS = Double3.ZERO;

    /**
     * The level of concentration of the lightning (the component
     * specular).
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


    /**
     * setter in a builder design for kR
     * @param kR attenuation coefficient of reflection
     * @return the new material
     */
    public Material setKR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter in a builder design for kT
     * @param kT Attenuation coefficient of transparency
     * @return the new material
     */
    public Material setKT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter in a builder design for kR
     * @param kR attenuation coefficient of reflection
     * @return the new material
     */
    public Material setKR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setter in a builder design for kT
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
}