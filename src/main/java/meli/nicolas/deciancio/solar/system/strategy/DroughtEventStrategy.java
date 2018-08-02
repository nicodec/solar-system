package meli.nicolas.deciancio.solar.system.strategy;

import static com.google.common.base.Preconditions.checkArgument;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.DROUGHT;
import static meli.nicolas.deciancio.solar.system.utils.MathUtils.isTriangle;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import org.springframework.stereotype.Component;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

@Component
public class DroughtEventStrategy implements ForecastEventStrategy {

    /**
     * Evaluates the position of the planets. If all planets are aligned with the sun it means that it's a Drought day
     * @param planets
     * @param sunPosition
     * @return returns true if drought day
     */
    @Override
    public boolean evaluatePrediction(List<Planet> planets, Point2D sunPosition) {
        checkArgument(isNotEmpty(planets) && planets.size() == 3, "Requires 3 planets");
        final Point2D planet1 = planets.get(0).getCartesianPosition();
        final Point2D planet2 = planets.get(1).getCartesianPosition();
        final Point2D planet3 = planets.get(2).getCartesianPosition();
        if (isTriangle(planet1, planet2, planet3)) {
            return false;
        } else {
            return !isTriangle(planet1, planet2, sunPosition);
        }
    }

    @Override
    public ForecastInfo getForecastInfo(List<Planet> planets) {
        return new ForecastInfo(DROUGHT);
    }
}
