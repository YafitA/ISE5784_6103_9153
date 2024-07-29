package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a camera in a 3D space with a position and orientation vectors.
 * Implements the Builder design pattern for creating camera objects and the Cloneable interface.
 */
public class Camera implements Cloneable {

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
     * the image writer
     */
    private ImageWriter imageWriter;
    /**
     * the ray tracer base
     */
    private RayTracerBase rayTracer;

    /**
     * Pixel manager for supporting
     */
    private PixelManager pixelManager;

    /**
     * Number of threads to use for rendering
     */
    private int threadsCount = 10;

    /**
     * Print interval for debug print of progress percentage in Console window/tab
     */
    private double printInterval = 0;


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
    @SuppressWarnings("unused")
    public Point getLocation() {
        return location;
    }

    /**
     * Gets the right direction vector of the camera.
     *
     * @return the right direction vector of the camera.
     */
    @SuppressWarnings("unused")
    public Vector getRight() {
        return right;
    }

    /**
     * Gets the up direction vector of the camera.
     *
     * @return the up direction vector of the camera.
     */
    @SuppressWarnings("unused")
    public Vector getUp() {
        return up;
    }

    /**
     * Gets the forward direction vector of the camera.
     *
     * @return the forward direction vector of the camera.
     */
    @SuppressWarnings("unused")
    public Vector getTo() {
        return to;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane.
     */
    @SuppressWarnings("unused")
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane.
     */
    @SuppressWarnings("unused")
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane.
     */
    @SuppressWarnings("unused")
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

        double yI = -(i - (nY - 1) / 2d) * (height / nY);
        double xJ = (j - (nX - 1) / 2d) * (width / nX);

        //move point of pixel on vp
        if (!isZero(xJ))
            pIJ = pIJ.add(right.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(up.scale(yI));

        return new Ray(location, pIJ.subtract(location));
    }

    /**
     * Render the image
     *
     * @return the camera
     */
    public Camera renderImage() {

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        pixelManager = new PixelManager(nY, nX, printInterval);

        if (threadsCount == 0) {
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        }
        else { // see further... option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nX, nY, pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            }
            catch (InterruptedException ignore) {
            }
        }

        return this;
    }

    /**
     * Print a grid on the vp
     *
     * @param interval the interval between the lines
     * @param color    the color of the grid
     * @return the camera
     */
    public Camera printGrid(int interval, Color color) {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        int i, j;

        for (i = 0; i < ny; i += interval) {
            for (j = 0; j < nx; j++) {
                imageWriter.writePixel(i, j, color);
            }
        }
        for (i = 0; i < nx; i += interval) {
            for (j = 0; j < ny; j++) {
                imageWriter.writePixel(j, i, color);
            }
        }

        return this;
    }

    /**
     * Write the image to a file
     *
     * @return the camera
     */
    public Camera writeToImage() {
        imageWriter.writeToImage();
        return this;
    }

    /**
     * cast a ray from the camera to a given pixel
     *
     * @param nX     size of webcam in X
     * @param nY     size of webcam in Y
     * @param column the x index of the pixel
     * @param row    the y index of the pixel
     */
    private void castRay(int nX, int nY, int column, int row) {
        //a ray through the center of the pixel
        Ray ray = constructRay(nX, nY, column, row);
        //color of pixel
        Color color = rayTracer.traceRay(ray);
        //coloring the pixel
        imageWriter.writePixel(column, row, color);
        pixelManager.pixelDone();
    }

    /**
     * The Builder class is used to construct instances of Camera
     */
    public static class Builder {
        /**
         * Camera in builder
         */
        final private Camera camera;

        /**
         * default constructor
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Parameter contractor
         *
         * @param camera Camera
         */
        @SuppressWarnings("unused")
        public Builder(Camera camera) {
            this.camera = camera;
        }

        /**
         * sets camera's location
         *
         * @param p new location for camera
         * @return the builder itself
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
         * set Direction for camera with a target point
         *
         * @param to target point where the camera is directed to
         * @param up where is the "up" direction of the camera to
         * @return the builder itself
         */
        public Builder setDirection(Point to, Vector up) {
            //check if vectors are aligned
            if (camera.location == null) throw new IllegalArgumentException("Please set the camera location first");

            camera.to = to.subtract(camera.location).normalize();
            up = up.normalize();
            camera.right = camera.to.crossProduct(up).normalize();
            camera.up = camera.right.crossProduct(camera.to).normalize();

            return this;
        }

        /**
         * set Vp Size
         *
         * @param width  for Vp
         * @param height for Vp
         * @return the builder itself
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
         * @return the builder itself
         */
        public Builder setVpDistance(double distance) {
            if (alignZero(distance) <= 0)
                throw new IllegalArgumentException("distance must be positive!");
            camera.distance = distance;
            return this;
        }

        /**
         * Set the image writer
         *
         * @param imageWriter the image writer
         * @return the camera builder
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Set the ray tracer
         *
         * @param rayTracer the ray tracer
         * @return the camera builder
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Set the number of threads to use for rendering
         *
         * @param threadsCount the number of threads to use for rendering
         * @return the camera builder
         */
        public Builder setMultithreading(int threadsCount) {
            camera.threadsCount = threadsCount;
            return this;
        }

        /**
         * Set the debug print interval
         *
         * @param printInterval the debug print interval
         * @return the camera builder
         */
        public Builder setDebugPrint(double printInterval) {
            camera.printInterval = printInterval;
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

            if (alignZero(this.camera.width) <= 0) {
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
            if (this.camera.rayTracer == null) {
                msg += "Missing parameter: ray tracer.\n";
                isMissingParams = true;
            }
            if (this.camera.imageWriter == null) {
                msg += "Missing parameter: image writer.\n";
                isMissingParams = true;
            }

            if (isMissingParams)
                throw new MissingResourceException("Missing render value(s)", "Camera", msg);

            if (!isZero(camera.to.dotProduct(camera.up))) throw new IllegalArgumentException("Vectors must be align");
            if (alignZero(camera.distance) <= 0) throw new IllegalArgumentException("distance must be positive!");
            if (alignZero(camera.width) <= 0 || alignZero(camera.height) <= 0)
                throw new IllegalArgumentException("width and/or height must be positive!");
            if (camera.threadsCount < 0) throw new IllegalArgumentException("Number of threads must be positive!");
            if(camera.printInterval < 0) throw new IllegalArgumentException("printInterval must be positive!");

            //calc missing information
            camera.right = camera.to.crossProduct(camera.up).normalize();

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }
}
