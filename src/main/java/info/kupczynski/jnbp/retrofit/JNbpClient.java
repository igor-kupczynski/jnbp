package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.model.CurrencyRates;

import java.io.IOException;

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
