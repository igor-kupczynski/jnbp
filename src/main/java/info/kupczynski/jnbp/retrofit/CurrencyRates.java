package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyTable;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Represents a list of daily rates for a given currency
 */
@JsonIgnoreProperties(value = { "currency" })
class CurrencyRates {

    private final Currency currency;
    private final List<DailyRate> rates;

    @JsonCreator
    public CurrencyRates(
            @JsonProperty("table") String table,
            @JsonProperty("code") String code,
            @JsonProperty("rates") List<DailyRate> rates) {

        CurrencyTable ct = CurrencyTable.valueOf(table);
        this.currency = Currency.valueOf(ct, code);
        this.rates = new ArrayList<>(requireNonNull(rates));
    }

    public Currency getCurrency() {
        return currency;
    }

    public List<DailyRate> getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRates that = (CurrencyRates) o;

        if (currency != that.currency) return false;
        return rates.equals(that.rates);

    }

    @Override
    public int hashCode() {
        int result = currency.hashCode();
        result = 31 * result + rates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyRates{" +
                "currency=" + currency +
                ", rates=" + rates +
                '}';
    }
}
