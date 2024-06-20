package renderer;


import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return (intersections == null) ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections));
    }

    /**
     * calc color of given point
     *
     * @param geoPoint to calc its color
     * @return point's color
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());
    }

}
