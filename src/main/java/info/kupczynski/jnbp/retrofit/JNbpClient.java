package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.model.Currency;
import info.kupczynski.jnbp.model.CurrencyRates;
import info.kupczynski.jnbp.model.DailyRate;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Low level synchronous client for NBP Web API
 */
public class JNbpClient {

    private final ExchangeRatesApi exchangeRatesApi;

    /**
     * Create the NBP client
     */
    public JNbpClient() {
        this(ExchangeRatesApiFactory.create());
    }

    /**
     * Custom constructor allowing mocking dependencies in tests
     */
    protected JNbpClient(ExchangeRatesApi exchangeRatesApi) {
        this.exchangeRatesApi = exchangeRatesApi;
    }

    /**
     * Current exchange rate for {@link Currency}
     *
     * @param currency to get the current rate
     * @return {@link CurrencyRates} with a single, current {@link DailyRate}
     */
    public CurrencyRates current(Currency currency) {
        return ensureSuccess(
                exchangeRatesApi.current(currency.table.name(), currency.code)
        );
    }

    /**
     * Return {@code n} latest currency rates.
     *
     * Note that more than last 255 rates may cause an error.
     *
     * @param currency to get the latest rates for
     * @param n        number of latest rates to return
     * @return {@link CurrencyRates} with {@code n} latest {@link DailyRate}s
     */
    public CurrencyRates latest(Currency currency, int n) {
        return ensureSuccess(
                exchangeRatesApi.latest(currency.table.name(), currency.code, n)
        );
    }

    /**
     * Today exchange rate for {@link Currency} if exists
     *
     * @param currency to get the rate for
     * @return {@code Optional<CurrencyRates>} with a single, current {@link DailyRate}
     * or {@code Optional.EMPTY} if the rate doesn't exist for today
     */
    public Optional<CurrencyRates> today(Currency currency) {
        return successIfFound(
                exchangeRatesApi.today(currency.table.name(), currency.code)
        );
    }

    /**
     * Exchange rate for {@link Currency} for a given day if exists
     *
     * @param currency to get the rate for
     * @param day      to get the rate for
     * @return {@code Optional<CurrencyRates>} with a single {@link DailyRate}
     * or {@code Optional.EMPTY} if the rate doesn't exist for the given day
     */
    public Optional<CurrencyRates> day(Currency currency, LocalDate day) {
        return successIfFound(
                exchangeRatesApi.day(currency.table.name(), currency.code, day)
        );
    }

    /**
     * Exchange rates for {@link Currency} for a given range of days
     *
     * Note that a period longer than a year may cause an error.
     *
     * @param currency to get the rate for
     * @param from     range start day, inclusive
     * @param to       range end day, inclusive
     * @return {@link CurrencyRates} with {@link DailyRate}s for the given data range
     */
    public CurrencyRates range(Currency currency, LocalDate from, LocalDate to) {
        return ensureSuccess(
            exchangeRatesApi.range(currency.table.name(), currency.code, from, to)
        );
    }

    private <T> T ensureSuccess(Call<T> call) {
        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new JNbpClientException(e);
        }
        if (!response.isSuccessful()) {
            throw new UnsuccessfulResponseException(response.code(), response.message());
        }
        return response.body();
    }

    private <T> Optional<T> successIfFound(Call<T> call) {
        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new JNbpClientException(e);
        }
        if (response.code() == 404) {
            return Optional.empty();
        }
        if (!response.isSuccessful()) {
            throw new UnsuccessfulResponseException(response.code(), response.message());
        }
        return Optional.of(response.body());
    }

}
