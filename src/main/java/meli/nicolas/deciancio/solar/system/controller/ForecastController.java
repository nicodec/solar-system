package meli.nicolas.deciancio.solar.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import meli.nicolas.deciancio.solar.system.dao.ForecastDao;
import meli.nicolas.deciancio.solar.system.model.entity.Forecast;

@RestController
@RequestMapping(value = "/forecast")
public class ForecastController {

    @Autowired
    private ForecastDao forecastDao;

    /**
     * Gets the forecast for a specific day
     * @param day
     * @return forecast of specified day
     */
    @RequestMapping(value = "/weather")
    public Forecast getForecast(@RequestParam Long day) {
        return this.forecastDao.findByDay(day).orElseGet(Forecast::new);
    }
}
