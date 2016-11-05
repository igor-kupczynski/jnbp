package info.kupczynski.jnbp;

import info.kupczynski.jnbp.model.CurrencyRateList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NbpWebApi {

    @GET("exchangerates/rates/{table}/{code}")
    Call<CurrencyRateList> current(@Path("table") String table, @Path("code") String code);

}
