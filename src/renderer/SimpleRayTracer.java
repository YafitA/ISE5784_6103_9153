package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.*;

/**
 * class that extends RayTracerBase to represent Simple Ray Tracer
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * For recursion stop term
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * For recursion stop term
     */
    private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);
    /**
     * The initial value of k
     */
    private static final Double3 INITIAL_K = Double3.ONE;


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
        var intersection = findClosestIntersection(ray);
        return intersection == null ? scene.background : calcColor(intersection, ray);
    }

    /**
     * Calculate the color of the intersection between the ray at the given point on a geometry
     *
     * @param intersection a given point and geometry
     * @param ray          a given ray
     * @return color at point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
//        return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the color at a point
     *
     * @param intersection the point
     * @param ray          the ray that hit the point
     * @param level        the level of the recursion
     * @param k            the k value of the point
     * @return the color at the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Construct the reflected ray
     *
     * @param gp        the point to reflect
     * @param direction the direction of the ray
     * @param n         the normal at the point
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector direction, Vector n) {
        Vector mirror = direction.subtract(n.scale(direction.dotProduct(n) * 2));
        return new Ray(gp.point, mirror, n);
    }

    /**
     * Construct the refracted ray
     *
     * @param gp        the point to refract
     * @param direction the direction of the ray
     * @param normal    the normal at the point
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector direction, Vector normal) {
        return new Ray(gp.point, direction, normal);
    }

    /**
     * Find the closest intersection of a ray with the scene
     *
     * @param ray the ray
     * @return the closest intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Calculate the global effects at a point
     *
     * @param gp    the point
     * @param ray   the ray
     * @param level the level of the recursion
     * @param k     the k value of the point
     * @return the color at the point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector v = ray.getDirection();
        Vector n = gp.geometry.getNormal(gp.point);

        return calcGlobalEffect(material, n, constructReflectedRay(gp, v, n), level, k, material.kR)
                .add(calcGlobalEffect(material, n, constructRefractedRay(gp, v, n), level, k, material.kT));
    }

    /**
     * Calculate the global effects at a point
     *
     * @param ray   the ray to calculate the color for
     * @param level the level of the recursion
     * @param k     the color at the point
     * @param kx    the color of the effect
     * @return the color at the point
     */
    private Color calcGlobalEffect(Material material, Vector n, Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;

        //GeoPoint gp = findClosestIntersection(ray);
        //return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);

        var rays = ray.generateBeam(n, material.blurGlassRadius, material.blurGlassDistance, material.numOfRays);
        return calcAverageColor(rays, level - 1, kkx).scale(kx);
    }

    /**
     * calculate the color between the ray and the point
     *
     * @param gp  point and geometry
     * @param ray a given ray
     * @param k   the attenuation coefficient
     * @return the color at the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();

        Vector n = gp.geometry.getNormal(gp.point); // Normal to point
        Vector v = ray.getDirection(); // Ray's direction
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {
                Double3 ktr = transparency(gp, l, n, lightSource);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculate the transparency of the point
     *
     * @param gp    the point
     * @param l     the light vector
     * @param n     the normal at the point
     * @param light the light source
     * @return the transparency of the point
     */
    private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource light) {
        Double3 ktr = Double3.ONE;
        Ray lightRay = new Ray(gp.point, l.scale(-1), n); // from point to light source
        var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));

        if (intersections == null)
            return ktr;

        for (GeoPoint p : intersections) {
            ktr = ktr.product(p.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }

        return ktr;
    }

    /**
     * Check if the point is shaded
     *
     * @param gp    the point and its body
     * @param l     vector from the source or to the point
     * @param n     the normal to the point
     * @param light the Light source
     * @return true if the point is unshaded and false if its shaded
     */
    @SuppressWarnings("unused")
    @Deprecated(forRemoval = true)
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
        Ray lightRay = new Ray(gp.point, l.scale(-1), n); // from point to light source
        var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        //if no intersections it's unshaded
        if (intersections == null)
            return true;

        //if kT==0 its shaded
        for (GeoPoint p : intersections)
            if (!Double3.ZERO.equals(p.geometry.getMaterial().kT))
                return false;

        return true;
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
        double minusVR = -alignZero(v.dotProduct(r));
        return minusVR > 0 ? material.kS.scale(Math.pow(minusVR, material.nShininess)) : Double3.ZERO;
    }

    /**
     * Calculate the diffusion
     *
     * @param material material of body
     * @param nl       angle
     * @return diffusion
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * Calculates the average color resulting from a list of rays.
     *
     * @param rays the list of rays for which to calculate the average color
     * @param level the current recursion level for color calculation
     * @param kkx a factor used for adjusting the color intensity
     * @return the average color resulting from the list of rays
     */
    Color calcAverageColor(List<Ray> rays, int level, Double3 kkx) {
        Color color = Color.BLACK;

        for (Ray ray : rays) {
            GeoPoint intersection = findClosestIntersection(ray);
            color = color.add(intersection == null ? scene.background : calcColor(intersection, ray, level - 1, kkx));
        }

        return color.reduce(rays.size());
    }
}

