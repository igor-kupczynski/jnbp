package info.kupczynski.jnbp.model;

import info.kupczynski.jnbp.JsonTest;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CurrencyRatesTest extends JsonTest {

    @Test
    public void shouldDeserializeFromJson() throws IOException {
        String raw = "{\"table\":\"A\",\"currency\":\"euro\",\"code\":\"EUR\",\"rates\":" +
                "[{\"no\":\"214/A/NBP/2016\",\"effectiveDate\":\"2016-11-04\",\"mid\":4.3133}]}";
        CurrencyRates expected = new CurrencyRates("A", "euro", "EUR", Arrays.asList(
                new DailyRate("214/A/NBP/2016", LocalDate.of(2016, 11, 04), new BigDecimal("4.3133"))
        ));

        CurrencyRates deserialized = MAPPER.readValue(raw, CurrencyRates.class);

        assertThat(deserialized, is(expected));
    }
}