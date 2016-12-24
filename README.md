# JNBP

This is a java client for the NBP currency exchange rates API.

[Narodowy Bank Polski](http://www.nbp.pl/) or NBP is the central bank of the
Republic of Poland. Among other tasks it publishes the official exchange rate
of Złoty (the Polish currency) against other currencies. [NBP provides an
API to access the exchange rates](http://api.nbp.pl/en.html).

**Note** This is a work in progress.

## Client

The client offers reactive wrapper around NBP Web API
 
## Examples

See the [Demo.java](./src/main/java/info/kupczynski/jnbp/Demo.java) class.

- Given some helper functions
    ```java
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
    ```

- And a client
    ```java
    private JNbpClient client = JNbpClientFactory.create();
    ```

- The following code
    ```java
    banner("Single currency, Euro, Table A");
    demo("Current rate", client.current(Currency.EUR_A));
    demo("Latest 3 rates", client.latest(Currency.EUR_A, 3));
    demo("Rates from 2016-11-01 to 2016-11-05", client.range(Currency.EUR_A, LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 5)));

    banner("Bid ask rates are stored in table C");
    demo("Latest 3 rates", client.latest(Currency.EUR_C, 3));

    banner("Less popular currencies are stored in table B and updated weekly");
    demo("Rates from 2016-10-01 to 2016-11-01", client.range(Currency.KES_B, LocalDate.of(2016, 10, 1), LocalDate.of(2016, 11, 1)));
    ```
    
- Produces the following input
    ```mardown
    Single currency, Euro, Table A
    ------------------------------
    
    ### Current rate
    * CurrencyDailyRate{currency=EUR_A, rateId='218/A/NBP/2016', effectiveDate=2016-11-10, mid=4.3424, bid=null, ask=null}
    
    ### Latest 3 rates
    * CurrencyDailyRate{currency=EUR_A, rateId='216/A/NBP/2016', effectiveDate=2016-11-08, mid=4.3285, bid=null, ask=null}
    * CurrencyDailyRate{currency=EUR_A, rateId='217/A/NBP/2016', effectiveDate=2016-11-09, mid=4.3455, bid=null, ask=null}
    * CurrencyDailyRate{currency=EUR_A, rateId='218/A/NBP/2016', effectiveDate=2016-11-10, mid=4.3424, bid=null, ask=null}
    
    ### Rates from 2016-11-01 to 2016-11-05
    * CurrencyDailyRate{currency=EUR_A, rateId='212/A/NBP/2016', effectiveDate=2016-11-02, mid=4.3169, bid=null, ask=null}
    * CurrencyDailyRate{currency=EUR_A, rateId='213/A/NBP/2016', effectiveDate=2016-11-03, mid=4.3238, bid=null, ask=null}
    * CurrencyDailyRate{currency=EUR_A, rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
    
    Bid ask rates are stored in table C
    -----------------------------------
    
    ### Latest 3 rates
    * CurrencyDailyRate{currency=EUR_C, rateId='216/C/NBP/2016', effectiveDate=2016-11-08, mid=null, bid=4.2919, ask=4.3787}
    * CurrencyDailyRate{currency=EUR_C, rateId='217/C/NBP/2016', effectiveDate=2016-11-09, mid=null, bid=4.2903, ask=4.3769}
    * CurrencyDailyRate{currency=EUR_C, rateId='218/C/NBP/2016', effectiveDate=2016-11-10, mid=null, bid=4.2983, ask=4.3851}
    
    Less popular currencies are stored in table B and updated weekly
    ----------------------------------------------------------------
    
    ### Rates from 2016-10-01 to 2016-11-01
    * CurrencyDailyRate{currency=KES_B, rateId='040/B/NBP/2016', effectiveDate=2016-10-05, mid=0.037866, bid=null, ask=null}
    * CurrencyDailyRate{currency=KES_B, rateId='041/B/NBP/2016', effectiveDate=2016-10-12, mid=0.038237, bid=null, ask=null}
    * CurrencyDailyRate{currency=KES_B, rateId='042/B/NBP/2016', effectiveDate=2016-10-19, mid=0.038785, bid=null, ask=null}
    * CurrencyDailyRate{currency=KES_B, rateId='043/B/NBP/2016', effectiveDate=2016-10-26, mid=0.039003, bid=null, ask=null}
    ```

- Replace currency with a table to get all the full table instead of a single currency
    ```java
    client.current(CurrencyTable.A)
    ```
    
    ```markdown
    * CurrencyDailyRate{currency=THB_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.1173, bid=null, ask=null}
    * CurrencyDailyRate{currency=USD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=4.2199, bid=null, ask=null}
    * CurrencyDailyRate{currency=AUD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=3.0412, bid=null, ask=null}
    * CurrencyDailyRate{currency=HKD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.5438, bid=null, ask=null}
    * CurrencyDailyRate{currency=CAD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=3.1284, bid=null, ask=null}
    * CurrencyDailyRate{currency=NZD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=2.9157, bid=null, ask=null}
    * CurrencyDailyRate{currency=SGD_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=2.9194, bid=null, ask=null}
    * CurrencyDailyRate{currency=EUR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=4.4126, bid=null, ask=null}
    * CurrencyDailyRate{currency=HUF_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.014231, bid=null, ask=null}
    * CurrencyDailyRate{currency=CHF_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=4.1170, bid=null, ask=null}
    * CurrencyDailyRate{currency=GBP_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=5.1815, bid=null, ask=null}
    * CurrencyDailyRate{currency=UAH_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.1605, bid=null, ask=null}
    * CurrencyDailyRate{currency=JPY_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.03597, bid=null, ask=null}
    * CurrencyDailyRate{currency=CZK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.1633, bid=null, ask=null}
    * CurrencyDailyRate{currency=DKK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.5935, bid=null, ask=null}
    * CurrencyDailyRate{currency=ISK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.03719, bid=null, ask=null}
    * CurrencyDailyRate{currency=NOK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.4841, bid=null, ask=null}
    * CurrencyDailyRate{currency=SEK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.4576, bid=null, ask=null}
    * CurrencyDailyRate{currency=HRK_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.5858, bid=null, ask=null}
    * CurrencyDailyRate{currency=RON_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.9734, bid=null, ask=null}
    * CurrencyDailyRate{currency=BGN_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=2.2561, bid=null, ask=null}
    * CurrencyDailyRate{currency=TRY_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=1.2025, bid=null, ask=null}
    * CurrencyDailyRate{currency=ILS_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=1.1013, bid=null, ask=null}
    * CurrencyDailyRate{currency=CLP_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.00625, bid=null, ask=null}
    * CurrencyDailyRate{currency=PHP_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.0849, bid=null, ask=null}
    * CurrencyDailyRate{currency=MXN_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.2036, bid=null, ask=null}
    * CurrencyDailyRate{currency=ZAR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.3018, bid=null, ask=null}
    * CurrencyDailyRate{currency=BRL_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=1.2838, bid=null, ask=null}
    * CurrencyDailyRate{currency=MYR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.9433, bid=null, ask=null}
    * CurrencyDailyRate{currency=RUB_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.0692, bid=null, ask=null}
    * CurrencyDailyRate{currency=IDR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.00031375, bid=null, ask=null}
    * CurrencyDailyRate{currency=INR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.062224, bid=null, ask=null}
    * CurrencyDailyRate{currency=KRW_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.003504, bid=null, ask=null}
    * CurrencyDailyRate{currency=CNY_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=0.6074, bid=null, ask=null}
    * CurrencyDailyRate{currency=XDR_A, rateId='248/A/NBP/2016', effectiveDate=2016-12-23, mid=5.6622, bid=null, ask=null}
    ```

## Implementation Status

- [X] Queries for complete tables
- [X] Queries for particular currency
- [ ] Queries for gold prices

## Disclaimer

I'm not associated in any way with NBP, neither is this package.

This code is licenced under Apache Licence 2.0 see [LICENSE](./LICENSE)
for details.
