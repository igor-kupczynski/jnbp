package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class JNbpClientBackedByRetrofit implements info.kupczynski.jnbp.api.JNbpClient {

    private static final long MAX_DAYS_PER_BATCH = 365;

    private final ExchangeRatesApi exchangeRatesApi =  ExchangeRatesApiFactory.create();

    JNbpClientBackedByRetrofit() {}

    @Override
    public Observable<CurrencyDailyRate> range(Currency currency, LocalDate start, LocalDate end) {
        Collection<DateRange> batches = DateRange.fromInclusive(start, end).split(MAX_DAYS_PER_BATCH);

        List<Observable<CurrencyDailyRate>> observables = batches.stream()
                .map(batch -> {
                    Call<CurrencyRates> call = exchangeRatesApi.range(currency.table.name(), currency.code, batch.from, batch.getLastIncludedDay());
                    return call2Observable(currency, call);
                })
                .collect(Collectors.toList());
        return Observable.concat(observables);
    }

    @Override
    public Single<CurrencyDailyRate> current(Currency currency) {
        return latest(currency, 1).firstOrError();
    }

    @Override
    public Observable<CurrencyDailyRate> latest(Currency currency, int n) {
        Call<CurrencyRates> call = exchangeRatesApi.latest(currency.table.name(), currency.code, n);
        return call2Observable(currency, call);
    }

    private static Observable<CurrencyDailyRate> call2Observable(Currency currency, Call<CurrencyRates> call) {
        return Rx.observe(call)
                .toObservable()
                .map(currencyRates -> currencyRates.getRates())
                .flatMap(Observable::fromIterable)
                .map(dailyRate -> dailyRate.toCurrencyDailyRate(currency));
    }
}
