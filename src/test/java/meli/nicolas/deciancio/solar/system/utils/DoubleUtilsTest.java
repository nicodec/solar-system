package meli.nicolas.deciancio.solar.system.utils;

import static meli.nicolas.deciancio.solar.system.utils.DoubleUtils.equalsDouble;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class DoubleUtilsTest {

    public void testEqualsDouble() {
        assertTrue(equalsDouble(14.000005, 14.000006));
        assertFalse(equalsDouble(14.005, 14.006));
    }
}