package meli.nicolas.deciancio.solar.system.utils;

import static java.lang.StrictMath.abs;

public class DoubleUtils {

    /**
     * Compares two double values for equality with a precision of 0.0001
     * @param one a double value
     * @param two a double value
     * @return true if equals, false other case
     */
    public static boolean equalsDouble(Double one, Double two) {
        final Double d1 = one != null ? one : 0.0;
        final Double d2 = two != null ? two : 0.0;

        return abs(d1 - d2) < 0.0001;
    }
}
