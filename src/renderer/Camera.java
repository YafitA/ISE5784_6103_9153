package renderer;

import primitives.*;

/**
 * Represents a camera in a 3D space with a position and orientation vectors.
 * Implements the Builder design pattern for creating camera objects and the Cloneable interface.
 */
public class Camera implements Cloneable{

    private Point position;
    private Vector right;
    private Vector up;
    private Vector to;
    private double viewPlaneHeight=0.0;
    private double viewPlaneWidth=0.0;
    private double viewPlaneDistance=0.0;

    /**
     *  Private constructor to create an item of type camera
     */
    private Camera() {
    }

    /**
     * Gets the position of the camera.
     *
     * @return the position of the camera.
     */
    public Point getPosition() {
        return position;
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
    public double getViewPlaneHeight() {
        return viewPlaneHeight;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    public double getViewPlaneWidth() {
        return viewPlaneWidth;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    public double getViewPlaneDistance() {
        return viewPlaneDistance;
    }

    /**
     * Gets the builder
     * @return Builder
     */
    static public Builder getBuilder(){
        return Bulder;
    }

    /**
     * Constructs a ray through a specific pixel in the view plane.
     *
     * @param nX Number of pixels in the view plane in the x-direction (width).
     * @param nY Number of pixels in the view plane in the y-direction (height).
     * @param j  The pixel column index (x-coordinate) for which to construct the ray.
     * @param i  The pixel row index (y-coordinate) for which to construct the ray.
     * @return   The constructed ray through the specified pixel.
     *.
     * This method considers the view plane's size and distance from the camera. The ray is constructed
     * through the center of the specified pixel in the view plane, taking into account the camera's
     * orientation vectors (right, up, and toward).
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    @Override
    public Camera clone() {
        try {
            Camera clone = (Camera) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
