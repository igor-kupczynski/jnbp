package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.model.Currency;
import info.kupczynski.jnbp.model.CurrencyRates;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JNbpClientTest {

    private final static Currency CURR = Currency.EUR_A;
    private final static String TABLE = CURR.table.name();
    private final static String CODE = CURR.code;
    private final static LocalDate DAY = LocalDate.of(2016, 11, 6);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ExchangeRatesApi exchangeRatesApi;

    private Response<CurrencyRates> okResponse = Response.success(new CurrencyRates(TABLE, CODE, Collections.EMPTY_LIST));

    private Response<CurrencyRates> notFound = Response.error(404,
            ResponseBody.create(MediaType.parse("text/plain"), "Not Found"));

    @Mock
    private Call<CurrencyRates> call;

    private JNbpClient client;

    @Before
    public void setUp() throws IOException {
        client = new JNbpClient(exchangeRatesApi);
    }

    @Test
    public void shouldReturnCurrentRate() throws IOException {
        when(call.execute()).thenReturn(okResponse);
        when(exchangeRatesApi.current(TABLE, CODE)).thenReturn(call);

        client.current(CURR);

        verify(exchangeRatesApi).current(TABLE, CODE);
    }

    @Test
    public void shouldRaiseExceptionOnErrorResponse() throws IOException {
        when(call.execute()).thenReturn(notFound);
        when(exchangeRatesApi.current(TABLE, CODE)).thenReturn(call);
        expectedException.expect(JNbpClientException.class);

        client.current(CURR);
    }

    @Test
    public void shouldReturnLatestRates() throws IOException {
        int n = 17;
        when(call.execute()).thenReturn(okResponse);
        when(exchangeRatesApi.latest(TABLE, CODE, n)).thenReturn(call);

        client.latest(CURR, n);

        verify(exchangeRatesApi).latest(TABLE, CODE, n);
    }

    @Test
    public void shouldReturnTodayRate() throws IOException {
        when(call.execute()).thenReturn(okResponse);
        when(exchangeRatesApi.today(TABLE, CODE)).thenReturn(call);

        client.today(CURR);

        verify(exchangeRatesApi).today(TABLE, CODE);
    }

    @Test
    public void shouldReturnEmptyIfRateForTodayNotExists() throws IOException {
        when(call.execute()).thenReturn(notFound);
        when(exchangeRatesApi.today(TABLE, CODE)).thenReturn(call);

        Optional<CurrencyRates> result = client.today(CURR);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void shouldReturnRateForDay() throws IOException {
        when(call.execute()).thenReturn(okResponse);
        when(exchangeRatesApi.day(TABLE, CODE, DAY)).thenReturn(call);

        client.day(CURR, DAY);

        verify(exchangeRatesApi).day(TABLE, CODE, DAY);
    }

    @Test
    public void shouldReturnEmptyIfRateForDayNotExists() throws IOException {
        when(call.execute()).thenReturn(notFound);
        when(exchangeRatesApi.day(TABLE, CODE, DAY)).thenReturn(call);

        Optional<CurrencyRates> result = client.day(CURR, DAY);

        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void shouldReturnRateForDateRange() throws IOException {
        when(call.execute()).thenReturn(okResponse);
        when(exchangeRatesApi.range(TABLE, CODE, DAY, DAY)).thenReturn(call);

        client.range(CURR, DAY, DAY);

        verify(exchangeRatesApi).range(TABLE, CODE, DAY, DAY);
    }

}
