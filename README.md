# JNBP

This is a java client for the NBP currency exchange rates API.

[Narodowy Bank Polski](http://www.nbp.pl/) or NBP is the central bank of the
Republic of Poland. Among other tasks it publishes the official exchange rate
of Złoty (the Polish currency) against other currencies. [NBP provides an
API to access the exchange rates](http://api.nbp.pl/en.html).

**Note** This is a work in progress.

## Thin client

This is a thin wrapper around the API provided by NBP. Its aim is to provide 
a 1:1 implementation of the functions available in the NBP Currency Exchange
API.
 
#### Examples

See the [Demo.java](./src/main/java/info/kupczynski/jnbp/Demo.java) class.

- Create the client
  ```java
  JNbpClient client = new JNbpClient();
  ```

- Queries for particular currency
    
    * Current mid-market exchange rate for "EUR" in table "A" 
        ```java
        CurrencyRates rates = client.current(Currency.EUR_A);
        ```
        
        *returns*:
        ```
        CurrencyRates{currency=EUR_A, rates=[
            DailyRate{rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
        ]}
        ```
    
    * Series of latest 3 exchange rates
        ```java
        CurrencyRates rates = client.latest(Currency.EUR_A, 3);
        ```
        
        *returns*:
        ```
        CurrencyRates{currency=EUR_A, rates=[
            DailyRate{rateId='212/A/NBP/2016', effectiveDate=2016-11-02, mid=4.3169, bid=null, ask=null},
            DailyRate{rateId='213/A/NBP/2016', effectiveDate=2016-11-03, mid=4.3238, bid=null, ask=null},
            DailyRate{rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
        ]}
        ```
        
    * Exchange rate for today (may not exist)
        ```java
        Optional<CurrencyRates> rates = client.today(Currency.EUR_A);
        ```
        
        *returns*:
        ```
        Optional[
            CurrencyRates{currency=EUR_A, rates=[
                DailyRate{rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
            ]}
        ]    
        ```
    
    * Exchange rate for a given day (may not exist)
        ```java
        Optional<CurrencyRates> rates = client.day(Currency.EUR_A, LocalDate.of(2016, 11, 4))
        ```
        
        *returns*:
        ```
        Optional[
            CurrencyRates{currency=EUR_A, rates=[
                DailyRate{rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
            ]}
        ]
        ```
    
    * Exchange rate between two dates 
        ```java
        CurrencyRates rates = client.range(Currency.EUR_A, LocalDate.of(2016, 11, 2), LocalDate.of(2016, 11, 4));
        ```
        
        *returns*:
        ```
        CurrencyRates{currency=EUR_A, rates=[
            DailyRate{rateId='212/A/NBP/2016', effectiveDate=2016-11-02, mid=4.3169, bid=null, ask=null},
            DailyRate{rateId='213/A/NBP/2016', effectiveDate=2016-11-03, mid=4.3238, bid=null, ask=null},
            DailyRate{rateId='214/A/NBP/2016', effectiveDate=2016-11-04, mid=4.3133, bid=null, ask=null}
        ]}                
        ```
    
    * Less popular currencies are updated once a week and stored in table "B"
        ```java
        CurrencyRates rates = client.current(Currency.KES_B);
        ```
        
        *returns*:
        ```
        CurrencyRates{currency=KES_B, rates=[
            DailyRate{rateId='044/B/NBP/2016', effectiveDate=2016-11-02, mid=0.038352, bid=null, ask=null}
        ]}
        ```
    
    * Bid-ask rates are stored in table "C"
        ```java
        CurrencyRates rates = client.current(Currency.EUR_C)
        ```
        
        *returns*:
        ```
        CurrencyRates{currency=EUR_C, rates=[
            DailyRate{rateId='214/C/NBP/2016', effectiveDate=2016-11-04, mid=null, bid=4.2832, ask=4.3698}
        ]}
        ```


## Implementation Status

* **Thin client**
    - [ ] Queries for complete tables
    - [X] Queries for particular currency
    - [ ] Queries for gold prices

* **JavaRx client**
    - [ ] Queries for complete tables
    - [ ] Queries for particular currency
    - [ ] Queries for gold prices

## Disclaimer

I'm not associated in any way with NBP, neither is this package.

This code is licenced under Apache Licence 2.0 see [LICENSE](./LICENSE)
for details.
