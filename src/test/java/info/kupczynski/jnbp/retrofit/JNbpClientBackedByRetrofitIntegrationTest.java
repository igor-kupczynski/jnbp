package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.CurrencyDailyRate;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static info.kupczynski.jnbp.api.Currency.EUR_A;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class JNbpClientBackedByRetrofitIntegrationTest {

    private JNbpClientBackedByRetrofit client;

    @Before
    public void setUp() throws IOException {
        client = new JNbpClientBackedByRetrofit();
    }

    @Test
    public void shouldReturnCorrectHistoricalRatesForTableA() {
        client.range(EUR_A, LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5))
                .test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertResult(
                        new CurrencyDailyRate(EUR_A, "212/A/NBP/2016", LocalDate.of(2016, 11, 2), new BigDecimal("4.3169"), null, null),
                        new CurrencyDailyRate(EUR_A, "213/A/NBP/2016", LocalDate.of(2016, 11, 3), new BigDecimal("4.3238"), null, null),
                        new CurrencyDailyRate(EUR_A, "214/A/NBP/2016", LocalDate.of(2016, 11, 4), new BigDecimal("4.3133"), null, null)
                );

    }

    @Test
    public void shouldSuccessfulReturnCurrentRate() {
        client.current(EUR_A)
                .test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertComplete()
                .assertValueCount(1)
                .assertNoErrors();
    }

    @Test
    public void shouldLatestBeSameAsLastRange() {
        // We need to grab more, as there may be a long weekend
        List<CurrencyDailyRate> lastTenDays = client.range(EUR_A, LocalDate.now().minusDays(10), LocalDate.now())
                .test()
                .awaitDone(5, TimeUnit.SECONDS)
                .values();

        List<CurrencyDailyRate> lastThreeRates = client.latest(EUR_A, 3).test()
                .awaitDone(5, TimeUnit.SECONDS)
                .values();

        assertThat(lastThreeElements(lastTenDays), is(lastThreeRates));
    }

    private List<CurrencyDailyRate> lastThreeElements(List<CurrencyDailyRate> lastTenDays) {
        return lastTenDays.subList(lastTenDays.size() - 3, lastTenDays.size());
    }

    @Test
    public void shouldAllowLongRanges() {
        client.range(EUR_A, LocalDate.of(2015, 11, 13), LocalDate.of(2016, 11, 13))
                .test()
                .awaitDone(5, TimeUnit.SECONDS)
                .assertNoErrors();
    }
}
