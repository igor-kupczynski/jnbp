package info.kupczynski.jnbp.api;

import io.reactivex.Observable;
import io.reactivex.Single;

import java.time.LocalDate;

/**
 * Java client for NBP Web API.
 *
 * @see <a href="http://api.nbp.pl/en.html">NBP Web API documentation</a>.
 */
public interface JNbpClient {

    /**
     * Fetch all the daily exchange rates from the given table between two dates, inclusive.
     *
     * @param table to fetch the rates for
     * @param start return the rates starting from this date
     * @param end   return the rates ending at this date
     * @return sequence of {@link CurrencyDailyRate} between {@code [start, end]} dates
     */
    Observable<CurrencyDailyRate> range(CurrencyTable table, LocalDate start, LocalDate end);

    /**
     * Fetch the current, i.e. last published, exchange rates for the given table.
     *
     * @param table to fetch the current rate for
     * @return current {@link CurrencyDailyRate}
     */
    Observable<CurrencyDailyRate> current(CurrencyTable table);

    /**
     * Fetch the n-latest daily exchange rates for the given table
     *
     * @param table to fetch the rates for
     * @param n number of rates to fetch
     * @return sequence of {@code n} last {@link CurrencyDailyRate}
     */
    Observable<CurrencyDailyRate> latest(CurrencyTable table, int n);

    /**
     * Fetch all the daily exchange rates for the given currency between two dates, inclusive.
     *
     * @param currency to fetch the rates for
     * @param start    return the rates starting from this date
     * @param end      return the rates ending at this date
     * @return sequence of {@link CurrencyDailyRate} between {@code [start, end]} dates
     */
    Observable<CurrencyDailyRate> range(Currency currency, LocalDate start, LocalDate end);

    /**
     * Fetch the current, i.e. last published, exchange rate for the given currency.
     *
     * @param currency to fetch the current rate for
     * @return current {@link CurrencyDailyRate}
     */
    Single<CurrencyDailyRate> current(Currency currency);

    /**
     * Fetch the n-latest daily exchange rates for the given currency
     *
     * @param currency to fetch the rates for
     * @param n        number of rates to fetch
     * @return sequence of {@code n} last {@link CurrencyDailyRate}
     */
    Observable<CurrencyDailyRate> latest(Currency currency, int n);
}
