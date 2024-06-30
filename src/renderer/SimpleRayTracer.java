package renderer;


import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;


import java.util.List;

import static primitives.Util.alignZero;

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
        return (intersections == null) ? scene.background : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * Calculate the color of the intersection between the ray at the given point on a geometry
     *
     * @param intersection a given point and geometry
     * @param ray          a given ray
     * @return color at point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * calculate the color between the ray and the point
     *
     * @param gp  point and geometry
     * @param ray a given ray
     * @return color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();

        Vector n = gp.geometry.getNormal(gp.point); // Normal to point
        Vector v = ray.getDirection(); // Ray's direction
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * calculate Specular
     *
     * @param material material of body
     * @param n        normal between point and geometry
     * @param l        Vector between lightSource and point
     * @param nl       angle
     * @param v        ray's direction
     * @return Specular
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(nl * 2));
        double num = -v.dotProduct(r);
        return num > 0 ? material.kS.scale(Math.pow(num, material.nShininess)) : Double3.ZERO;
    }

    /**
     * Calculate the diffusion
     *
     * @param material material of body
     * @param nl       angle
     * @return diffusion
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }


}
