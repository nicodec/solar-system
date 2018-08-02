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

    /**
     *Returns the euclidean distance between this and other planet
     * @param planet other planet
     * @return distance
     */
    public Double distance(Planet planet) {
        return this.getCartesianPosition().distance(planet.getCartesianPosition());
    }

    /**
     * Returns the position of the planet expressed as cartesian coordinates.
     *
     * x = cos({@link #anglePosition}) * {@link #distanceFromSun}
     * @return position represented as cartesian coordinates
     */
    public Point2D getCartesianPosition() {
        final Double xPos = cos(toRadians(this.anglePosition)) * this.distanceFromSun;
        final Double yPos = sin(toRadians(this.anglePosition)) * this.distanceFromSun;
        return new Point2D(xPos, yPos);
    }

    /**
     * Move this planet {@link #anglePosition} to the next position the number of units represented by {@link #angularSpeed}.
     */
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

    public Double getAnglePosition() {
        return anglePosition;
    }

    public void setAnglePosition(Double anglePosition) {
        this.anglePosition = anglePosition;
    }

    public Double getDistanceFromSun() {
        return distanceFromSun;
    }
}
