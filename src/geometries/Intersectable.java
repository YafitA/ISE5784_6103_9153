package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * represent intersect
 */
public abstract class Intersectable {

    /**
     * Represent GeoPoint a point with a shape
     */
    public static class GeoPoint {
        /**
         * geometry body
         */
        public Geometry geometry;
        /**
         * Point
         */
        public Point point;

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            return (obj instanceof GeoPoint other) &&
                    this.geometry == other.geometry &&
                    this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint {" +
                    "geometry = " + geometry +
                    ", point = " + point +
                    '}';
        }

        /**
         * Create a GeoPoint object
         *
         * @param geometry geometry body
         * @param point    Point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
    }

    /**
     * finds the points that intersect with the ray and the shape
     *
     * @param ray that intersect with the shape
     * @return list of points
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find Intersections between ray and geometries collection
     *
     * @param ray intersecting ray
     * @return list of points and shapes
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find all GeoPoints that intersect with a ray limited by max distance
     *
     * @param ray         intersecting ray
     * @param maxDistance the given maximum distance
     * @return list of points and shapes
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * A helper method to find Geo Intersections
     *
     * @param ray         intersecting ray
     * @param maxDistance the given maximum distance
     * @return list of points and shapes
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

}
