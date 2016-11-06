package info.kupczynski.jnbp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * Represents a list of daily rates for a given currency
 */
public class CurrencyRates {

    private final String table;
    private final String currency;
    private final String code;
    private final List<DailyRate> rates;

    @JsonCreator
    public CurrencyRates(
            @JsonProperty("table") String table,
            @JsonProperty("currency") String currency,
            @JsonProperty("code") String code,
            @JsonProperty("rates") List<DailyRate> rates) {
        this.table = requireNonNull(table);
        this.currency = requireNonNull(currency);
        this.code = requireNonNull(code);
        this.rates = new ArrayList<>(requireNonNull(rates));
    }

    public String getTable() {
        return table;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public List<DailyRate> getRates() {
        return unmodifiableList(rates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRates that = (CurrencyRates) o;

        if (!table.equals(that.table)) return false;
        if (!currency.equals(that.currency)) return false;
        if (!code.equals(that.code)) return false;
        return rates.equals(that.rates);

    }

    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + rates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyRates{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
