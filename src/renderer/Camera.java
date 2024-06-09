package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera in a 3D space with a position and orientation vectors.
 * Implements the Builder design pattern for creating camera objects and the Cloneable interface.
 */
public class Camera implements Cloneable {
    /**
     * The Builder class is used to construct instances of Camera
     */
    public static class Builder {
        /*** Camera in builder*/
        final private Camera camera;

        /**
         * default constructor
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * parameters constructor
         */
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * sets camera's location
         *
         * @param p new location for camera
         * @return this
         */
        public Builder setLocation(Point p) {
            camera.location = p;
            return this;
        }

        /**
         * set Direction for camera
         * make sure the two vectors are align
         *
         * @param to new to Vector for camera
         * @param up new up Vector for camera
         * @return this
         */
        public Builder setDirection(Vector to, Vector up) {
            //check if vectors are aligned
            if (!isZero(to.dotProduct(up))) throw new IllegalArgumentException("Vectors must be align");

            camera.to = to.normalize();
            camera.up = up.normalize();

            return this;
        }

        /**
         * set Vp Size
         *
         * @param width  for Vp
         * @param height for Vp
         * @return this
         */
        public Builder setVpSize(double width, double height) {
            if (alignZero(width) <= 0 || alignZero(height) <= 0)
                throw new IllegalArgumentException("width and/or height must be positive!");
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * set Vp Distance between camera and Vp
         *
         * @param distance between camera and Vp
         * @return this
         */
        public Builder setVpDistance(double distance) {
            if (alignZero(distance) <= 0) throw new IllegalArgumentException("distance must be positive!");
            camera.distance = distance;
            return this;
        }

        /**
         * checks all camera parameters are valid
         *
         * @return camera
         */
        public Camera build() {

            boolean isMissingParams = false;
            String msg = "";

            if (isZero(this.camera.width)) {
                msg += "Missing parameter: width.\n";
                isMissingParams = true;
            }
            if (isZero(this.camera.height)) {
                msg += "Missing parameter: height.\n";
                isMissingParams = true;
            }
            if (isZero(this.camera.distance)) {
                msg += "Missing parameter: distance.\n";
                isMissingParams = true;
            }
            if (this.camera.to == null) {
                msg += "Missing parameter: Vector to.\n";
                isMissingParams = true;
            }
            if (this.camera.up == null) {
                msg += "Missing parameter: Vector up.\n";
                isMissingParams = true;
            }
            if (isMissingParams)
                throw new MissingResourceException("Missing render value(s)", "Camera", msg);

            if (!isZero(camera.to.dotProduct(camera.up))) throw new IllegalArgumentException("Vectors must be align");
            if (alignZero(camera.distance) <= 0) throw new IllegalArgumentException("distance must be positive!");
            if (alignZero(camera.width) <= 0 || alignZero(camera.height) <= 0)
                throw new IllegalArgumentException("width and/or height must be positive!");

            //calc missing information
            camera.right = camera.to.crossProduct(camera.up).normalize();

            try {
                return (Camera) camera.clone();
            } catch (java.lang.CloneNotSupportedException e) {
                throw new RuntimeException();
            }

        }
    }

    /**
     * Point location for camera
     */
    private Point location;
    /**
     * right direction vector of the camera
     */
    private Vector right;
    /**
     * up direction vector of the camera
     */
    private Vector up;
    /**
     * toward direction vector of the camera
     */
    private Vector to;

    /**
     * View Plane (size: w x h) height
     */
    private double height = 0.0;
    /**
     * View Plane (size: w x h) width
     */
    private double width = 0.0;
    /**
     * the distance from the camera to the view plane
     */
    private double distance = 0.0;

    /**
     * Private constructor to create an item of type camera
     */
    private Camera() {
    }

    /**
     * Gets the position of the camera.
     *
     * @return Point location for camera.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return the right direction vector of the camera.
     */
    public Vector getRight() {
        return right;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return the up direction vector of the camera.
     */
    public Vector getUp() {
        return up;
    }

    /**
     * Gets the forward direction vector of the camera.
     *
     * @return the forward direction vector of the camera.
     */
    public Vector getTo() {
        return to;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the builder
     *
     * @return Builder
     */
    static public Builder getBuilder() {
        return new Builder();
    }

    /**
     * Constructs a ray through a specific pixel in the view plane.
     *
     * @param nX Number of pixels in the view plane in the x-direction (width).
     * @param nY Number of pixels in the view plane in the y-direction (height).
     * @param j  The pixel column index (x-coordinate) for which to construct the ray.
     * @param i  The pixel row index (y-coordinate) for which to construct the ray.
     * @return The constructed ray through the specified pixel.
     * .
     * This method considers the view plane's size and distance from the camera. The ray is constructed
     * through the center of the specified pixel in the view plane, taking into account the camera's
     * orientation vectors (right, up, and toward).
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        //calc center of vp
        Point pIJ = location.add(to.scale(distance));

        double yI = -(i - (double) (nY - 1) / 2) * (height / nY);
        double xJ = (j - (double) (nX - 1) / 2) * (width / nX);

        //move point of pixel on vp
        if (!isZero(xJ))
            pIJ = pIJ.add(right.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(up.scale(yI));

        return new Ray(location, pIJ.subtract(location));
    }


}
