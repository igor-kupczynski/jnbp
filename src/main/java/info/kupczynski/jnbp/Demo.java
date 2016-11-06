package info.kupczynski.jnbp;

import info.kupczynski.jnbp.model.Currency;
import info.kupczynski.jnbp.retrofit.JNbpClient;

import java.io.IOException;
import java.time.LocalDate;

public class Demo {

    public static void main(String[] args) throws IOException {
        JNbpClient client = new JNbpClient();

        System.out.println("# Single currency rates demo - EUR, table A");
        System.out.println();

        System.out.println("## Current rate");
        System.out.println(client.current(Currency.EUR_A));
        System.out.println("");

        System.out.println("## Latest 3 rates");
        System.out.println(client.latest(Currency.EUR_A, 3));
        System.out.println("");

        System.out.println("## Rate for today (may not exist)");
        System.out.println(client.today(Currency.EUR_A));
        System.out.println("");

        System.out.println("## Rate for 2016-11-04");
        System.out.println(client.day(Currency.EUR_A, LocalDate.of(2016, 11, 4)));
        System.out.println("");

        System.out.println("## Rates for 2016-11-01 to 2016-11-05");
        System.out.println(client.range(Currency.EUR_A, LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5)));
        System.out.println("");

        System.out.println("# Less popular currencies are updated weekly and stored in table B");
        System.out.println();

        System.out.println("## Current rate");
        System.out.println(client.current(Currency.KES_B));
        System.out.println("");

        System.out.println("# Bid-ask rates for some of the currencies are stored in table C");
        System.out.println();

        System.out.println("## Current rate");
        System.out.println(client.current(Currency.EUR_C));
        System.out.println("");
    }
}
