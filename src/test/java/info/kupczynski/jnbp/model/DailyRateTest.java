package info.kupczynski.jnbp.model;

import info.kupczynski.jnbp.JsonUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DailyRateTest {

    @Test
    public void shouldDeserializeFromMidMarketJson() throws IOException {
        String rawRate = "{\"no\":\"214/A/NBP/2016\",\"effectiveDate\":\"2016-11-04\",\"mid\":4.3133}";
        DailyRate expected = new DailyRate("214/A/NBP/2016", LocalDate.of(2016, 11, 4),
                new BigDecimal("4.3133"), null, null);

        DailyRate deserializedDailyRate = JsonUtils.MAPPER.readValue(rawRate, DailyRate.class);

        assertThat(deserializedDailyRate, is(expected));
    }

    @Test
    public void shouldDeserializeFromBidAskJson() throws IOException {
        String rawRate = "{\"no\":\"214/C/NBP/2016\",\"effectiveDate\":\"2016-11-04\",\"bid\":4.2832,\"ask\":4.3698}}";
        DailyRate expected = new DailyRate("214/C/NBP/2016", LocalDate.of(2016, 11, 4),
                null, new BigDecimal("4.2832"), new BigDecimal("4.3698"));

        DailyRate deserializedDailyRate = JsonUtils.MAPPER.readValue(rawRate, DailyRate.class);

        assertThat(deserializedDailyRate, is(expected));
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireARate() throws IOException {
        new DailyRate("214/C/NBP/2016", LocalDate.of(2016, 11, 4),
                null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireAskIfBidProvided() throws IOException {
        new DailyRate("214/C/NBP/2016", LocalDate.of(2016, 11, 4),
                null, new BigDecimal("4.2832"), null);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireBidIfAslProvided() throws IOException {
        new DailyRate("214/C/NBP/2016", LocalDate.of(2016, 11, 4),
                null, null, new BigDecimal("4.3698"));
    }
}
