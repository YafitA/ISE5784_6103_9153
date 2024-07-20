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
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder().setRayTracer(new SimpleRayTracer(scene));


    /**
     * Test for glossy surface and diffusive glass
     */
    @Test
    public void testBlurryGlass() {

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
                .setImageWriter(new ImageWriter("blurryGlass1", 500, 500))
                .setRayTracer(new SimpleRayTracer(scene))
                .build()
                .renderImage()
                .writeToImage();
    }





}
