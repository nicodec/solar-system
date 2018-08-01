package meli.nicolas.deciancio.solar.system.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import javafx.geometry.Point2D;

@Test
public class PlanetTest {

    public void testDistance() {
        final Planet planet1 = new Planet(9.0, 0.0, 1000.0);
        final Planet planet2 = new Planet(9.0, 90.0, 1000.0);

        final Double expected = new Point2D(0.0, 1000.0).distance(new Point2D(1000.0, 0.0));

        final Double result = planet1.distance(planet2);

        assertTrue(equalsDouble(result, expected));
    }

    public void testMove() {
        final Planet target = new Planet(-361.0, 0.0, 1000.0);

        final Double xPos = cos(toRadians(-1.0)) * 1000.0;
        final Double yPos = sin(toRadians(-1.0)) * 1000.0;
        final Point2D expected = new Point2D(xPos, yPos);

        target.move();
        assertEquals(target.getCartesianPosition() , expected);
        assertEquals(target.getAnglePosition() , -1.0);
    }
}