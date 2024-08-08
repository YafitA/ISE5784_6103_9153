package renderer;

import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Mini-Project 2 tests
 */
public class MiniProject2Tests {

    /**
     * Enum to represent the mode of bounding volume hierarchy usage.
     */
    public enum BVHMode {
        /**
         * No bounding volume hierarchy
          */
        NONE,
        /**
         * Use bounding boxes only
         */
        CBR,
        /**
         * Use BVH
         */
        BVH
    }

    /**
     * param to turn glossyAffect on or off
     */
    private final boolean isAffectGlossyOn = true;

    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene mini project 2");

    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder().setRayTracer(new SimpleRayTracer(scene));

    /**
     * test that'll create an image, testing the time depends on different params
     *
     * @param mode             A param that'll turn on/off the BVH
     * @param isMT             A param for number of threads
     * @param isAffectGlossyOn A param that'll turn on/off glossy the affect
     * @param photoName        Name for photo
     */
    private void testBVHAndThreadsAndGlossyRunningTime(BVHMode mode, boolean isMT, boolean isAffectGlossyOn, String photoName) {

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        Point a = new Point(-4, -5, -11);

        // Define colors
        Color red = new Color(java.awt.Color.RED);
        Color blue = new Color(java.awt.Color.BLUE);

        // Define sphere properties
        double sphereRadius = 1.0;  // Adjusted to fit more spheres in the scene
        double spacing = 3;  // Adjusted to fit more spheres in the scene

        // Calculate the number of rows and columns to get approximately 500 spheres
        int numRows = (int) Math.ceil(Math.sqrt(500));
        int numCols = (int) Math.ceil(500.0 / numRows);

        // Calculate the midpoints to position the camera correctly
        double midX = a.getX() + (numCols - 1) * spacing / 2;
        double midZ = a.getZ() + (numRows - 1) * spacing / 2;

        // Generate spheres within the grid
        for (int k = 0; k < numRows; k++) {
            for (int j = 0; j < numCols; j++) {

                double x = a.getX() + j * spacing;
                double z = a.getZ() + k * spacing;

                scene.geometries.add(
                        new Sphere(new Point(x, 0, z), sphereRadius)
                                .setEmission(k % 2 == 0 ? red : blue)
                                .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0))
                );
            }
        }

        // Add plane
        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(java.awt.Color.PINK).reduce(3))
                .setMaterial(new Material().setKD(0.2).setShininess(1000).setKR(0.8).setKS(0.8)
                        .setBlurGlass(isAffectGlossyOn ? 300 : 1, 10, 2))
        );


        //Enum modes for running - normal, CBR, BVH
        switch (mode) {
            case CBR:
                scene.geometries.setCBR();
                break;
            case BVH:
                scene.geometries.setBVH();
                break;
            default:
                break;
        }


        // Add lighting
        scene.lights.add(new DirectionalLight(new Color(WHITE).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(WHITE).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));
        scene.lights.add(new SpotLight(new Color(WHITE), new Point(midX, -200, midZ), new Vector(1, 1, 1)));


        // Set up the camera
        cameraBuilder.setLocation(new Point(midX, -350, midZ))
                .setDirection(Vector.Y, Vector.Z)
                .setMultithreading(isMT ? 3 : 0)
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter(photoName, 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();

    }

    /**
     * Test without improvements
     * multiple-threads        OFF
     * BVHMode                 NONE
     */
    @Test
    public void testNormal() {
        testBVHAndThreadsAndGlossyRunningTime(BVHMode.NONE, false, isAffectGlossyOn, "NORMAL");
    }

    /**
     * Test with MT
     * multiple-threads     ON
     * BVHMode              NONE
     */
    @Test
    public void testMT() {
        testBVHAndThreadsAndGlossyRunningTime(BVHMode.NONE, true, isAffectGlossyOn, "MT");
    }

    /**
     * Test with MT and CBR
     * multiple-threads     ON
     * BVHMode              CBR
     */
    @Test
    public void testCBR() {
        testBVHAndThreadsAndGlossyRunningTime(BVHMode.CBR, true, isAffectGlossyOn, "CBR");
    }

    /**
     * Test with MT and BVH
     * multiple-threads    ON
     * BVHMode             BVH
     */
    @Test
    public void testBVH() {
        testBVHAndThreadsAndGlossyRunningTime(BVHMode.BVH, true, isAffectGlossyOn, "BVH");
    }

}
