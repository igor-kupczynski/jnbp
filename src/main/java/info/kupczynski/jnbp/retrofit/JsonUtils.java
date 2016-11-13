package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Ugly static utility, at least package-private
 */
final class JsonUtils {

    private JsonUtils() {
        throw new AssertionError("static utility");
    }

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
}
