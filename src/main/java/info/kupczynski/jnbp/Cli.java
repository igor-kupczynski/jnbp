package info.kupczynski.jnbp;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
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
import java.time.format.DateTimeFormatter;

public class Cli {

    private final JNbpClient client = JNbpClientFactory.create();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @Parameter(names = {"--start-date"}, description = "Grab the rates starting from this date. Defaults to 2002-01-01")
    private String startDate = "2002-01-01";

    @Parameter(names = {"--end-date"}, description = "Last date to grab the rates for. Defaults to today")
    private String endDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

    @Parameter(names = {"--table"}, description = "Which rate table to use. Defaults to 'A'")
    private String table = "A";

    @Parameter(names = {"--batch"}, description = "Use elasticsearch batch syntax. Defaults to false")
    private boolean batch = false;

    private Cli() {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static void main(String[] args) {
        Cli cli = new Cli();
        new JCommander(cli, args);
        cli.run();
    }

    private void run() {
        Observable<CurrencyDailyRate> range = client.range(
                CurrencyTable.valueOf(table),
                parseDate(startDate),
                parseDate(endDate));
        range.blockingForEach(rate -> {
            if (batch) {
                BatchOp op = BatchOp.index("rates", table, rate.rateId + "/" + rate.currency.code);
                System.out.println(mapper.writeValueAsString(op));
            }
            System.out.println(mapper.writeValueAsString(rate));
        });
    }

    private static LocalDate parseDate(String raw) {
        String[] parts = raw.split("-");
        return LocalDate.of(Integer.valueOf(parts[0]),
                Integer.valueOf(parts[1]),
                Integer.valueOf(parts[2]));
    }

    /**
     * Elasticsearch batch operation
     */
    private static class BatchOp {
        public final DocumentId index;

        private BatchOp(DocumentId index) {
            this.index = index;
        }

        public static BatchOp index(String index, String type, String id) {
            return new BatchOp(new DocumentId(index, type, id));
        }
    }

    /**
     * Elasticsearch document locator - index/type/id
     */
    private static class DocumentId {
        public final String _index;
        public final String _type;
        public final String _id;

        public DocumentId(String _index, String _type, String _id) {
            this._index = _index;
            this._type = _type;
            this._id = _id;
        }
    }
}
