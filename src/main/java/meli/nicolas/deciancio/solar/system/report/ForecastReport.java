package meli.nicolas.deciancio.solar.system.report;

import static java.util.Objects.isNull;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.DROUGHT;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.OPTIMUM_WEATHER;
import static meli.nicolas.deciancio.solar.system.model.ForecastEvent.RAINY;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.EnumMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import meli.nicolas.deciancio.solar.system.model.ForecastEvent;
import meli.nicolas.deciancio.solar.system.model.ForecastInfo;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ForecastReport {

    private static final Logger LOGGER = getLogger(ForecastReport.class);

    private ForecastInfo maxIntensityRain;

    private Long daysPeriod;

    private ForecastEvent lastForecast;

    private EnumMap<ForecastEvent,Long> eventPeriod;

    @PostConstruct
    public void init() {
        this.eventPeriod = new EnumMap<>(ForecastEvent.class);
        this.daysPeriod = 0L;
    }

    public void addToReport(ForecastInfo info) {
        if (isNull(info)) {
            return;
        }
        if (isNull(lastForecast) || !lastForecast.equals(info.getForecastEvent())) {
            lastForecast = info.getForecastEvent();
            final Long count = eventPeriod.getOrDefault(lastForecast, 0L);
            eventPeriod.put(lastForecast, count + 1L);
        }
        if (RAINY.equals(info.getForecastEvent())) {
            if (isNull(maxIntensityRain) || maxIntensityRain.getTrianglePerimeter() < info.getTrianglePerimeter()) {
                maxIntensityRain = info;
            }
        }
        this.daysPeriod++;
    }

    public void sendReport() {
        LOGGER.info("Reporte para el periodo de {} dias.", this.daysPeriod);
        LOGGER.info("Cantidad de periodos de sequia: {}", this.eventPeriod.getOrDefault(DROUGHT, 0L));
        LOGGER.info("Cantidad de periodos de lluvia: {}", this.eventPeriod.getOrDefault(RAINY, 0L));
        LOGGER.info("Dia de mayor intesidad de lluvia: {}", isNull(this.maxIntensityRain) ? "" : this.maxIntensityRain.getDay());
        LOGGER.info("Cantidad de periodos de condiciones optimas de presion y temperatura: {}", this.eventPeriod.getOrDefault(OPTIMUM_WEATHER, 0L));
    }
}
