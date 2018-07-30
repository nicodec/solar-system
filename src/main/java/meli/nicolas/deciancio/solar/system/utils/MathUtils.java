package meli.nicolas.deciancio.solar.system.utils;

import static java.lang.StrictMath.abs;
import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static org.springframework.util.Assert.notNull;

import javafx.geometry.Point2D;

public class MathUtils {

    public static boolean isTriangle(Point2D pointA, Point2D pointB, Point2D pointC) {
        return !equalsDouble(triangleArea(pointA, pointB, pointC), 0.0);
    }

    public static Double triangleArea(Point2D pointA, Point2D pointB, Point2D pointC) {
        notNull(pointA, "pointA must not be null");
        notNull(pointB, "pointB must not be null");
        notNull(pointC, "pointC must not be null");
        return abs((pointA.getX() - pointC.getX()) * (pointB.getY() - pointA.getY())
                -  (pointA.getX() - pointB.getX()) * (pointC.getY() - pointA.getY())) * 0.5;
    }

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
