package primitives;

import static primitives.Util.isZero;

/**
 * BoundingBox for BVH improvement
 */
public class BoundingBox {

    /**
     * parameter to enable/disable BoundingBox
     */
    private static boolean isBoundingBoxOn = true;

    /**
     * x's minimum value
     */
    private final double xMin;
    /**
     * x's maximum value
     */
    private final double xMax;
    /**
     * y's minimum value
     */
    private final double yMin;
    /**
     * y's maximum value
     */
    private final double yMax;
    /**
     * z's minimum value
     */
    private final double zMin;
    /**
     * z's maximum value
     */
    private final double zMax;

    /**
     * create an object of BoundingBox
     *
     * @param xMin x's minimum value
     * @param xMax x's maximum value
     * @param yMin y's minimum value
     * @param yMax y's maximum value
     * @param zMin z's minimum value
     * @param zMax z's maximum value
     */
    public BoundingBox(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    /**
     * Constructs a new BoundingBox with default values.
     */
    public BoundingBox() {
        this.xMin = Double.MIN_VALUE;
        this.xMax = Double.MAX_VALUE;
        this.yMin = Double.MIN_VALUE;
        this.yMax = Double.MAX_VALUE;
        this.zMin = Double.MIN_VALUE;
        this.zMax = Double.MAX_VALUE;
    }


    /**
     * Constructs a new BoundingBox from two points.
     *
     * @param p1 the first point
     * @param p2 the second point
     */
    public BoundingBox(Point p1, Point p2) {
        if (p1.getX() < p2.getX()) {
            this.xMin = p1.getX();
            this.xMax = p2.getX();
        } else {
            this.xMin = p2.getX();
            this.xMax = p1.getX();
        }

        if (p1.getY() < p2.getY()) {
            this.yMin = p1.getY();
            this.yMax = p2.getY();
        } else {
            this.yMin = p2.getY();
            this.yMax = p1.getY();
        }

        if (p1.getZ() < p2.getZ()) {
            this.zMin = p1.getZ();
            this.zMax = p2.getZ();
        } else {
            this.zMin = p2.getZ();
            this.zMax = p1.getZ();
        }
    }

    /**
     * Returns the minimum x value of the bounding box.
     *
     * @return the minimum x value
     */
    public double getxMin() {
        return xMin;
    }

    /**
     * Returns the maximum x value of the bounding box.
     *
     * @return the maximum x value
     */
    public double getxMax() {
        return xMax;
    }

    /**
     * Returns the minimum y value of the bounding box.
     *
     * @return the minimum y value
     */
    public double getyMin() {
        return yMin;
    }

    /**
     * Returns the maximum y value of the bounding box.
     *
     * @return the maximum y value
     */
    public double getyMax() {
        return yMax;
    }

    /**
     * Returns the minimum z value of the bounding box.
     *
     * @return the minimum z value
     */
    public double getzMin() {
        return zMin;
    }

    /**
     * Returns the maximum z value of the bounding box.
     *
     * @return the maximum z value
     */
    public double getzMax() {
        return zMax;
    }

    /**
     * Checks if the given ray intersects with the bounding box.
     *
     * @param r the ray to check for intersection
     * @return true if the ray intersects with the bounding box, false otherwise
     */
    public boolean intersectionBox(Ray r) {
        //component is off
        if (!isBoundingBoxOn) return true;

        double tMin, tMax;

        // Calculate the intersection range for the x-axis
        double dirTemp = r.getDirection().getX();
        double pTemp = r.getHead().getX();

        if (isZero(dirTemp)) {
            // The ray is parallel to the x-axis
            if (xMin > pTemp || xMax < pTemp)
                return false;

            tMin = Double.NEGATIVE_INFINITY;
            tMax = Double.MAX_VALUE;
        } else {
            // The ray is not parallel to the x-axis
            if (dirTemp > 0) { //positive
                tMin = (xMin - pTemp) / dirTemp;
                tMax = (xMax - pTemp) / dirTemp;
            } else { //negative
                tMax = (xMin - pTemp) / dirTemp;
                tMin = (xMax - pTemp) / dirTemp;
            }
        }

        // Calculate the intersection range for the y-axis
        double tempMin, tempMax;

        dirTemp = r.getDirection().getY();
        pTemp = r.getHead().getY();

        if (isZero(dirTemp)) {
            // The ray is parallel to the y-axis
            if (yMin > pTemp || yMax < pTemp)
                return false;

            tempMin = Double.NEGATIVE_INFINITY;
            tempMax = Double.MAX_VALUE;
        } else {
            // The ray is not parallel to the y-axis
            if (dirTemp > 0) {
                tempMin = (yMin - pTemp) / dirTemp;
                tempMax = (yMax - pTemp) / dirTemp;
            } else {
                tempMax = (yMin - pTemp) / dirTemp;
                tempMin = (yMax - pTemp) / dirTemp;
            }
        }

        // Check for intersection range overlap
        if ((tMin > tempMax) || (tempMin > tMax))
            return false;

        // Update the minimum and maximum intersection ranges
        if (tempMin > tMin)
            tMin = tempMin;

        if (tempMax < tMax)
            tMax = tempMax;

        // Calculate the intersection range for the z-axis
        dirTemp = r.getDirection().getZ();
        pTemp = r.getHead().getZ();

        if (isZero(dirTemp)) {
            // The ray is parallel to the z-axis
            if (zMin > pTemp || zMax < pTemp)
                return false;

            return true;
        } else {
            // The ray is not parallel to the z-axis
            if (dirTemp > 0) {
                tempMin = (zMin - pTemp) / dirTemp;
                tempMax = (zMax - pTemp) / dirTemp;
            } else {
                tempMax = (zMin - pTemp) / dirTemp;
                tempMin = (zMax - pTemp) / dirTemp;
            }
        }

        // Check for intersection range overlap
        if ((tMin > tempMax) || (tempMin > tMax))
            return false;

        // Update the minimum and maximum intersection ranges
        if (tempMin > tMin)
            tMin = tempMin;

        if (tempMax < tMax)
            tMax = tempMax;

        return true;
    }

    /**
     * Getter for IsBoundingBoxOn
     *
     * @return Is BoundingBox On
     */
    public static boolean GetIsBoundingBoxOn() {
        return isBoundingBoxOn;
    }

    /**
     * setter IsBoundingBoxOn
     *
     * @param isBoundingBoxOn turn the affect on/off
     */
    public static void setIsBoundingBoxOn(boolean isBoundingBoxOn) {
        BoundingBox.isBoundingBoxOn = isBoundingBoxOn;
    }

}