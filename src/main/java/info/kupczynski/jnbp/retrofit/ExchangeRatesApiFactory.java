package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.JsonUtils;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

class ExchangeRatesApiFactory {

    public static final String NBP_API_URL = "http://api.nbp.pl/api/";

    public static ExchangeRatesApi create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NBP_API_URL)
                .addConverterFactory(JacksonConverterFactory.create(JsonUtils.MAPPER))
                .build();
        return retrofit.create(ExchangeRatesApi.class);
    }

}
