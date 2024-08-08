package geometries;

import primitives.*;

import java.util.*;

/**
 * Class to represent a collection of geometries using a Bounding Volume Hierarchy (BVH).
 */
public class Geometries extends Intersectable {

    /**
     * Root node of the BVH tree.
     */
    private BVHNode root = new BVHNode();

    /**
     * Constructs an empty Geometries object.
     */
    public Geometries() {
    }

    /**
     * Constructs a Geometries object with a given list of shapes.
     *
     * @param geometries group of shapes
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds shapes to the group.
     *
     * @param geometries group of shapes
     */
    public void add(Intersectable... geometries) {
        root.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * boundingBox For Shapes
     *
     * @param geometries list of geos
     * @return boundingbox for list
     */
    private BoundingBox boundingBoxForShapes(List<Intersectable> geometries) {
        BoundingBox boundingBox = new BoundingBox();
        for (Intersectable geo : geometries)
            boundingBox.expandToInclude(geo.boundingBox);
        return boundingBox;
    }

    @Override
    public void setBoundingBox() {
    }

    /**
     * Sets the bounding boxes for all intersectional in the list.
     */
    public void setCBR() {
        for (Intersectable geo : root.geometries)
            geo.setBoundingBox();
    }

    /**
     * Constructs the BVH tree for the geometries.
     */
    public void setBVH() {
        //set CBR for all shapes
        setCBR();
        //sort list along x-axis
        root.geometries.sort(Comparator.comparingDouble(g -> g.boundingBox.getCenter().getX()));
        //set root to be organized
        root = buildBVH(root.geometries, 0);
    }

    /**
     * Recursively builds the BVH tree from a list of intersectable geometries.
     *
     * @param geometries the list of geometries
     * @param depth      the current depth of the BVH tree
     * @return the root node of the BVH tree
     */
    protected BVHNode buildBVH(List<Intersectable> geometries, int depth) {
        BoundingBox boundingBox = boundingBoxForShapes(geometries);

        int maxDepth = 3;// Adjust as needed
        int minObjectsPerLeaf = 3;  // Adjust as needed

        if (depth >= maxDepth || geometries.size() <= minObjectsPerLeaf) {
            return new BVHNode(boundingBox, geometries);
        }

        double xRange = boundingBox.getxMax() - boundingBox.getxMin();
        double yRange = boundingBox.getyMax() - boundingBox.getyMin();
        double zRange = boundingBox.getzMax() - boundingBox.getzMin();

        Comparator<Intersectable> comparator;

        if (xRange > yRange && xRange > zRange) {
            comparator = Comparator.comparingDouble(g -> g.boundingBox.getCenter().getX());
        } else if (yRange > zRange) {
            comparator = Comparator.comparingDouble(g -> g.boundingBox.getCenter().getY());
        } else {
            comparator = Comparator.comparingDouble(g -> g.boundingBox.getCenter().getZ());
        }

        geometries.sort(comparator);
        int mid = geometries.size() / 2;

        BVHNode leftChild = buildBVH(geometries.subList(0, mid), depth + 1);
        BVHNode rightChild = buildBVH(geometries.subList(mid, geometries.size()), depth + 1);

        return new BVHNode(boundingBox, leftChild, rightChild);
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, root, maxDistance);
    }

    /**
     * Recursively finds intersections between a ray and geometries in the BVH tree.
     *
     * @param ray         the ray
     * @param node        the current BVH node
     * @param maxDistance the maximum distance to check for intersections
     * @return a list of intersection points
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, BVHNode node, double maxDistance) {

        if (node.boundingBox != null && !node.boundingBox.intersectionBox(ray))
            return null;

        if (node.geometries != null) {//leaf
            List<GeoPoint> intersectionsPoints = new LinkedList<>();
            for (Intersectable geom : node.geometries) {
                List<GeoPoint> itemIntersections = geom.findGeoIntersections(ray, maxDistance);
                if (itemIntersections != null) {
                    intersectionsPoints.addAll(itemIntersections);
                }
            }
            return intersectionsPoints.isEmpty() ? null : intersectionsPoints;

        } else {//not leaf
            List<GeoPoint> leftIntersections = findGeoIntersectionsHelper(ray, node.leftChild, maxDistance);
            List<GeoPoint> rightIntersections = findGeoIntersectionsHelper(ray, node.rightChild, maxDistance);
            if (leftIntersections == null) return rightIntersections;
            if (rightIntersections == null) return leftIntersections;
            leftIntersections.addAll(rightIntersections);
            return leftIntersections;
        }
    }

    /**
     * Inner class representing a node in the BVH tree.
     */
    public static class BVHNode {
        /**
         * Bounding box for children.
         */
        private BoundingBox boundingBox;
        /**
         * Node's left child.
         */
        private BVHNode leftChild;
        /**
         * Node's right child.
         */
        private BVHNode rightChild;
        /**
         * Node's list of geometries.
         */
        private List<Intersectable> geometries;

        /**
         * Constructor empty.
         */
        public BVHNode() {
            this.geometries = new ArrayList<>();
        }

        /**
         * Constructor for leaf nodes.
         *
         * @param boundingBox the bounding box
         * @param geometries  the list of geometries
         */
        public BVHNode(BoundingBox boundingBox, List<Intersectable> geometries) {
            this.boundingBox = boundingBox;
            this.geometries = geometries;
        }

        /**
         * Constructor for internal nodes.
         *
         * @param boundingBox the bounding box
         * @param leftChild   the left child node
         * @param rightChild  the right child node
         */
        public BVHNode(BoundingBox boundingBox, BVHNode leftChild, BVHNode rightChild) {
            this.boundingBox = boundingBox;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}
