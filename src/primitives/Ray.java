package primitives;

/**
 * Represents a ray in three-dimensional space.
 */
public class Ray {

    private final Point head;

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
        this.direction = direction.normalize();;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    @Override
    public String toString() {
        return this.head + " " + this.direction;
    }

}