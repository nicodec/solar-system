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

    public ForecastInfo getForecastInfoForPlanets(List<Planet> planets, Point2D sunPosition) {
        return safeStream(this.strategies)
                .filter(strategy -> strategy.evaluatePrediction(planets, sunPosition))
                .findFirst()
                .map(forecastEventStrategy -> forecastEventStrategy.getForecastInfo(planets))
                .orElse(new ForecastInfo(NOTHING));
    }
}
