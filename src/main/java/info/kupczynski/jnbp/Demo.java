package info.kupczynski.jnbp;

import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.JNbpClient;
import info.kupczynski.jnbp.retrofit.JNbpClientFactory;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Demo {

    public static void main(String[] args) throws IOException {
        new Demo().run();
    }

    private JNbpClient client = JNbpClientFactory.create();

    private void run() {
        LocalDate monthAgo = LocalDate.now().minus(1, ChronoUnit.MONTHS);
        LocalDate weekAgo = LocalDate.now().minus(1, ChronoUnit.WEEKS);
        LocalDate today = LocalDate.now();


        System.out.println("JNbpClient demo");
        System.out.println("===============");
        System.out.println();

        banner("Single currency, Euro, Table A");
        demo("Current rate", client.current(Currency.EUR_A));
        demo("Latest 3 rates", client.latest(Currency.EUR_A, 3));
        demo(String.format("Rates from %s to %s", weekAgo, today), client.range(Currency.EUR_A, weekAgo, today));

        banner("Bid ask rates are stored in table C");
        demo("Latest 3 rates", client.latest(Currency.EUR_C, 3));

        banner("Less popular currencies are stored in table B and updated weekly");
        demo(String.format("Rates from %s to %s", monthAgo, today), client.range(Currency.KES_B, monthAgo, today));
    }

    private static void banner(String title) {
        System.out.println(title);
        System.out.println(new String(new char[title.length()]).replace("\0", "-"));
        System.out.println();
    }

    private static void demo(String title, Observable<CurrencyDailyRate> rates) {
        System.out.println("### " + title);
        rates.blockingForEach(rate -> System.out.println("* " + rate));
        System.out.println();
    }

    private static void demo(String title, Single<CurrencyDailyRate> rate) {
        System.out.println("### " + title);
        System.out.println("* " + rate.blockingGet());
        System.out.println();
    }
}
