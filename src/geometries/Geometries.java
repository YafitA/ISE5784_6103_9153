package geometries;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent a geometries
 */
public class Geometries extends Intersectable {

    /**
     * list of intersection
     */
    private final List<Intersectable> intersectableList = new LinkedList<>();

    /**
     * Constructs Geometries (empty)
     */
    public Geometries() {}

    /**
     * Constructs Geometries with params
     *
     * @param geometries group of shapes
     */
    public Geometries(Intersectable... geometries) {
        if (geometries != null)
            add(geometries);
    }

    /**
     * Add shapes to the group
     *
     * @param geometries group of shapes
     */
    public void add(Intersectable... geometries) {

        intersectableList.addAll(Arrays.asList(geometries));
        //if isBoundingBoxOn is off return
        if(!BoundingBox.isBoundingBoxOn)    return;
        // build a bounding box
        // search in all new geometries for the min and max X,Y,Z (if they bigger then the current x,y,z bounding box)
        double xMax = Double.NEGATIVE_INFINITY;
        double xMin = Double.MAX_VALUE;

        double yMax = Double.NEGATIVE_INFINITY;
        double yMin = Double.MAX_VALUE;

        double zMax = Double.NEGATIVE_INFINITY;
        double zMin = Double.MAX_VALUE;

        for (Intersectable g : intersectableList) {

            // check x
            if (g.boundingBox.getxMin() < xMin)
                xMin = g.boundingBox.getxMin();

            if (g.boundingBox.getxMax() > xMax)
                xMax = g.boundingBox.getxMax();

            // check y
            if (g.boundingBox.getyMin() < yMin)
                yMin = g.boundingBox.getyMin();

            if (g.boundingBox.getyMax() > yMax)
                yMax = g.boundingBox.getyMax();

            // check z
            if (g.boundingBox.getzMin() < zMin)
                zMin = g.boundingBox.getzMin();

            if (g.boundingBox.getzMax() > zMax)
                zMax = g.boundingBox.getzMax();
        }
        boundingBox = new BoundingBox(new Point(xMin, yMin, zMin), new Point(xMax, yMax, zMax));
    }

    /**
     * set bounding box
     *
     * @param p1 first point
     * @param p2 second point
     * @return new object
     */
    public Geometries setBoundingBox(Point p1, Point p2) {
        this.boundingBox = new BoundingBox(p1, p2);
        return this;
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // there is bounding box and no intersection
        if (this.boundingBox != null && !this.boundingBox.intersectionBox(ray))
            return null;

        //initialize list with null
        List<GeoPoint> intersectionsPoints = null;

        //go over the list of shapes
        for (Intersectable item : intersectableList) {
            List<GeoPoint> itemIntersections = item.findGeoIntersections(ray, maxDistance);
            // check if findIntersectionsHelper returned points and
            // if intersectionsPoints is empty if so initialize an empty list
            if (itemIntersections != null) {
                if (intersectionsPoints == null)
                    intersectionsPoints = new LinkedList<>(itemIntersections);
                else
                    intersectionsPoints.addAll(itemIntersections);
            }
        }

        return intersectionsPoints;
    }
}
