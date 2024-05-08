/**
 *
 */
package primitives;

/**
 * Represents a vector in three-dimensional space.
 */
public class Vector extends Point {

    /**
     * Constructs a Vector object with the specified coordinates.
     * @param x The x-coordinate of the vector.
     * @param y The y-coordinate of the vector.
     * @param z The z-coordinate of the vector.
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if(Util.isZero(x) && Util.isZero(y) && Util.isZero(z))
            throw new IllegalArgumentException("Can't create Zero Vector!");
    }

    /**
     * Constructs a Vector object with the specified coordinates.
     * @param xyz The coordinates of the vector.
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if(xyz == Double3.ZERO)
            throw new IllegalArgumentException("Can't create Zero Vector!");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override
    public String toString() { return "->" + super.toString(); }

    /**
     * Calculates the squared length of this vector.
     * @return The squared length of this vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculates the length of this vector.
     * @return The length of this vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Adds another vector to this vector and returns the result.
     * @param vec The vector to add.
     * @return The result of adding the given vector to this vector.
     */
    public Vector add(Vector vec) {
        return new Vector(this.xyz.add(vec.xyz));
    }

    /**
     * Scales this vector by a scalar value and returns the result.
     * @param num The scalar value to scale by.
     * @return The result of scaling this vector by the given scalar value.
     */
    public Vector scale(double num) {
        return new Vector(this.xyz.scale(num));
    }

    /**
     * Calculates the dot product of this vector with another vector.
     * @param vec The vector to calculate the dot product with.
     * @return The dot product of this vector with the given vector.
     */
    public double dotProduct(Vector vec) {
        return this.xyz.d1 * vec.xyz.d1 +
               this.xyz.d2 * vec.xyz.d2 +
               this.xyz.d3 * vec.xyz.d3;
    }

    /**
     * Calculates the cross product of this vector with another vector.
     * @param vec The vector to calculate the cross product with.
     * @return The cross product of this vector with the given vector.
     */
    public Vector crossProduct(Vector vec) {
        double x = this.xyz.d2 * vec.xyz.d3 - this.xyz.d3 * vec.xyz.d2;
        double y = this.xyz.d3 * vec.xyz.d1 - this.xyz.d1 * vec.xyz.d3;
        double z = this.xyz.d1 * vec.xyz.d2 - this.xyz.d2 * vec.xyz.d1;
        return new Vector(x, y, z);
    }

    /**
     * Normalizes this vector.
     * @return The normalized version of this vector.
     */
    public Vector normalize() {
        double length = this.length();
        return this.scale(1.0/length);
    }

}