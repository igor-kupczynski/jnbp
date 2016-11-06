package info.kupczynski.jnbp.model;

import static java.util.Objects.requireNonNull;

/**
 * Currency represented by its code and the table that its rates are stored in
 *
 * @see
 */
public enum Currency {

    EUR_A(CurrencyTable.A, "EUR");

    private final CurrencyTable table;
    private final String code;

    Currency(CurrencyTable table, String code) {
        this.table = table;
        this.code = code;
    }

    public static Currency valueOf(CurrencyTable table, String code) {
        requireNonNull(table);
        requireNonNull(code);
        for (Currency c : Currency.values()) {
            if (c.table == table && c.code.equals(code))
                return c;
        }
        throw new IllegalArgumentException(String.format("Currency not found table %s -> %s", table, code));
    }
}
