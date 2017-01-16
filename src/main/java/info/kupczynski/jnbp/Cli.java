package info.kupczynski.jnbp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.CurrencyTable;
import info.kupczynski.jnbp.api.JNbpClient;
import info.kupczynski.jnbp.retrofit.JNbpClientFactory;
import io.reactivex.Observable;

import java.time.LocalDate;

public class Cli {

    private final JNbpClient client = JNbpClientFactory.create();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    private Cli() {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static void main(String[] args) {
        Cli cli = new Cli();
        cli.run(CurrencyTable.valueOf(args[0]), parseDate(args[1]), parseDate(args[2]));
    }

    private void run(CurrencyTable table, LocalDate from, LocalDate to) {
        Observable<CurrencyDailyRate> range = client.range(table, from, to);
        range.blockingForEach(rate -> {
            String line = mapper.writeValueAsString(rate);
            System.out.println(line);
        });
    }

    private static LocalDate parseDate(String raw) {
        String[] parts = raw.split("-");
        return LocalDate.of(Integer.valueOf(parts[0]),
                Integer.valueOf(parts[1]),
                Integer.valueOf(parts[2]));
    }
}
