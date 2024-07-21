package renderer;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;
import static java.awt.Color.white;

/**
 * Test class for glossy surface and diffusive glass
 */
public class MiniProject1Tests {

    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene glossy surface and diffusive glass");
    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder().setRayTracer(new SimpleRayTracer(scene));

    /**
     * Test for glossy surface
     */
    @Test
    public void testGlossy() {

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

        Point a = new Point(-4, -5, -11);
        Point b = new Point(-4, -5, 5);
        Point c = new Point(4, -5, 5);
        Point d = new Point(4, -5, -11);
        Point e = new Point(12, -5, 5);
        Point f = new Point(12, -5, -11);

        // Define colors
        Color red = new Color(java.awt.Color.RED);
        Color blue = new Color(java.awt.Color.BLUE);

        // Define sphere properties
        double sphereRadius = 1.3;
        double spacing = 3.8;  // Adjust the spacing between the spheres

        // Set the number of rows and columns
        int numRows = 5;
        int numCols = 5;

        // Generate spheres within the square
        for (int k = 0; k < numRows; k++) {
            for (int j = 0; j < numCols; j++) {
                double x = a.getX() + j * spacing;
                double z = a.getZ() + k * spacing;

                scene.geometries.add(
                        new Sphere(new Point(x, k % 2 == 0 ? -1.50 : 3, z), sphereRadius)
                                .setEmission(k % 2 == 0 ? red : blue)
                                .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0))
                );
            }
        }

        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(pink).reduce(3))
                .setMaterial(new Material().setKD(0.2).setShininess(0).setKR(20).setKG(100).setKT(0.5).setKB(100)
                        .setBlurGlass(10, 30, 1))
        );

        scene.lights.add(new DirectionalLight(new Color(white).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));

        // Set up the camera
        cameraBuilder.setLocation(new Point(5, -230, -2.5).add(Vector.Y.scale(100)))
                .setDirection(Vector.Y, Vector.Z)
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("GlossyTest - 1", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Test for diffusive glass
     */
    @Test
    public void testDiffused() {

        scene.setAmbientLight(new AmbientLight(new Color(lightGray), new Double3(0.15)));

        Point a = new Point(-4, -5, -11);
        Point b = new Point(-4, -5, 5);
        Point c = new Point(4, -5, 5);
        Point d = new Point(4, -5, -11);
        Point e = new Point(12, -5, 5);
        Point f = new Point(12, -5, -11);

        int i = 0;

        scene.geometries.add(

                new Triangle(a, b, c).setEmission(new Color(250, 235, 215).reduce(2.5))
                        .setMaterial(new Material().setKD(0.001).setKS(0.002).setShininess(1).setKT(0.95)
                                .setBlurGlass(i == 4 ? 1 : 20, 0.3 * (i + 5), 1)),
                new Triangle(a, b, d).setEmission(new Color(250, 235, 215).reduce(2.5))
                        .setMaterial(new Material().setKD(0.001).setKS(0.002).setShininess(1).setKT(0.95)
                                .setBlurGlass(i == 4 ? 1 : 20, 0.3 * (i + 5), 1)),

                new Triangle(c, e, f).setEmission(new Color(250, 235, 215).reduce(2.5))
                        .setMaterial(new Material().setKD(0.001).setKS(0.002).setShininess(1).setKT(0.95)
                                .setBlurGlass(i == 4 ? 1 : 20, 0.3 * (i + 5), 1)),
                new Triangle(d, e, f).setEmission(new Color(250, 235, 215).reduce(2.5))
                        .setMaterial(new Material().setKD(0.001).setKS(0.002).setShininess(1).setKT(0.95)
                                .setBlurGlass(i == 4 ? 1 : 20, 0.3 * (i + 5), 1))
        );

        // Define colors
        Color red = new Color(java.awt.Color.RED);
        Color blue = new Color(java.awt.Color.BLUE);

        // Define sphere properties
        double sphereRadius = 1.3;
        double spacing = 3.8;  // Adjust the spacing between the spheres

        // Set the number of rows and columns
        int numRows = 5;
        int numCols = 5;

        // Generate spheres within the square
        for (int k = 0; k < numRows; k++) {
            for (int j = 0; j < numCols; j++) {
                double x = a.getX() + j * spacing;
                double z = a.getZ() + k * spacing;

                scene.geometries.add(
                        new Sphere(new Point(x, k % 2 == 0 ? -1.50 : 3, z), sphereRadius)
                                .setEmission(k % 2 == 0 ? red : blue)
                                .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0))
                );
            }
        }

        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(pink).reduce(3))
                .setMaterial(new Material().setKD(0.2).setShininess(100).setKR(0.5))
        );

        scene.lights.add(new DirectionalLight(new Color(white).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));

        // Set up the camera
        cameraBuilder.setLocation(new Point(5, -230, -2.5).add(Vector.Y.scale(100)))
                .setDirection(Vector.Y, Vector.Z)
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("DiffusedTest - 1", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }

}
