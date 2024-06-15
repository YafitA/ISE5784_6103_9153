package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent a geometries
 */
public class Geometries implements Intersectable {

    /**
     * list of intersection
     */
    private final List<Intersectable> intersectableList = new LinkedList<>();

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
        add(geometries);
    }

    /**
     * Add shapes to the group
     *
     * @param geometries group of shapes
     */
    public void add(Intersectable... geometries) {
        intersectableList.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        //initialize list with null
        List<Point> intersectionsPoints = null;

        //go over the list of shapes
        for (Intersectable item : intersectableList) {

            List<Point> itemIntersections = item.findIntersections(ray);

            //check if findIntersections returned points and
            // if intersectionsPoints is empty if so initialize an empty list
            if (itemIntersections != null) {
                if (intersectionsPoints == null) {
                    intersectionsPoints = new LinkedList<>();
                }
                intersectionsPoints.addAll(itemIntersections);
            }
        }

        return intersectionsPoints;
    }
}
