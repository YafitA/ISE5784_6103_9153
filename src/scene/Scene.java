package scene;


import geometries.Geometries;
import lighting.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent Scene
 */
public class Scene {

    /**
     * Scene's name
     */
    public final String name;
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
     * List of LightSources
     */
    public List<LightSource> lights = new LinkedList<>();


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
     * @return the Scene itself
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * set ambient light
     *
     * @param ambientLight Ambient light
     * @return the Scene itself
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }


    /**
     * set Geometries (3D model)
     *
     * @param geometries 3D model
     * @return the Scene itself
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * set List of LightSources
     *
     * @param lights List of LightSources
     * @return the Scene itself
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
