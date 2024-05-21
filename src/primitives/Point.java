/**
 *
 */
package primitives;

/**
 * A fundamental object in 3D geometry - a point with 3 coordinates
 */
public class Point {

    /**
     * The coordinates of the point
     */
    protected final Double3 xyz;

    /**
     * The point at the origin (0, 0, 0)
     */
    public static Point ZERO = new Point(0, 0, 0);

    /**
     * Constructs a Point object with the specified coordinates
     *
     * @param xyz The coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a Point object with the specified coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "" + this.xyz;
    }

    /**
     * Adds the given vector to this point and returns the resulting point.
     *
     * @param vec The vector to add.
     * @return The resulting point after adding the vector.
     */
    public Point add(Vector vec) {
        return new Point(this.xyz.add(vec.xyz));
    }

    /**
     * Subtracts the given point from this point and returns the resulting vector.
     *
     * @param p The point to subtract.
     * @return The resulting vector after subtracting the given point.
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * Calculates the Euclidean distance between this point and the given point.
     *
     * @param p The point to calculate the distance to.
     * @return The Euclidean distance between this point and the given point.
     */
    public double distance(Point p) {
        //root((p1-p2)*(p1-p2))
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * Calculates the squared Euclidean distance between this point and the given point.
     *
     * @param p The point to calculate the distance to.
     * @return The squared Euclidean distance between this point and the given point.
     */
    public double distanceSquared(Point p) {
        double d1Dis = this.xyz.d1 - p.xyz.d1;
        double d2Dis = this.xyz.d2 - p.xyz.d2;
        double d3Dis = this.xyz.d3 - p.xyz.d3;
        return d1Dis * d1Dis + d2Dis * d2Dis + d3Dis * d3Dis;
    }
}