package info.kupczynski.jnbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class Demo {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws IOException {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.nbp.pl/api/")
                .addConverterFactory(JacksonConverterFactory.create(MAPPER))
                .build();

        NbpWebApi nbp = retrofit.create(NbpWebApi.class);

        System.out.println(nbp.current("A", "EUR").execute().body());
    }
}
