package info.kupczynski.jnbp;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class Demo {

    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nbp.pl/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        NbpWebApi nbp = retrofit.create(NbpWebApi.class);

        System.out.println(nbp.current("A", "EUR").execute().body());
    }
}
