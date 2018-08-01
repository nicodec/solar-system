package meli.nicolas.deciancio.solar.system.utils;

import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.isTriangle;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.triangleArea;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.trianglePerimeter;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javafx.geometry.Point2D;

@Test
public class MathUtilsTest {

    @DataProvider
    public Object[][] testTriangleAreaError_provider() {
        return new Object[][] {
                {null, null, null},
                {new Point2D(0.0,0.0), null, null},
                {new Point2D(0.0,0.0), new Point2D(0.0,0.0), null}
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "testTriangleAreaError_provider")
    public void testTriangleArea(Point2D point1, Point2D point2, Point2D point3) {
        final Double result = triangleArea(point1, point2, point3);
    }

    public void testTriangleArea() {
        final Point2D point1 = new Point2D(0.0, 0.0);
        final Point2D point2 = new Point2D(10.0, 0.0);
        final Point2D point3 = new Point2D(0.0, 10.0);

        final Double result = triangleArea(point1, point2, point3);

        assertEquals(result, 50.0);
        assertTrue(isTriangle(point1, point2, point3));
    }

    public void testTrianglePerimeter() {
        final Point2D point1 = new Point2D(0.0, 0.0);
        final Point2D point2 = new Point2D(10.0, 0.0);
        final Point2D point3 = new Point2D(0.0, 10.0);

        final Double result = trianglePerimeter(point1, point2, point3);

        assertTrue(equalsDouble(result, 34.142135));
    }
}