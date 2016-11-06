package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import info.kupczynski.jnbp.model.CurrencyRates;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

/**
 * Low level synchronous client for NBP Web API
 */
public class JNbpClient {

    public static final String NBP_API_URL = "http://api.nbp.pl/api/";

    private static final ObjectMapper MAPPER = new ObjectMapper()
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());

    private final ExchangeRatesApi exchangeRatesApi;

    /**
     * Create the NBP client
     */
    public JNbpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NBP_API_URL)
                .addConverterFactory(JacksonConverterFactory.create(MAPPER))
                .build();
        this.exchangeRatesApi = retrofit.create(ExchangeRatesApi.class);
    }

    /**
     * Custom constructor to allow dependency injection in tests
     */
    private JNbpClient(ExchangeRatesApi exchangeRatesApi) {
        this.exchangeRatesApi = exchangeRatesApi;
    }

    public CurrencyRates current(String table, String code) {
        try {
            return exchangeRatesApi.current(table, code).execute().body();
        } catch (IOException e) {
            throw new JNbpClientException(e);
        }
    }

}
