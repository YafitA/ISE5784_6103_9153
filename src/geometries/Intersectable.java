package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 *  represent intersect
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
                    this.geometry== other.geometry &&
                    this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

        /**
         * Create a GeoPoint object
         * @param geometry  geometry body
         * @param point Point
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
    public abstract List<Point> findIntersections(Ray ray);

    /**
     * find Geo Intersections
     * @param ray
     * @return
     */
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * findGeoIntersectionsHelper
     * @param ray ray
     * @return List<GeoPoint>
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        return null;
    }

}
