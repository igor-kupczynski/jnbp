package info.kupczynski.jnbp;

import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.JNbpClient;
import info.kupczynski.jnbp.retrofit.JNbpClientFactory;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.IOException;
import java.time.LocalDate;

public class Demo {

    public static void main(String[] args) throws IOException {
        new Demo().run();
    }

    private JNbpClient client = JNbpClientFactory.create();

    private void run() {
        System.out.println("JNbpClient demo");
        System.out.println("===============");
        System.out.println();

        banner("Single currency, Euro, Table A");
        demo("Current rate", client.current(Currency.EUR_A));
        demo("Latest 3 rates", client.latest(Currency.EUR_A, 3));
        demo("Rates from 2016-11-01 to 2016-11-05", client.range(Currency.EUR_A, LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5)));

        banner("Bid ask rates are stored in table C");
        demo("Latest 3 rates", client.latest(Currency.EUR_C, 3));

        banner("Less popular currencies are stored in table B and updated weekly");
        demo("Rates from 2016-10-01 to 2016-11-01", client.range(Currency.KES_B, LocalDate.of(2016, 10, 1), LocalDate.of(2016, 11, 1)));
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
