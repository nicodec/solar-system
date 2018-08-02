package meli.nicolas.deciancio.solar.system.strategy;

import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.NOTHING;
import static meli.nicolas.deciancio.solar.system.utils.CollectionUtils.safeStream;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

@Component
public class ForecastEventStrategyManager {

    @Autowired
    private List<ForecastEventStrategy> strategies;

    /**
     * Evaluates the position of the planets based in a list of strategies, when it finds a strategy that match the planets configuration
     * returns a {@link ForecastInfo}. If there is no strategy matching it returns a ForecastInfo with {@link meli.nicolas.deciancio.solar.system.model.ForecastEvent#NOTHING}
     * @param planets
     * @param sunPosition
     * @return {@link ForecastInfo} for {@param planets} configuration
     */
    public ForecastInfo getForecastInfoForPlanets(List<Planet> planets, Point2D sunPosition) {
        return safeStream(this.strategies)
                .filter(strategy -> strategy.evaluatePrediction(planets, sunPosition))
                .findFirst()
                .map(forecastEventStrategy -> forecastEventStrategy.getForecastInfo(planets))
                .orElse(new ForecastInfo(NOTHING));
    }
}
