package info.kupczynski.jnbp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.time.LocalDate;
import java.util.List;

interface ExchangeRatesApi {

    // Single currency queries
    String SINGLE_CURRENCY_ROOT = "exchangerates/rates";

    @GET(SINGLE_CURRENCY_ROOT + "/{table}/{code}")
    Call<CurrencyRates> current(@Path("table") String table, @Path("code") String code);

    @GET(SINGLE_CURRENCY_ROOT + "/{table}/{code}/last/{topCount}")
    Call<CurrencyRates> latest(@Path("table") String table, @Path("code") String code, @Path("topCount") int topCount);

    @GET(SINGLE_CURRENCY_ROOT + "/{table}/{code}/today")
    Call<CurrencyRates> today(@Path("table") String table, @Path("code") String code);

    @GET(SINGLE_CURRENCY_ROOT + "/{table}/{code}/{day}")
    Call<CurrencyRates> day(@Path("table") String table, @Path("code") String code, @Path("day") LocalDate day);

    @GET(SINGLE_CURRENCY_ROOT + "/{table}/{code}/{startDay}/{endDay}")
    Call<CurrencyRates> range(@Path("table") String table, @Path("code") String code,
                              @Path("startDay") LocalDate day, @Path("endDay") LocalDate endDay);


    // Table queries
    String TABLE_ROOT = "exchangerates/tables";

    @GET(TABLE_ROOT + "/{table}")
    Call<List<DailyTable>> current(@Path("table") String table);

    @GET(TABLE_ROOT + "/{table}/last/{topCount}")
    Call<List<DailyTable>> latest(@Path("table") String table, @Path("topCount") int topCount);

    @GET(TABLE_ROOT + "/{table}/today")
    Call<List<DailyTable>> today(@Path("table") String table);

    @GET(TABLE_ROOT + "/{table}/{day}")
    Call<List<DailyTable>> day(@Path("table") String table, @Path("day") LocalDate day);

    @GET(TABLE_ROOT + "/{table}/{startDay}/{endDay}")
    Call<List<DailyTable>> range(@Path("table") String table,
                                 @Path("startDay") LocalDate day, @Path("endDay") LocalDate endDay);

}
