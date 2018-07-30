package meli.nicolas.deciancio.solar.system.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import meli.nicolas.deciancio.solar.system.model.ForecastEvent;

@Entity
@Table(name = "FORECAST")
public class Forecast {

    @Id
    @GeneratedValue
    @Column(name = "DAY")
    private Long day;

    @Column(name = "EVENT_TYPE")
    @Enumerated(EnumType.STRING)
    private ForecastEvent forecastEvent;

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public ForecastEvent getForecastEvent() {
        return forecastEvent;
    }

    public void setForecastEvent(ForecastEvent forecastEvent) {
        this.forecastEvent = forecastEvent;
    }
}
