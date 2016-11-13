package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.JNbpClientException;
import info.kupczynski.jnbp.api.UnsuccessfulResponseException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    // TODO: Implement generic retrofit <-> rxjava integration, instead of this ad hoc method
    private static Observable<CurrencyDailyRate> call2Observable(Currency currency, Call<CurrencyRates> call) {
        Observable<DailyRate> callObservable = Observable.create(emitter -> {
            Callback<CurrencyRates> callback = new CurrencyRatesCallback(emitter);
            call.enqueue(callback);
            emitter.setCancellable(call::cancel);
        });

        return callObservable.map(dailyRate -> dailyRate.toCurrencyDailyRate(currency));
    }

    private static class CurrencyRatesCallback implements Callback<CurrencyRates> {
        private final ObservableEmitter<DailyRate> emitter;

        public CurrencyRatesCallback(ObservableEmitter<DailyRate> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onResponse(Call<CurrencyRates> call, Response<CurrencyRates> response) {
            if (!response.isSuccessful()) {
                emitter.onError(new UnsuccessfulResponseException(response.code(), response.message()));
            } else {
                for (DailyRate rate : response.body().getRates()) {
                    if (emitter.isDisposed()) {
                        break;
                    }
                    emitter.onNext(rate);
                }
            }
            emitter.onComplete();
        }

        @Override
        public void onFailure(Call<CurrencyRates> call, Throwable t) {
            emitter.onError(new JNbpClientException(t));
        }
    }
}
