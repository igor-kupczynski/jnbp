package info.kupczynski.jnbp.api;

/**
 * Table is a set of currencies which rates are updated on the same schedule.
 *
 * @see <a href="http://www.nbp.pl/home.aspx?f=/statystyka/kursy.html">NBP web page</a>
 */
public enum CurrencyTable {
    /**
     * Mid-market rates updated daily, 11:45-12:15; contains the common currencies.
     */
    A,

    /**
     * Mid-market rates updated weekly on Wednesday, 11:45-12:15; contains the less common currencies.
     */
    B,

    /**
     * Bid and ask rates updated daily, 7:45-8:15.
     */
    C;
}
