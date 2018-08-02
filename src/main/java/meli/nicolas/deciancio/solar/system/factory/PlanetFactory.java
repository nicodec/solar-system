package meli.nicolas.deciancio.solar.system.factory;

import meli.nicolas.deciancio.solar.system.model.Planet;

public class PlanetFactory {

    private PlanetFactory() {

    }

    /**
     * Builds {@link Planet} Ferengi
     * angularSpeed = 1 degree/day
     * distanceFromSun = 500km
     * rotationOrientation = clock wise
     * @return Planet Ferengi
     */
    public static Planet getFerengi() {
        return new Planet(-1.0, 0.0, 500.0);
    }

    /**
     * Builds {@link Planet} Betazoid
     * angularSpeed = 3 degree/day
     * distanceFromSun = 2000km
     * rotationOrientation = clock wise
     * @return Planet Betazoid
     */
    public static Planet getBetazoid() {
        return new Planet(-3.0, 0.0, 2000.0);
    }

    /**
     * Builds {@link Planet} Vulcan
     * angularSpeed = 5 degree/day
     * distanceFromSun = 1000km
     * rotationOrientation = counter-clock wise
     * @return Planet Vulcan
     */
    public static Planet getVulcan() {
        return new Planet(5.0, 0.0, 1000.0);
    }
}
