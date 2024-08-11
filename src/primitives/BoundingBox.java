package primitives;

import geometries.Intersectable;

import java.util.List;

import static primitives.Util.isZero;

/**
 * BoundingBox for BVH improvement
 */
public class BoundingBox {

    /**
     * x's minimum value
     */
    private double xMin;
    /**
     * x's maximum value
     */
    private double xMax;
    /**
     * y's minimum value
     */
    private double yMin;
    /**
     * y's maximum value
     */
    private double yMax;
    /**
     * z's minimum value
     */
    private double zMin;
    /**
     * z's maximum value
     */
    private double zMax;
    /**
     * Center of bounding box
     */
    private Point center;

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
        setCenter();
    }

    /**
     * Constructs a new BoundingBox with default values.
     */
    public BoundingBox() {
        this.xMin = Double.NEGATIVE_INFINITY;
        this.xMax = Double.MAX_VALUE;
        this.yMin = Double.NEGATIVE_INFINITY;
        this.yMax = Double.MAX_VALUE;
        this.zMin = Double.NEGATIVE_INFINITY;
        this.zMax = Double.MAX_VALUE;
        setCenter();
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
        setCenter();
    }

    /**
     * Setter for center
     */
    public void setCenter() {
        center = new Point(
                (xMin + xMax) / 2d,
                (yMin + yMax) / 2d,
                (zMin + zMax) / 2d
        );
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
     * getter for center of bounding box
     *
     * @return center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Expand this boundingBox with the received bb
     *
     * @param other boundingBox to include
     */
    public void expandToInclude(BoundingBox other) {
        if (other.xMin < this.xMin) this.xMin = other.xMin;
        if (other.xMax > this.xMax) this.xMax = other.xMax;
        if (other.yMin < this.yMin) this.yMin = other.yMin;
        if (other.yMax > this.yMax) this.yMax = other.yMax;
        if (other.zMin < this.zMin) this.zMin = other.zMin;
        if (other.zMax > this.zMax) this.zMax = other.zMax;
    }


    /**
     * Checks if the given ray intersects with the bounding box.
     *
     * @param r the ray to check for intersection
     * @return true if the ray intersects with the bounding box, false otherwise
     */
    public boolean intersectionBox(Ray r) {

        double tMin, tMax;

        // Calculate the intersection range for the x-axis
        //ray's vec x
        double dirTemp = r.getDirection().getX();
        //ray's head x
        double headTemp = r.getHead().getX();

        if (isZero(dirTemp)) {
            // The ray is parallel to the x-axis
            if (xMin > headTemp || xMax < headTemp)
                return false;

            tMin = Double.NEGATIVE_INFINITY;
            tMax = Double.MAX_VALUE;
        } else {//dirTemp!=0
            // The ray is not parallel to the x-axis
            if (dirTemp > 0) { //positive
                tMin = (xMin - headTemp) / dirTemp;
                tMax = (xMax - headTemp) / dirTemp;
            } else { //negative
                tMax = (xMin - headTemp) / dirTemp;
                tMin = (xMax - headTemp) / dirTemp;
            }
        }

        // Calculate the intersection range for the y-axis
        double tempMin, tempMax;

        dirTemp = r.getDirection().getY();
        headTemp = r.getHead().getY();

        if (isZero(dirTemp)) {
            // The ray is parallel to the y-axis
            if (yMin > headTemp || yMax < headTemp)
                return false;

            tempMin = Double.NEGATIVE_INFINITY;
            tempMax = Double.MAX_VALUE;
        } else {
            // The ray is not parallel to the y-axis
            if (dirTemp > 0) {
                tempMin = (yMin - headTemp) / dirTemp;
                tempMax = (yMax - headTemp) / dirTemp;
            } else {
                tempMax = (yMin - headTemp) / dirTemp;
                tempMin = (yMax - headTemp) / dirTemp;
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
        headTemp = r.getHead().getZ();

        if (isZero(dirTemp)) {
            // The ray is parallel to the z-axis
            if (zMin > headTemp || zMax < headTemp)
                return false;

            return true;
        } else {
            // The ray is not parallel to the z-axis
            if (dirTemp > 0) {
                tempMin = (zMin - headTemp) / dirTemp;
                tempMax = (zMax - headTemp) / dirTemp;
            } else {
                tempMax = (zMin - headTemp) / dirTemp;
                tempMin = (zMax - headTemp) / dirTemp;
            }
        }

        // Check for intersection range overlap
        if ((tMin > tempMax) || (tempMin > tMax))
            return false;

        return true;
    }
}