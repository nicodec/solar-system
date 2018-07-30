package meli.nicolas.deciancio.solar.system.job;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import meli.nicolas.deciancio.solar.system.dao.ForecastDao;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;
import meli.nicolas.deciancio.solar.system.model.SolarSystem;
import meli.nicolas.deciancio.solar.system.model.entity.Forecast;

@Component
public class ForecastJob {

    private static final Logger LOGGER = getLogger(ForecastJob.class);

    @Value("${solar.system.forecast-job.enabled}")
    private boolean enabled;

    @Autowired
    private SolarSystem solarSystem;
    @Autowired
    private ForecastDao forecastDao;

    @Scheduled( cron = "${solar.system.forecast-job.cron}")
    public void execute() {
        if (this.enabled) {
            LOGGER.info("Starting ForecastJob");
            final ForecastInfo forecastInfo = this.solarSystem.oneDayTransitionForecast();
            final Forecast persistEntity = new Forecast();
            persistEntity.setForecastEvent(forecastInfo.getForecastEvent());
            persistEntity.setDay(forecastInfo.getDay());
            this.forecastDao.save(persistEntity);
            LOGGER.info("ForecastJob finished succesful for day {} with event {}", forecastInfo.getDay(), forecastInfo.getForecastEvent());
        } else {
            LOGGER.info("ForecastJob is DISABLED");
        }
    }
}
