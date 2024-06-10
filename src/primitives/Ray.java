package primitives;

/**
 * Represents a ray in three-dimensional space.
 */
public class Ray {
    /**The starting point of the ray.*/
    private final Point head;
    /**The direction vector of the ray.*/
    private final Vector direction;

    /**
     * Constructs a Ray object with the specified starting point and direction.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        //Vector direction must be normalized
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
}