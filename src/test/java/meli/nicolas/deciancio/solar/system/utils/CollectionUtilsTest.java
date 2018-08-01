package meli.nicolas.deciancio.solar.system.utils;

import static meli.nicolas.deciancio.solar.system.utils.CollectionUtils.safeStream;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

@Test
public class CollectionUtilsTest {


    public void testSafeStream() {
        assertNotNull(safeStream(null));
    }
}