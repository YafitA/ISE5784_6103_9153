package renderer;

import geometries.Intersectable;
import geometries.Sphere;
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
public class integrationTests {

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            //.setRayTracer(new SimpleRayTracer(new Scene("Test")))
            //.setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1)
            .setVpSize(3,3);

    /**
     * Finds the number of Integration Points between camera and the shape
     * @param camera camera object
     * @param shape shape object
     * @return number of Integration Points
     */
    int IntegrationPointsNumber(Camera camera, Intersectable shape){
        int h=(int)camera.getHeight();
        int w=(int)camera.getWidth();
        int numPoints=0;
        Ray ray;
        for (int j = 0; j < w; j++) {
            for (int i = 0; i < h; i++) {
                ray=camera.constructRay(h,w,j,i);
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
        final Camera camera1=cameraBuilder.build();
        final Sphere sphere1=new Sphere(new Point(0,0,-3),1);
        assertEquals(2,IntegrationPointsNumber(camera1,sphere1),
                "wrong number of intersection with sphere");

        //TC02: test case r=2.5 (18 points)
        final Camera camera2=cameraBuilder.setLocation(new Point(0,0,0.5)).build();
        final Sphere sphere2=new Sphere(new Point(0,0,-2.5),2.5);
        assertEquals(18,IntegrationPointsNumber(camera2,sphere2),
                "wrong number of intersection with sphere");

        //97-98
    }

    /**
     * Test method for integration between rays of constructRay from camera and Sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    void testIntegrationTriangle() {
    }

    /**
     * Test method for integration between rays of constructRay from camera and Sphere
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     * {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    void testIntegrationPlane() {
    }

}
