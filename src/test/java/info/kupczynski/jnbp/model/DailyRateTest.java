package info.kupczynski.jnbp.model;

import info.kupczynski.jnbp.JsonTest;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DailyRateTest extends JsonTest {

    @Test
    public void shouldDeserializeFromJson() throws IOException {
        String rawRate = "{\"no\":\"214/A/NBP/2016\",\"effectiveDate\":\"2016-11-04\",\"mid\":4.3133}";
        DailyRate expected = new DailyRate("214/A/NBP/2016", LocalDate.of(2016, 11, 4), new BigDecimal("4.3133"));

        DailyRate deserializedDailyRate = MAPPER.readValue(rawRate, DailyRate.class);

        assertThat(deserializedDailyRate, is(expected));
    }
}
