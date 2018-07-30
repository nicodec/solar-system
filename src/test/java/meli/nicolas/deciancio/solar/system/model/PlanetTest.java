package meli.nicolas.deciancio.solar.system.model;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import javafx.geometry.Point2D;

@Test
public class PlanetTest {

    public void testDistance() {

    }

    public void testMove() {
        final Planet target = new Planet(-361.0, 0.0, 1000.0);

        final Double xPos = cos(toRadians(-1.0)) * 1000.0;
        final Double yPos = sin(toRadians(-1.0)) * 1000.0;
        final Point2D expected = new Point2D(xPos, yPos);

        target.move();
        assertEquals(target.getCartesianPosition() , expected);
    }
}