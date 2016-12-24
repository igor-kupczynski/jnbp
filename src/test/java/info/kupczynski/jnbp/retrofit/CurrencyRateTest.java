package info.kupczynski.jnbp.retrofit;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static info.kupczynski.jnbp.retrofit.JsonUtils.MAPPER;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CurrencyRateTest {

    @Test
    public void shouldDeserializeFromTableAJson() throws IOException {
        String raw = "{\n" +
                "        \"currency\": \"jen (Japonia)\",\n" +
                "        \"code\": \"JPY\",\n" +
                "        \"mid\": 0.037925\n" +
                "      }";
        CurrencyRate expected = new CurrencyRate("JPY", new BigDecimal("0.037925"), null, null);

        CurrencyRate deserialized = MAPPER.readValue(raw, CurrencyRate.class);

        assertThat(deserialized, is(expected));
    }

    @Test
    public void shouldDeserializeFromTableCJson() throws IOException {
        String raw = "{\n" +
                "        \"currency\": \"dolar amerykański\",\n" +
                "        \"code\": \"USD\",\n" +
                "        \"bid\": 4.121,\n" +
                "        \"ask\": 4.2042\n" +
                "      }";
        CurrencyRate expected = new CurrencyRate("USD", null, new BigDecimal("4.121"), new BigDecimal("4.2042"));

        CurrencyRate deserialized = MAPPER.readValue(raw, CurrencyRate.class);

        assertThat(deserialized, is(expected));
    }
}
