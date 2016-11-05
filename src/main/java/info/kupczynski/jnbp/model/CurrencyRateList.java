package info.kupczynski.jnbp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

public class CurrencyRateList {

    private final String table;
    private final String currency;
    private final String code;
    private final List<CurrencyRate> rates;

    @JsonCreator
    public CurrencyRateList(
            @JsonProperty("table") String table,
            @JsonProperty("currency") String currency,
            @JsonProperty("code") String code,
            @JsonProperty("rates") List<CurrencyRate> rates) {
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

    public List<CurrencyRate> getRates() {
        return unmodifiableList(rates);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRateList that = (CurrencyRateList) o;

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
        return "CurrencyRateList{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}