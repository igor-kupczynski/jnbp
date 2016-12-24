package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;
import info.kupczynski.jnbp.api.CurrencyTable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency rates table for a given day
 */
@JsonIgnoreProperties(value = {"tradingDate"})
class DailyTable {

    public final CurrencyTable table;
    public final String rateId;
    public final LocalDate effectiveDate;
    public final List<CurrencyRate> rates;

    @JsonCreator
    public DailyTable(
            @JsonProperty("table") String table,
            @JsonProperty("no") String rateId,
            @JsonProperty("effectiveDate") LocalDate effectiveDate,
            @JsonProperty("rates") List<CurrencyRate> rates) {
        this.table = CurrencyTable.valueOf(table);
        this.rateId = requireNonNull(rateId);
        this.effectiveDate = requireNonNull(effectiveDate);
        this.rates = new ArrayList<>(requireNonNull(rates));
    }

    public List<CurrencyDailyRate> toCurrencyDailyRates() {
        return rates.stream()
                .map(rate -> new CurrencyDailyRate(
                        Currency.valueOf(table, rate.currencyCode),
                        rateId,
                        effectiveDate,
                        rate.mid,
                        rate.bid,
                        rate.ask))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyTable that = (DailyTable) o;

        if (table != that.table) return false;
        if (!rateId.equals(that.rateId)) return false;
        if (!effectiveDate.equals(that.effectiveDate)) return false;
        return rates.equals(that.rates);

    }

    @Override
    public int hashCode() {
        int result = table.hashCode();
        result = 31 * result + rateId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DailyTable{" +
                "table=" + table +
                ", rateId='" + rateId + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", rates=" + rates +
                '}';
    }
}
