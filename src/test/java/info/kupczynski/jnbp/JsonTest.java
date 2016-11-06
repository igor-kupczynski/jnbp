package info.kupczynski.jnbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonTest {
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }
}
