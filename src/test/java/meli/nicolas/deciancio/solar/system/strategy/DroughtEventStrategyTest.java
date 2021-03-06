package meli.nicolas.deciancio.solar.system.strategy;

import static com.google.common.collect.Lists.newArrayList;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getVulcan;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.DROUGHT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

@Test
public class DroughtEventStrategyTest {

    private DroughtEventStrategy target;

    @BeforeClass
    public void init() {
        this.target = new DroughtEventStrategy();
    }

    @DataProvider
    public Object[][] dataProvider() {

        return new Object[][] {
                {0.0, 180.0, 90.0, false },
                {0.0, -90.0, 90.0, false },
                {10.0, 180.0, 90.0, false },
                {10.0, 200.0, 270.0, false },
                {10.0, 180.0, 270.0, false },
                {10.0, -180.0, -90.0, false },
                {0.0, 180.0, 0.0, true },
        };
    }

    @Test(dataProvider = "dataProvider")
    public void testEvaluatePrediction_ok(Double angle1, Double angle2, Double angle3, boolean expected) {
        final List<Planet> planets = newArrayList(
                new Planet(0.0, angle1, 10.0),
                new Planet(0.0, angle2, 10.0),
                new Planet(0.0, angle3, 10.0)
        );

        assertEquals(this.target.evaluatePrediction(planets, new Point2D(0.0, 0.0)), expected);
    }

    @Test(dataProvider = "dataProvider")
    public void testGetForecastInfo_ok(Double angle1, Double angle2, Double angle3, boolean expected) {
        final List<Planet> planets = newArrayList(
                new Planet(0.0, angle1, 10.0),
                new Planet(0.0, angle2, 10.0),
                new Planet(0.0, angle3, 10.0)
        );

        final ForecastInfo result = this.target.getForecastInfo(planets);
        assertNotNull(result);
        assertEquals(result.getForecastEvent(), DROUGHT);
        assertEquals(result.getTrianglePerimeter(), 0.0);
        assertNull(result.getDay());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEvaluatePrediction_Error() {
        final List<Planet> planets = newArrayList(getVulcan(), getVulcan(), getVulcan(), getVulcan());
        this.target.evaluatePrediction(planets, new Point2D(0.0, 0.0));
    }
}