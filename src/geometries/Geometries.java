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
    private final BVHNode root = new BVHNode();

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

    @Override
    public void setBoundingBox() {
        for (Intersectable geo : root.geometries)
            root.boundingBox.expandToInclude(geo.getBoundingBox());
    }

    /**
     * Sets the bounding boxes for all intersectional in the list.
     */
    public void setCBR() {
        for (var geo : root.geometries)
            geo.setBoundingBox();
        setBoundingBox();
    }

    /**
     * Constructs the BVH tree for the geometries.
     */
    public void setBVH() {
        //set CBR for all shapes
        setCBR();
        //set bounding box for root
        buildBVH(root, 0);
    }

    /**
     * max depth
     */
    private final static int maxDepth = 10;

    /**
     * builds bvh tree
     *
     * @param parent the current node
     * @param depth  the current depth
     */
    private void buildBVH(BVHNode parent, int depth) {
        parent.boundingBox.setCenter();

        if (depth >= maxDepth) return;

        parent.childA = new BVHNode();
        parent.childB = new BVHNode();

        for (Intersectable geometry : parent.geometries) {
            boolean inA = geometry.boundingBox.getCenter().getX() < parent.boundingBox.getCenter().getX();
            BVHNode child = inA ? parent.childA : parent.childB;
            child.geometries.add(geometry);
            child.boundingBox.expandToInclude(geometry.boundingBox);
        }

        buildBVH(parent.childA, depth + 1);
        buildBVH(parent.childB, depth + 1);
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
            List<GeoPoint> leftIntersections = findGeoIntersectionsHelper(ray, node.childA, maxDistance);
            List<GeoPoint> rightIntersections = findGeoIntersectionsHelper(ray, node.childB, maxDistance);
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
        private BVHNode childA;
        /**
         * Node's right child.
         */
        private BVHNode childB;
        /**
         * Node's list of geometries.
         */
        private List<Intersectable> geometries;

        /**
         * Constructor empty.
         */
        public BVHNode() {
            this.boundingBox = new BoundingBox();
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
         * @param childA      the left child node
         * @param childB      the right child node
         */
        public BVHNode(BoundingBox boundingBox, BVHNode childA, BVHNode childB) {
            this.boundingBox = boundingBox;
            this.childA = childA;
            this.childB = childB;
        }
    }
}
