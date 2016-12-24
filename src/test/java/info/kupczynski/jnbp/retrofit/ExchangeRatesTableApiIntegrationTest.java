package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.CurrencyTable;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ExchangeRatesTableApiIntegrationTest {

    private ExchangeRatesApi api;

    @Before
    public void setUp() {
        api = ExchangeRatesApiFactory.create();
    }

    @Test
    public void shouldLastExchangeRateSameAsCurrent() throws IOException {
        Call<List<DailyTable>> current = api.current("A");
        Call<List<DailyTable>> latest = api.latest("A", 1);

        assertThat(current.execute().body(), is(latest.execute().body()));
    }

    @Test
    public void shouldReturnValidExchangeRateForATable() throws IOException {
        DailyTable rates = api.day("A", LocalDate.of(2016, 11, 4)).execute().body().get(0);

        assertThat(rates.table, is(CurrencyTable.A));
        assertThat(rates.rateId, is("214/A/NBP/2016"));
        assertThat(rates.effectiveDate, is(LocalDate.of(2016, 11, 4)));
        assertThat(rates.rates, hasItem(new CurrencyRate("EUR", new BigDecimal("4.3133"), null, null)));
    }

    @Test
    public void shouldReturnValidExchangeRateForBTable() throws IOException {
        DailyTable rates = api.day("B", LocalDate.of(2016, 11, 2)).execute().body().get(0);

        assertThat(rates.table, is(CurrencyTable.B));
        assertThat(rates.rateId, is("044/B/NBP/2016"));
        assertThat(rates.effectiveDate, is(LocalDate.of(2016, 11, 2)));
        assertThat(rates.rates, hasItem(new CurrencyRate("AFN", new BigDecimal("0.059043"), null, null)));
    }

    @Test
    public void shouldReturnValidExchangeRateForCTable() throws IOException {
        DailyTable rates = api.day("C", LocalDate.of(2016, 11, 4)).execute().body().get(0);

        assertThat(rates.table, is(CurrencyTable.C));
        assertThat(rates.rateId, is("214/C/NBP/2016"));
        assertThat(rates.effectiveDate, is(LocalDate.of(2016, 11, 4)));
        assertThat(rates.rates, hasItem(new CurrencyRate("EUR", null, new BigDecimal("4.2832"), new BigDecimal("4.3698"))));
    }

    @Test
    public void shouldReturnNoDataForNonWorkingDay() throws IOException {
        LocalDate nonWorkingDay = LocalDate.of(2016, 11, 6);
        Response<List<DailyTable>> response = api.day("A", nonWorkingDay).execute();

        assertThat(response.code(), is(404));
    }

    @Test
    public void shouldReturnSeriesOfRates() throws IOException {
        List<DailyTable> series = api.range("A", LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5))
                .execute()
                .body();

        assertThat(series, hasItems(
                api.day("A", LocalDate.of(2016, 11, 2)).execute().body().get(0),
                api.day("A", LocalDate.of(2016, 11, 3)).execute().body().get(0),
                api.day("A", LocalDate.of(2016, 11, 4)).execute().body().get(0)
        ));
    }

    @Test
    public void shouldReturnBadRequestWhenSeriesTooLong() throws IOException {
        Response<List<DailyTable>> response = api.range("A", LocalDate.of(2015, 11, 1), LocalDate.of(2016, 11, 5))
                .execute();
        assertThat(response.code(), is(400));
        assertThat(response.message(), containsString("Przekroczony limit 93 dni / Limit of 93 days has been exceeded"));
    }
}
