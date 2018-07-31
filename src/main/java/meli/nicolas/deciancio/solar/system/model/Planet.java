package meli.nicolas.deciancio.solar.system.model;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import javafx.geometry.Point2D;

public class Planet {

    private Double angularSpeed;
    private Double anglePosition;
    private Double distanceFromSun;

    public Planet(Double angularSpeed, Double anglePosition, Double distanceFromSun) {
        this.angularSpeed = angularSpeed;
        this.anglePosition = anglePosition;
        this.distanceFromSun = distanceFromSun;
    }

    public Double getAngularSpeed() {
        return angularSpeed;
    }

    public Double distance(Planet planet) {
        return this.getCartesianPosition().distance(planet.getCartesianPosition());
    }

    public Point2D getCartesianPosition() {
        final Double xPos = cos(toRadians(this.anglePosition)) * this.distanceFromSun;
        final Double yPos = sin(toRadians(this.anglePosition)) * this.distanceFromSun;
        return new Point2D(xPos, yPos);
    }

    public void move() {
        this.anglePosition = this.anglePosition + this.angularSpeed;
        // esto lo hago porque al pasar los 360 se introduce un minimo error en decimales al calcular las coordenadas cartesianas
        if (abs(this.anglePosition) >= 360.0) {
            if (this.anglePosition > 0.0) {
                this.anglePosition -= 360.0;
            } else {
                this.anglePosition += 360.0;
            }
        }
    }
}
