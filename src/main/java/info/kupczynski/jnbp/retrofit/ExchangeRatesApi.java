package info.kupczynski.jnbp.retrofit;

import info.kupczynski.jnbp.model.CurrencyRates;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.time.LocalDate;

interface ExchangeRatesApi {

    String EXCHANGE_RATES_ROOT = "exchangerates/rates";

    @GET(EXCHANGE_RATES_ROOT + "/{table}/{code}")
    Call<CurrencyRates> current(@Path("table") String table, @Path("code") String code);

    @GET(EXCHANGE_RATES_ROOT + "/{table}/{code}/last/{topCount}")
    Call<CurrencyRates> latest(@Path("table") String table, @Path("code") String code, @Path("topCount") int topCount);

    @GET(EXCHANGE_RATES_ROOT + "/{table}/{code}/today")
    Call<CurrencyRates> today(@Path("table") String table, @Path("code") String code);

    @GET(EXCHANGE_RATES_ROOT + "/{table}/{code}/{day}")
    Call<CurrencyRates> day(@Path("table") String table, @Path("code") String code, @Path("day") LocalDate day);

    @GET(EXCHANGE_RATES_ROOT + "/{table}/{code}/{startDay}/{endDay}")
    Call<CurrencyRates> range(@Path("table") String table, @Path("code") String code,
                              @Path("startDay") LocalDate day, @Path("endDay") LocalDate endDay);
}
