package meli.nicolas.deciancio.solar.system.utils;

import java.util.Collection;
import java.util.stream.Stream;

public class CollectionUtils {

    /**
     * Safe null stream generator
     * @param list
     * @return a stream of {@param list} or an empty stream if null
     */
    public static <T> Stream<T> safeStream(Collection<T> list) {
        return list != null ? list.stream() : Stream.empty();
    }
}
