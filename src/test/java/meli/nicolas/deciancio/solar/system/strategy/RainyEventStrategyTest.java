package meli.nicolas.deciancio.solar.system.strategy;

import static com.google.common.collect.Lists.newArrayList;
import static meli.nicolas.deciancio.solar.system.factory.PlanteFactory.getVulcan;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.RAINY;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

@Test
public class RainyEventStrategyTest {

    private RainyEventStrategy target;

    @BeforeClass
    public void init() {
        this.target = new RainyEventStrategy();
    }

    @DataProvider
    public Object[][] dataProvider() {

        return new Object[][] {
                {0.0, 180.0, 90.0, true },
                {0.0, -90.0, 90.0, true },
                {10.0, 180.0, 90.0, false },
                {10.0, 200.0, 270.0, false },
                {10.0, 180.0, 270.0, true },
                {10.0, -180.0, -90.0, true },
        };
    }

    @Test(dataProvider = "dataProvider")
    public void testEvaluatePrediction(Double angle1, Double angle2, Double angle3, boolean expected) {
        final List<Planet> planets = newArrayList(
                new Planet(0.0, angle1, 10.0),
                new Planet(0.0, angle2, 10.0),
                new Planet(0.0, angle3, 10.0)
        );

        assertEquals(this.target.evaluatePrediction(planets, new Point2D(0.0, 0.0)), expected);
    }

    @Test(dataProvider = "dataProvider")
    public void testGetForecastInfo(Double angle1, Double angle2, Double angle3, boolean expected) {
        final List<Planet> planets = newArrayList(
                new Planet(0.0, angle1, 10.0),
                new Planet(0.0, angle2, 10.0),
                new Planet(0.0, angle3, 10.0)
        );

        final ForecastInfo result = this.target.getForecastInfo(planets);
        assertNotNull(result);
        assertEquals(result.getForecastEvent(), RAINY);
        assertNotNull(result.getTrianglePerimeter());
        assertTrue(result.getTrianglePerimeter() > 0.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testEvaluatePrediction_Error() {
        final List<Planet> planets = newArrayList(getVulcan(), getVulcan(), getVulcan(), getVulcan());
        this.target.evaluatePrediction(planets, new Point2D(0.0, 0.0));
    }
}