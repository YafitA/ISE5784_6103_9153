package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Represents a ray in three-dimensional space.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    private final Point head;
    /**
     * The direction vector of the ray.
     */
    private final Vector direction;

    /**
     * Constructs a Ray object with the specified starting point and direction.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    /**
     * get ray's direction
     *
     * @return direction
     */
    public Vector getDirection() {
        return direction;
    }

    /**
     * get ray's head
     *
     * @return head
     */
    public Point getHead() {
        return head;
    }

    @Override
    public String toString() {
        return head + " " + this.direction;
    }

    /**
     * get a point on the ray multiply by t
     *
     * @param t scalar to find point
     * @return point p=p0+tv
     */
    public Point getPoint(double t) {
        //p= p0 or p0 + tv
        return Util.isZero(t) ? this.head : head.add(this.direction.scale(t));
    }

    /**
     * Find the closest point on a geometry to ray's head
     *
     * @param geoPoints collection of points on geometries
     * @return point on geometry closest to ray's head
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        //if list is empty - return null
        if (geoPoints.isEmpty()) {
            return null;
        }

        //We initialize the values for comparison
        GeoPoint closestGeoPoint = null;
        double minDistance = Double.MAX_VALUE;

        //Go over points and find the point w\ the minimum distance to point head
        for (GeoPoint geoPoint : geoPoints) {
            double distance = head.distance(geoPoint.point);
            if (distance < minDistance) {
                minDistance = distance;
                closestGeoPoint = geoPoint;
            }
        }

        return closestGeoPoint;
    }

    /**
     * Find the closest point to ray's head
     *
     * @param points collection of points
     * @return point closest to ray's head
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


}