package primitives;

public class Point {
    protected final Double3 xyz;
    public static Point ZERO = new Point(0, 0, 0);

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Point(int x, int y, int z) {
        this.xyz = new Double3(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Point other) && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return this.xyz.toString();
    }

    public Point add(Vector vec) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public Vector subtract(Point p) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public double distance(Point p) {
        // TODO
        throw new UnsupportedOperationException();
    }

    public double distanceSquared(Point p) {
        // TODO
        throw new UnsupportedOperationException();
    }

}