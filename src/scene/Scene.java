package scene;


import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Class to represent Scene
 */
public class Scene {

    /**
     * Scene's name
     */
    public String name;
    /**
     * background's color
     */
    public Color background = Color.BLACK;
    /**
     * Ambient light
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * 3D model
     */
    public Geometries geometries = new Geometries();

    /**
     * Constructor with name
     *
     * @param name Scene's name
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * set background's color
     *
     * @param background background's color
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set ambient light
     *
     * @param ambientLight Ambient light
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }


    /**
     * set Geometries (3D model)
     *
     * @param geometries 3D model
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
