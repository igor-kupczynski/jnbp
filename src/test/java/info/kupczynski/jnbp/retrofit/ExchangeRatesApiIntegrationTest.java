package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.model.CurrencyRates;
import info.kupczynski.jnbp.model.DailyRate;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ExchangeRatesApiIntegrationTest {

    private ExchangeRatesApi api;

    @Before
    public void setUp() {
        api = ExchangeRatesApiFactory.create();
    }

    @Test
    public void shouldLastExchangeRateSameAsCurrent() throws IOException {
        Call<CurrencyRates> current = api.current("A", "EUR");
        Call<CurrencyRates> latest = api.latest("A", "EUR", 1);

        assertThat(current.execute().body(), is(latest.execute().body()));
    }

    @Test
    public void shouldReturnValidExchangeRateForATable() throws IOException {
        List<DailyRate> rates = api.day("A", "EUR", LocalDate.of(2016, 11, 4)).execute().body().getRates();

        DailyRate expected = new DailyRate("214/A/NBP/2016", LocalDate.of(2016, 11, 4),
                new BigDecimal("4.3133"), null, null);
        assertThat(rates, contains(expected));
    }

    @Test
    public void shouldReturnValidExchangeRateForBTable() throws IOException {
        List<DailyRate> rates = api.day("B", "AFN", LocalDate.of(2016, 11, 2)).execute().body().getRates();

        DailyRate expected = new DailyRate("044/B/NBP/2016", LocalDate.of(2016, 11, 2),
                new BigDecimal("0.059043"), null, null);
        assertThat(rates, contains(expected));
    }

    @Test
    public void shouldReturnValidExchangeRateForCTable() throws IOException {
        List<DailyRate> rates = api.day("C", "EUR", LocalDate.of(2016, 11, 4)).execute().body().getRates();

        DailyRate expected = new DailyRate("214/C/NBP/2016", LocalDate.of(2016, 11, 4),
                null, new BigDecimal("4.2832"), new BigDecimal("4.3698"));
        assertThat(rates, contains(expected));
    }

    @Test
    public void shouldReturnNoDataForNonWorkingDay() throws IOException {
        LocalDate nonWorkingDay = LocalDate.of(2016, 11, 6);
        Response<CurrencyRates> response = api.day("A", "EUR", nonWorkingDay).execute();

        assertThat(response.code(), is(404));
    }

    @Test
    public void shouldReturnSeriesOfRates() throws IOException {
        CurrencyRates series = api.range("A", "EUR", LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5))
                .execute()
                .body();

        CurrencyRates expected = new CurrencyRates("A", "EUR", Arrays.asList(
            new DailyRate("212/A/NBP/2016", LocalDate.of(2016, 11, 2), new BigDecimal("4.3169"), null, null),
            new DailyRate("213/A/NBP/2016", LocalDate.of(2016, 11, 3), new BigDecimal("4.3238"), null, null),
            new DailyRate("214/A/NBP/2016", LocalDate.of(2016, 11, 4), new BigDecimal("4.3133"), null, null)
        ));
    }

    @Test
    public void shouldReturnBadRequestWhenSeriesTooLong() throws IOException {
        Response<CurrencyRates> response = api.range("A", "EUR", LocalDate.of(2015, 11, 1), LocalDate.of(2016, 11, 5))
                .execute();
        assertThat(response.code(), is(400));
        assertThat(response.message(), containsString("Limit of 367 days has been exceeded"));
    }
}
