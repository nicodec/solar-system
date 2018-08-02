package meli.nicolas.deciancio.solar.system.utils;

import static java.lang.StrictMath.abs;
import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static org.springframework.util.Assert.notNull;

import javafx.geometry.Point2D;

public class MathUtils {

    /**
     * Evaluate if points {@param pointA}, {@param pointB} and {@param pointC} forms a triangle
     * @param pointA
     * @param pointB
     * @param pointC
     * @return true if points forms a triangle
     */
    public static boolean isTriangle(Point2D pointA, Point2D pointB, Point2D pointC) {
        return !equalsDouble(triangleArea(pointA, pointB, pointC), 0.0);
    }

    /**
     * Calculates the area of the triangle formed by points {@param pointA}, {@param pointB} and {@param pointC}
     * @param pointA
     * @param pointB
     * @param pointC
     * @return area of the triangle
     * @throws IllegalArgumentException if any parameter is null
     */
    public static Double triangleArea(Point2D pointA, Point2D pointB, Point2D pointC) {
        notNull(pointA, "pointA must not be null");
        notNull(pointB, "pointB must not be null");
        notNull(pointC, "pointC must not be null");
        return abs((pointA.getX() - pointC.getX()) * (pointB.getY() - pointA.getY())
                -  (pointA.getX() - pointB.getX()) * (pointC.getY() - pointA.getY())) * 0.5;
    }

    /**
     * Calculates the perimeter of the triangle formed by points {@param pointA}, {@param pointB} and {@param pointC}
     * @param pointA
     * @param pointB
     * @param pointC
     * @return perimeter of the triangle
     * @throws IllegalArgumentException if any parameter is null
     */
    public static Double trianglePerimeter(Point2D pointA, Point2D pointB, Point2D pointC) {
        notNull(pointA, "pointA must not be null");
        notNull(pointB, "pointB must not be null");
        notNull(pointC, "pointC must not be null");
        final Double distanceAB = pointA.distance(pointB);
        final Double distanceBC = pointB.distance(pointC);
        final Double distanceCA = pointC.distance(pointA);
        return distanceAB + distanceBC + distanceCA;
    }
}
