package meli.nicolas.deciancio.solar.system.strategy;

import static com.google.common.base.Preconditions.checkArgument;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.RAINY;
import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.isTriangle;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.triangleArea;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.trianglePerimeter;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import org.springframework.stereotype.Component;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

@Component
public class RainyEventStrategy implements ForecastEventStrategy {

    /**
     * Evaluates the position of the planets. If the planets form a triangle and the sun is inside of it means that it's a Rainy day
     * @param planets
     * @param sunPosition
     * @return returns true if rainy day
     */
    @Override
    public boolean evaluatePrediction(List<Planet> planets, Point2D sunPosition) {
        checkArgument(isNotEmpty(planets) && planets.size() == 3, "Requires 3 planets");
        final Point2D planet1 = planets.get(0).getCartesianPosition();
        final Point2D planet2 = planets.get(1).getCartesianPosition();
        final Point2D planet3 = planets.get(2).getCartesianPosition();
        if (!isTriangle(planet1, planet2, planet3)) {
            return false;
        }
        final Double triangleArea1 = triangleArea(planet1, planet2, sunPosition);
        final Double triangleArea2 = triangleArea(planet1, sunPosition, planet3);
        final Double triangleArea3 = triangleArea(sunPosition, planet2, planet3);

        return equalsDouble(triangleArea1 + triangleArea2 + triangleArea3, triangleArea(planet1, planet2, planet3));
    }

    @Override
    public ForecastInfo getForecastInfo(List<Planet> planets) {
        final Point2D planet1 = planets.get(0).getCartesianPosition();
        final Point2D planet2 = planets.get(1).getCartesianPosition();
        final Point2D planet3 = planets.get(2).getCartesianPosition();
        final Double trianglePerimeter = trianglePerimeter(planet1, planet2, planet3);
        return new ForecastInfo(RAINY, trianglePerimeter);
    }
}
