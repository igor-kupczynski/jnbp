package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.api.UnsuccessfulResponseException;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
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
     * Convert retrofit {@link Call} to Rx {@link Single}
     * @param call to convert
     * @param <T> type of response body
     * @return {@link Single} with response payload
     */
    public static <T> Single<T> observe(Call<T> call) {
        return Single.<T>create(emitter -> {
            Callback<T> callback = new RxCallback<T>(emitter);
            call.enqueue(callback);
            emitter.setCancellable(call::cancel);
        });
    }



    private static class RxCallback<T> implements Callback<T> {
        private final SingleEmitter<T> emitter;

        public RxCallback(SingleEmitter<T> emitter) {
            this.emitter = emitter;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (!response.isSuccessful()) {
                emitter.onError(new UnsuccessfulResponseException(response.code(), response.message()));
            } else {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(response.body());
                }
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            emitter.onError(t);
        }
    }
}
