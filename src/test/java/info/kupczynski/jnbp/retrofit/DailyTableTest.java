package info.kupczynski.jnbp.retrofit;

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DailyTableTest {

    @Test
    public void shouldDeserializeFromTableAJson() throws IOException {
        String raw = "{\n" +
                "    \"table\": \"A\",\n" +
                "    \"no\": \"223/A/NBP/2016\",\n" +
                "    \"effectiveDate\": \"2016-11-18\",\n" +
                "    \"rates\": [\n" +
                "      {\n" +
                "        \"currency\": \"dolar amerykański\",\n" +
                "        \"code\": \"USD\",\n" +
                "        \"mid\": 4.1968\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        DailyTable expected = new DailyTable("A", "223/A/NBP/2016", LocalDate.of(2016, 11, 18),
                Arrays.asList(new CurrencyRate("USD", new BigDecimal("4.1968"), null, null)));

        DailyTable deserialized = JsonUtils.MAPPER.readValue(raw, DailyTable.class);

        assertThat(deserialized, is(expected));
    }

    @Test
    public void shouldDeserializeFromTableCJson() throws IOException {
        String raw = "{\n" +
                "    \"table\": \"C\",\n" +
                "    \"no\": \"223/C/NBP/2016\",\n" +
                "    \"tradingDate\": \"2016-11-17\",\n" +
                "    \"effectiveDate\": \"2016-11-18\",\n" +
                "    \"rates\": [\n" +
                "      {\n" +
                "        \"currency\": \"euro\",\n" +
                "        \"code\": \"EUR\",\n" +
                "        \"bid\": 4.4044,\n" +
                "        \"ask\": 4.4934\n" +
                "      }\n" +
                "    ]\n" +
                "}";
        DailyTable expected = new DailyTable("C", "223/C/NBP/2016", LocalDate.of(2016, 11, 18),
                Arrays.asList(new CurrencyRate("EUR", null, new BigDecimal("4.4044"), new BigDecimal("4.4934"))));

        DailyTable deserialized = JsonUtils.MAPPER.readValue(raw, DailyTable.class);

        assertThat(deserialized, is(expected));
    }
}
