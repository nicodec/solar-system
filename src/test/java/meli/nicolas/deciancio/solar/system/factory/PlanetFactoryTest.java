package meli.nicolas.deciancio.solar.system.factory;

import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getBetazoid;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getFerengi;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getVulcan;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import meli.nicolas.deciancio.solar.system.model.Planet;

@Test
public class PlanetFactoryTest {

    public void testGetFerengi() {
        final Planet ferengi = getFerengi();

        assertEquals(ferengi.getAnglePosition(), 0.0);
        assertEquals(ferengi.getDistanceFromSun(), 500.0);
        assertEquals(ferengi.getAngularSpeed(), -1.0);
    }

    public void testGetBetazoid() {
        final Planet betazoid = getBetazoid();

        assertEquals(betazoid.getAnglePosition(), 0.0);
        assertEquals(betazoid.getDistanceFromSun(), 2000.0);
        assertEquals(betazoid.getAngularSpeed(), -3.0);
    }

    public void testGetVulcan() {
        final Planet vulcan = getVulcan();

        assertEquals(vulcan.getAnglePosition(), 0.0);
        assertEquals(vulcan.getDistanceFromSun(), 1000.0);
        assertEquals(vulcan.getAngularSpeed(), 5.0);
    }
}