package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.CurrencyTable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

class JNbpClientBackedByRetrofit implements info.kupczynski.jnbp.api.JNbpClient {

    private static final long MAX_DAYS_PER_BATCH = 365;

    private final ExchangeRatesApi exchangeRatesApi =  ExchangeRatesApiFactory.create();

    JNbpClientBackedByRetrofit() {}

    @Override
    public Observable<CurrencyDailyRate> range(CurrencyTable table, LocalDate start, LocalDate end) {
        Call<List<DailyTable>> call = exchangeRatesApi.range(table.name(), start, end);
        return call2Observable(call);
    }

    @Override
    public Observable<CurrencyDailyRate> current(CurrencyTable table) {
        return latest(table, 1);
    }

    @Override
    public Observable<CurrencyDailyRate> latest(CurrencyTable table, int n) {
        Call<List<DailyTable>> call = exchangeRatesApi.latest(table.name(), n);
        return call2Observable(call);
    }

    private Observable<CurrencyDailyRate> call2Observable(Call<List<DailyTable>> call) {
        return Rx.observe(call)
                .flatMap(Observable::fromIterable)
                .flatMap(dailyTable -> Observable.fromIterable(dailyTable.toCurrencyDailyRates()));
    }


    @Override
    public Observable<CurrencyDailyRate> range(Currency currency, LocalDate start, LocalDate end) {
        Collection<DateRange> batches = DateRange.fromInclusive(start, end).split(MAX_DAYS_PER_BATCH);

        return Observable.fromIterable(batches)
                .map(batch -> exchangeRatesApi.range(currency.table.name(), currency.code, batch.from, batch.getLastIncludedDay()))
                .concatMap(call -> call2Observable(currency, call));
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
                .map(currencyRates -> currencyRates.rates)
                .flatMap(Observable::fromIterable)
                .map(dailyRate -> dailyRate.toCurrencyDailyRate(currency));
    }
}
