package meli.nicolas.deciancio.solar.system.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getBetazoid;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getFerengi;
import static meli.nicolas.deciancio.solar.system.factory.PlanetFactory.getVulcan;
import static meli.nicolas.deciancio.solar.system.utils.CollectionUtils.safeStream;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.geometry.Point2D;
import meli.nicolas.deciancio.solar.system.dao.ForecastDao;
import meli.nicolas.deciancio.solar.system.model.entity.Forecast;
import meli.nicolas.deciancio.solar.system.report.ForecastReport;
import meli.nicolas.deciancio.solar.system.strategy.ForecastEventStrategyManager;

@Transactional
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SolarSystem {

    private static final Logger LOGGER = getLogger(SolarSystem.class);

    private static final int DAYS_YEAR = 360;

    @Autowired
    private ForecastDao dao;

    @Autowired
    private ForecastReport forecastReport;

    @Autowired
    private ForecastEventStrategyManager forecastEventManager;

    @Value("${solar.system.init.years}")
    private int initYears;

    private List<Planet> planets;
    private Long actualDay = 0L;
    private Point2D sunPosition = new Point2D(0.0, 0.0);

    /**
     * Initializes the instance on startup and generates the initial Forecast Report for the years
     * set as environment property as user requirement
     */
    @PostConstruct
    public void init() {
        this.planets = newArrayList(getFerengi(), getBetazoid(), getVulcan());
        this.getForecast(this.initYears);
    }

    /**
     * Performs a forecast over the current solar system for the number of {@param years} defined as parameter
     * for this method
     * @param years number of years for forecast to perform
     */
    public void getForecast(int years) {
        LOGGER.info("Starting forecast for {} years", years);
        final int totalDays = years * DAYS_YEAR;
        final LinkedList<ForecastInfo> result = newLinkedList();
        for (int i = 0; i < totalDays; i++) {
            final ForecastInfo forecastInfo = this.oneDayTransitionForecast();
            this.forecastReport.addToReport(forecastInfo);
            result.add(forecastInfo);
        }
        if (isNotEmpty(result)) {
            persistForecast(result);
        }
        LOGGER.info("Forecast ready to send");
        this.forecastReport.sendReport();
    }

    /**
     * Saves the {@link ForecastInfo} introduced in the Database calling {@link ForecastDao} save method
     * @param forecastInfo info to save
     */
    private void persistForecast(LinkedList<ForecastInfo> forecastInfo) {
        final List<Forecast> forecastList = safeStream(forecastInfo).map(f -> {
            final Forecast forecast = new Forecast();
            forecast.setDay(f.getDay());
            forecast.setForecastEvent(f.getForecastEvent());
            return forecast;
        }).collect(Collectors.toList());

        dao.saveAll(forecastList);
    }

    /**
     * Performs a single day transition of forecast and returns the information collected for the solar system
     * in current transition moment
     * @return {@link ForecastInfo} collected
     */
    public ForecastInfo oneDayTransitionForecast() {
        final ForecastInfo info = forecastEventManager.getForecastInfoForPlanets(planets, sunPosition);
        info.setDay(actualDay);
        safeStream(this.planets).forEach(Planet::move);
        this.actualDay++;

        return info;
    }
}
