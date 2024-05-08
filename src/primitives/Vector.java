package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) { super(x,y,z);}
    public Vector(Double3 xyz) {
        super(xyz);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public double lengthSquared() {
        // TODO
        throw new UnsupportedOperationException();

    }

    public double length() {
        // TODO
        throw new UnsupportedOperationException();

    }

    public Vector add(Vector vec) {
        // TODO
        throw new UnsupportedOperationException();

    }

    public Vector scale(double num) {
        // TODO
        throw new UnsupportedOperationException();

    }

    public Vector dotProduct(Vector vec) {
        // TODO
        throw new UnsupportedOperationException();

    }

    public Vector crossProduct(Vector vec) {
        // TODO
        throw new UnsupportedOperationException();

    }

    public Vector normalize() {
        // TODO
        throw new UnsupportedOperationException();

    }

}