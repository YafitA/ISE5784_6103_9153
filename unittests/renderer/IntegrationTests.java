package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests between creating rays from a camera and
 * calculating sections of a ray with geometric bodies
 */
public class IntegrationTests {

    /**
     * Camera Builder
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            //.setRayTracer(new SimpleRayTracer(new Scene("Test")))
            //.setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3, 3);

    /**
     * Camera (0,0,0) center
     */
    private final Camera camera000 = cameraBuilder.build();

    /**
     * Calculate the number of integration points between the camera's ray and the shape
     *
     * @param camera Camera
     * @param shape  Shape
     * @return number of integration Points Number
     */
    int integrationPointsNumber(Camera camera, Intersectable shape) {
        int numPoints = 0;
        Ray ray;
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                ray = camera.constructRay(3, 3, j, i);
                List<Point> intersections = shape.findIntersections(ray);
                numPoints += (intersections != null) ? intersections.size() : 0;
            }
        }
        return numPoints;
    }

    /**
     * Test method for integration between rays of constructRay from camera and Sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testIntegrationSphere() {

        //TC01: test case r=1 (2 points)
        final Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, integrationPointsNumber(camera000, sphere1),
                "wrong number of intersection with sphere");

        //TC02: test case r=2.5 (18 points)
        final Camera camera2 = cameraBuilder.setLocation(new Point(0, 0, 0.5)).build();
        final Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, integrationPointsNumber(camera2, sphere2),
                "wrong number of intersection with sphere");

        //TC03: test case r=2 (10 points)
        final Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, integrationPointsNumber(camera2, sphere3),
                "wrong number of intersection with sphere");

        //TC04: test case r=4 (9 points)
        final Sphere sphere4 = new Sphere(new Point(0, 0, -0.5), 4);
        assertEquals(9, integrationPointsNumber(camera2, sphere4),
                "wrong number of intersection with sphere");

        //TC05: test case r=0.5 (0 point)
        final Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, integrationPointsNumber(camera2, sphere5),
                "wrong number of intersection with sphere");
    }

    /**
     * Test method for integration between rays of constructRay from camera and Sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testIntegrationTriangle() {

        final Point p1 = new Point(1, -1, -2);
        final Point p2 = new Point(-1, -1, -2);

        //TC01: test case small triangle (1 point)
        Triangle triangle1 = new Triangle(new Point(0, 1, -2), p1, p2);
        assertEquals(1, integrationPointsNumber(camera000, triangle1),
                "wrong number of intersection with triangle");

        //TC02: test case big triangle (2 points)
        Triangle triangle2 = new Triangle(new Point(0, 20, -2), p1, p2);
        assertEquals(2, integrationPointsNumber(camera000, triangle2),
                "wrong number of intersection with triangle");
    }

    /**
     * Test method for integration between rays of constructRay from camera and Sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testIntegrationPlane() {
        //TC01: Plane is on vp (9 points)
        final Plane Plane1 = new Plane(new Point(10, 0, -10), new Point(0, 10, -10), new Point(0, -10, -10));
        assertEquals(9, integrationPointsNumber(camera000, Plane1),
                "wrong number of intersection with Plane");

        //TC02: Plane cuts vp a small slope (9 points)
        final Plane Plane2 = new Plane(new Point(10, 0, -10), new Point(0, 10, -2), new Point(0, -10, -15));
        assertEquals(9, integrationPointsNumber(camera000, Plane2),
                "wrong number of intersection with Plane");

        //TC03: Plane cuts vp a big slope (6 points)
        final Plane Plane3 = new Plane(new Point(10, 0, -10), new Vector(0, 6, -5));
        assertEquals(6, integrationPointsNumber(camera000, Plane3),
                "wrong number of intersection with Plane");

    }
}