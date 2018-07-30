package meli.nicolas.deciancio.solar.system.factory;

import meli.nicolas.deciancio.solar.system.model.Planet;

public class PlanteFactory {

    public static Planet getFerengi() {
        return new Planet(-1.0, 0.0, 500.0);
    }

    public static Planet getBetazoid() {
        return new Planet(-3.0, 0.0, 2000.0);
    }

    public static Planet getVulcan() {
        return new Planet(5.0, 0.0, 1000.0);
    }
}
