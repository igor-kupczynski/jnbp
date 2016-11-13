package info.kupczynski.jnbp.retrofit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ExchangeRatesApiFactory {

    public static final String NBP_API_URL = "http://api.nbp.pl/api/";

    private static final int MAX_CONCURRENT_REQUESTS = 2;  // Let's be nice to the NBP API

    public static ExchangeRatesApi create() {


        // We need to provide our own executor service, as the default one is not bounded and is not `daemon`ized
        ExecutorService executorService = new ThreadPoolExecutor(0, MAX_CONCURRENT_REQUESTS, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Util.threadFactory("ExchangeRatesApi Dispatcher", true));

        Dispatcher dispatcher = new Dispatcher(executorService);
        dispatcher.setMaxRequests(MAX_CONCURRENT_REQUESTS);

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NBP_API_URL)
                .addConverterFactory(JacksonConverterFactory.create(JsonUtils.MAPPER))
                .client(client)
                .build();
        return retrofit.create(ExchangeRatesApi.class);
    }

}
