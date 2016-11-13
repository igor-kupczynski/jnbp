package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.UnsuccessfulResponseException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Reactive utilities
 */
final class Rx {

    private Rx() {
        throw new AssertionError("Static utility");
    }

    /**
     * Convert retrofit {@link Call} to Rx {@link Observable}
     * @param call to convert
     * @param <T> type of response body
     * @return {@link Observable} with response payload
     */
    public static <T> Observable<T> observe(Call<T> call) {
        return Observable.<T>create(emitter -> {
            Callback<T> callback = new RxCallback<T>(emitter);
            call.enqueue(callback);
            emitter.setCancellable(call::cancel);
        });
    }



    private static class RxCallback<T> implements Callback<T> {
        private final ObservableEmitter<T> emitter;

        public RxCallback(ObservableEmitter<T> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.code() == 404) {
                // Nothing to emit on 404
            } else if (!response.isSuccessful()) {
                emitter.onError(new UnsuccessfulResponseException(response.code(), response.message()));
            } else if (!emitter.isDisposed()) {
                emitter.onNext(response.body());
            }

            emitter.onComplete();
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            emitter.onError(t);
        }
    }
}
