package meli.nicolas.deciancio.solar.system.strategy;

import java.util.List;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.Planet;

public interface ForecastEventStrategy {

    boolean evaluatePrediction(List<Planet> planets, Point2D sunPosition);

    ForecastInfo getForecastInfo(List<Planet> planets);
}
