package info.kupczynski.jnbp;

import info.kupczynski.jnbp.model.CurrencyRates;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface NbpWebApi {

    @GET("exchangerates/rates/{table}/{code}")
    Call<CurrencyRates> current(@Path("table") String table, @Path("code") String code);

}
