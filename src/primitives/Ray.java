package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

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
     * fot calculation
     */
    public static final double DELTA = 0.1;

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

    /**
     * creates a ray from point and two vectors
     *
     * @param p0     The starting point of the ray.
     * @param dir    the direction vector of the ray. must be normalized (length = 1)
     * @param normal the normal vector of the ray.
     */
    public Ray(Point p0, Vector dir, Vector normal) {
        double res = dir.dotProduct(normal);
        this.head = p0.add(normal.scale(res >= 0 ? DELTA : -DELTA));
        this.direction = dir;
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
        return isZero(t) ? this.head : head.add(this.direction.scale(t));
    }

    /**
     * Find the closest point on a geometry to ray's head
     *
     * @param geoPoints collection of points on geometries
     * @return point on geometry closest to ray's head
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        //if list is empty - return null
        if (geoPoints == null || geoPoints.isEmpty())
            return null;

        //We initialize the values for comparison
        GeoPoint closestGeoPoint = null;
        double minDistance = Double.POSITIVE_INFINITY;

        //Go over points and find the point w\ the minimum distance to point head
        for (GeoPoint geoPoint : geoPoints) {
            double distance = head.distanceSquared(geoPoint.point);
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

    /**
     * Generates a beam of rays within a given radius and distance from the original ray.
     *
     * @param n         the normal vector to the surface
     * @param radius    the radius within which to generate the rays
     * @param distance  the distance from the starting point of the ray to the target plane
     * @param numOfRays the number of rays to generate in the beam
     * @return a list of rays within the specified radius and distance
     */
    public List<Ray> generateBeamOfRays(Vector n, double radius, double distance, int numOfRays) {
        // The component (glossy surface /diffuse glass) is turned off
        if (numOfRays == 1 || isZero(radius))
            return List.of(this);

        List<Ray> beamOfRays = new LinkedList<Ray>();
        beamOfRays.add(this);

        // the 2 vectors that create the virtual grid for the beam
        // a vector normal to the current direction
        Vector nX = direction.createNormal();
        // is the cross product of the current direction and nX, ensuring orthogonality
        Vector nY = direction.crossProduct(nX);

        //point in the center of the target plane
        Point centerOfCircle = this.getPoint(distance);
        Point randomPoint;
        Vector v12;

        double rand_x, rand_y, delta_radius = radius / (numOfRays - 1);
        // the dot product of the normal vector n and the direction vector of the main ray
        double nv = n.dotProduct(direction);

        for (int i = 1; i < numOfRays; i++) {
            randomPoint = centerOfCircle;
            rand_x = random(-radius, radius);
            rand_y = randomSign() * Math.sqrt(radius * radius - rand_x * rand_x);

            try {
                randomPoint = randomPoint.add(nX.scale(rand_x));
            } catch (Exception ignore) {}

            try {
                randomPoint = randomPoint.add(nY.scale(rand_y));
            } catch (Exception ignore) {}

            v12 = randomPoint.subtract(head).normalize();

            // the dot product of the normal vector n and the direction vector v12 of each generated ray
            double nt = alignZero(n.dotProduct(v12));

            // if they have the same sign, the new ray is added to the list
            if (compareSign(nv,nt)) {
                beamOfRays.add(new Ray(head, v12));
            }
            radius -= delta_radius;
        }

        return beamOfRays;
    }

}