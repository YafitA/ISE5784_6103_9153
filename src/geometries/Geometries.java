package geometries;

import primitives.BoundingBox;
import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.Comparator;
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
        add(geometries);
    }

    /**
     * Constructor with parameters
     *
     * @param geometries list of geometries
     */
    public Geometries(List<Intersectable> geometries) {
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

    /**
     * Add geometries to the list
     *
     * @param geometries list of geometries
     */
    public void add(List<Intersectable> geometries) {
        this.intersectableList.addAll(geometries);
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
    public void setBoundingBox() {
        if (intersectableList.isEmpty()) {
            boundingBox = null;
            return;
        }

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
        boundingBox = new BoundingBox(xMin,xMax,yMin,yMax,zMin,zMax);
    }

    /** Set the bounding box for the intractable */
    public void setCBR() {
        for (var intractable : intersectableList)
            intractable.setBoundingBox();
    }

    /** Store the geometries as a BVH */
    public void setBVH() {
        setCBR();
        buildBVH();
    }

    /** Build a BVH tree from a list of intersectable geometries */
    protected void buildBVH() {
        if (intersectableList.size() <= 3) {
            // if there are 3 or fewer intersectableList, use them as the bounding box
            return;
        }

        // extract infinite intersectableList into a separate list
        List<Intersectable> infiniteGeometries = new LinkedList<>();
        for (int i = 0; i < intersectableList.size(); i++) {
            var g = intersectableList.get(i);
            if (g.getBoundingBox() == null) {
                infiniteGeometries.add(g);
                intersectableList.remove(i);
                i--;
            }
        }

        // sort intersectableList based on their bounding box centroids along an axis (e.g., x-axis)
        intersectableList.sort(Comparator.comparingDouble(g -> g.getBoundingBox().getCenter().getX()));

        // split the list into two halves
        int mid = intersectableList.size() / 2;
        Geometries leftGeometries = new Geometries(intersectableList.subList(0, mid));
        Geometries rightGeometries = new Geometries(intersectableList.subList(mid, intersectableList.size()));

        // recursively build the BVH for the two halves
        leftGeometries.buildBVH();
        rightGeometries.buildBVH();

        // calculate the bounding box for the two halves
        leftGeometries.setBoundingBox();
        rightGeometries.setBoundingBox();

        // create a combined bounding box
        Geometries combined = new Geometries(leftGeometries);
        combined.add(rightGeometries);
        combined.setBoundingBox();

        // return the list of geometries
        List<Intersectable> result = new LinkedList<>(infiniteGeometries);
        result.add(combined);
        intersectableList.clear();
        intersectableList.addAll(result);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

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
