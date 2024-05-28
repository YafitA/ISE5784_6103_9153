package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private final List<Intersectable> L = new LinkedList<>();

    /**
     * Constructs Geometries (empty)
     */
    public Geometries() {
    }

    /**
     * Constructs Geometries with params
     *
     * @param geometries group of shapes
     */
    public Geometries(Intersectable... geometries) {
    }

    /**
     * Add shapes to the group
     *
     * @param geometries group of shapes
     */
    public void add(Intersectable... geometries) {

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
