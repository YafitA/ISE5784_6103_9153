package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * class that extends RayTracerBase to represent Simple Ray Tracer
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Ray Tracer Base constructor accepting scene parameter
     *
     * @param scene to put in scene parameter
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        return intersections == null ? scene.background
                : calcColor(ray.findClosestPoint(intersections));
    }

    /**
     * calc color of given point
     *
     * @param point to calc its color
     * @return point's color
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

}
