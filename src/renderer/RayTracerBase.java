package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class to represent trace ray
 */
public abstract class RayTracerBase {
    /**
     * Scene parameter
     */
    protected final Scene scene;

    /**
     * Ray Tracer Base constructor accepting scene parameter
     *
     * @param scene to put in scene parameter
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * traces the given ray
     *
     * @param ray to trace
     * @return color by given ray
     */
    public abstract Color traceRay(Ray ray);
}
