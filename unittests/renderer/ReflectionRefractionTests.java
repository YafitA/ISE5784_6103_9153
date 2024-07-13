/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.*;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKL(0.0004).setKQ(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setDirection(Point.ZERO, Vector.Y)
                .setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKL(0.00001).setKQ(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setDirection(Point.ZERO, Vector.Y)
                .setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spotlight with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKL(4E-5).setKQ(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setDirection(Point.ZERO, Vector.Y)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * our test
     */
    @Test
    public void ourTest() {

        Color[] colors = {
                new Color(255, 0, 255),
                new Color(0, 0, 255),
                new Color(255, 255, 0),
                new Color(0, 255, 0),
                new Color(255, 0, 0)
        };

        // Add stars as spheres with varying sizes and colors
        double N = 15;  // number of spheres in the spiral
        double R = 500; // radius of the spiral

        double[] numbers = {0.1, 0.2, 0.35, 0.5, 0.7, 1};

        // Add nebulae as semi-transparent spheres with emissive colors
        for (int i = 0; i < 6; i++) {
            double radius = numbers[i] * 200 + 100;
            Point position = new Point(numbers[i] * 2000 - 1000, numbers[i] * 2000 - 1000, numbers[i] * -1000 - 500);
            scene.geometries.add(new Sphere(position, radius)
                    .setEmission(colors[i % colors.length])
                    .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0.5).setKR(0.15)));
        }

        // Add ambient light
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.1));

        // Add spotlights to simulate cosmic light sources
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(500, 500, -100), new Vector(-1, -1, -1))
                .setKL(4E-5).setKQ(2E-7));
        scene.lights.add(new PointLight(new Color(500, 300, 300), new Point(0, 1000, -500))
                .setKL(0.0001).setKQ(0.00001));

        scene.setBackground(new Color(64, 64, 200));

        // Add two transparent pyramids with inner pyramids
        addPyramidsToScene(scene, colors);

        // Set up the camera
        cameraBuilder.setLocation(new Point(0, 0, 2000)).setVpDistance(2000).setDirection(Point.ZERO, Vector.Y)
                .setVpSize(2000, 2000)
                .setImageWriter(new ImageWriter("OurTest", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Adds two large pyramids and two small pyramids (inside the large pyramids) to the scene.
     * The large pyramids are located at the right-bottom and left-top positions, and the small pyramids are inside the large ones.
     * for ourTest test
     *
     * @param scene the scene to which the pyramids are added
     * @param colors an array of colors used for the emission of the pyramids
     */
    private void addPyramidsToScene(Scene scene, Color[] colors) {
        // Large pyramid 1 (right-bottom), enlarged by 3
        Point p1 = new Point(700, -500, -600);
        Point p2 = new Point(900, -500, -600);
        Point p3 = new Point(800, -300, -600);
        Point p4 = new Point(800, -400, 0);

        // Add large pyramid 1
        scene.geometries.add(new Triangle(p1, p2, p4).setEmission(colors[0]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p2, p3, p4).setEmission(colors[1]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p3, p1, p4).setEmission(colors[2]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p1, p2, p3).setEmission(colors[3]).setMaterial(new Material().setKT(0.5)));

        // Small pyramid inside large pyramid 1, enlarged by 3
        Point sp1 = new Point(750, -450, -450);
        Point sp2 = new Point(850, -450, -450);
        Point sp3 = new Point(800, -350, -450);
        Point sp4 = new Point(800, -400, -150);

        // Add small pyramid inside large pyramid 1
        scene.geometries.add(new Triangle(sp1, sp2, sp4).setEmission(colors[4]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp2, sp3, sp4).setEmission(colors[0]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp3, sp1, sp4).setEmission(colors[1]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp1, sp2, sp3).setEmission(colors[2]).setMaterial(new Material().setKT(0.5)));

        // Large pyramid 2 (left-top), enlarged by 3
        Point p5 = new Point(-700, 500, -600);
        Point p6 = new Point(-900, 500, -600);
        Point p7 = new Point(-800, 700, -600);
        Point p8 = new Point(-800, 600, 0);

        // Add large pyramid 2
        scene.geometries.add(new Triangle(p5, p6, p8).setEmission(colors[3]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p6, p7, p8).setEmission(colors[4]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p7, p5, p8).setEmission(colors[0]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(p5, p6, p7).setEmission(colors[1]).setMaterial(new Material().setKT(0.5)));

        // Small pyramid inside large pyramid 2, enlarged by 3
        Point sp5 = new Point(-750, 550, -450);
        Point sp6 = new Point(-850, 550, -450);
        Point sp7 = new Point(-800, 650, -450);
        Point sp8 = new Point(-800, 600, -150);

        // Add small pyramid inside large pyramid 2
        scene.geometries.add(new Triangle(sp5, sp6, sp8).setEmission(colors[2]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp6, sp7, sp8).setEmission(colors[3]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp7, sp5, sp8).setEmission(colors[4]).setMaterial(new Material().setKT(0.5)));
        scene.geometries.add(new Triangle(sp5, sp6, sp7).setEmission(colors[0]).setMaterial(new Material().setKT(0.5)));
    }

}
