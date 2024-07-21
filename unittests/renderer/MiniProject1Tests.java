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
     * Test for glossy surface and diffusive glass
     */
    @Test
    public void testBlurryGlass() {

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

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
        double sphereRadius = 0.5;
        double spacing = 1.5;  // Adjust the spacing between the spheres

        // Calculate the number of spheres to fit within the square
        int numRows = (int) ((b.getZ() - a.getZ()) / spacing);
        int numCols = (int) ((f.getX() - a.getX()) / spacing);

        // Generate spheres within the square
        for (int k = 0; k <= numRows; k++) {
            for (int j = 0; j <= numCols; j++) {
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
                .setMaterial(new Material().setKD(0.2).setKS(0).setShininess(0).setKT(0))
        );

        scene.lights.add(new DirectionalLight(new Color(white).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));

        // Set up the camera
        cameraBuilder.setLocation(new Point(5, -230, -2.5).add(Vector.Y.scale(100)))
                .setDirection(Vector.Y, Vector.Z)
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("blurryGlassON", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }


    @Test
    public void testBlurryGlass0() {
        scene.setAmbientLight(new AmbientLight(new Color(lightGray).reduce(2), new Double3(0.15)));

        for (int i = -4; i < 6; i += 2) {
            scene.geometries.add(
                    new Sphere(new Point(5 * i, -1.50, -3), 3).setEmission(new Color(red).reduce(4).reduce(2.2))
                            .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0)),

                    new Sphere(new Point(5 * i, 5, 3), 3).setEmission(new Color(pink).reduce(2.2))
                            .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0)),
                    new Sphere(new Point(5 * i, -8, -8), 3).setEmission(new Color(blue).reduce(2.2))
                            .setMaterial(new Material().setKD(0.2).setKS(1).setShininess(80).setKT(0)),

                    new Polygon(new Point(5 * i - 4, -5, -11), new Point(5 * i - 4, -5, 5), new Point(5 * i + 4, -5, 5),
                            new Point(5 * i + 4, -5, -11)).setEmission(new Color(250, 235, 215).reduce(2.5))
                            .setMaterial(new Material().setKD(0.001).setKS(0.002).setShininess(1).setKT(0.95)
                                    .setBlurGlass(i == 4 ? 1 : 20, 0.3 * (i + 5), 1))
            );
        }

        scene.geometries.add(new Plane(new Point(1, 10, 1), new Point(2, 10, 1), new Point(5, 10, 0))
                .setEmission(new Color(white).reduce(3))
                .setMaterial(new Material().setKD(0.2).setKS(0).setShininess(0).setKT(0))
        );

        // scene.lights.add(new PointLight(new Color(100, 100, 150), new Point(0, 6, 0)));
        scene.lights.add(new DirectionalLight(new Color(white).reduce(1.3), new Vector(-0.4, 1, 0)));
        scene.lights.add(new SpotLight(new Color(white).reduce(2), new Point(20.43303, -7.37104, 13.77329),
                new Vector(-20.43, 7.37, -13.77)).setKL(0.6));

        // Set up the camera
        cameraBuilder.setLocation(new Point(0, -230, 0).add(Vector.Y.scale(-13)))
                .setDirection(Vector.Y, Vector.Z)
                .setVpSize(200, 200)
                .setVpDistance(1000)
                .setImageWriter(new ImageWriter("blurryGlass2", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }


}
