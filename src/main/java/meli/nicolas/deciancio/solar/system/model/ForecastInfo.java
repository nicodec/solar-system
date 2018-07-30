package meli.nicolas.deciancio.solar.system.model;

public class ForecastInfo {

    private Long day;
    private ForecastEvent forecastEvent;
    private Double trianglePerimeter;

    public ForecastInfo(ForecastEvent forecastEvent) {
        this.forecastEvent = forecastEvent;
        this.trianglePerimeter = 0.0;
    }

    public ForecastInfo(ForecastEvent forecastEvent, Double trianglePerimeter) {
        this.forecastEvent = forecastEvent;
        this.trianglePerimeter = trianglePerimeter;
    }

    public ForecastEvent getForecastEvent() {
        return forecastEvent;
    }

    public void setForecastEvent(ForecastEvent forecastEvent) {
        this.forecastEvent = forecastEvent;
    }

    public Double getTrianglePerimeter() {
        return trianglePerimeter;
    }

    public void setTrianglePerimeter(Double trianglePerimeter) {
        this.trianglePerimeter = trianglePerimeter;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }
}
