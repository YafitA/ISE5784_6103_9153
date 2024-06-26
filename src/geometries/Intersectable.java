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
     * Represent GeoPoint
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
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * find Intersections between ray and geometries collection
     *
     * @param ray intersecting ray
     * @return list of points and shapes
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * A helper method to find Geo Intersections
     *
     * @param ray intersecting ray
     * @return list of points and shapes
     */
    abstract protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
