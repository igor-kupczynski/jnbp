package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.kupczynski.jnbp.api.Currency;
import info.kupczynski.jnbp.api.CurrencyDailyRate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency mid rate (or a bid/ask pair) for a given day.
 *
 * It can be either a mid market rate or bid and ask rates.
 */
class DailyRate {

    public final String rateId;
    public final LocalDate effectiveDate;
    public final BigDecimal mid;
    public final BigDecimal bid;
    public final BigDecimal ask;

    @JsonCreator
    public DailyRate(
            @JsonProperty("no") String rateId,
            @JsonProperty("effectiveDate") LocalDate effectiveDate,
            @JsonProperty("mid") BigDecimal mid,
            @JsonProperty("bid") BigDecimal bid,
            @JsonProperty("ask") BigDecimal ask) {
        this.rateId = requireNonNull(rateId);
        this.effectiveDate = requireNonNull(effectiveDate);
        if (mid == null && (bid == null || ask == null)) {
            throw new NullPointerException("Either mid or bid and ask rates are required");
        }
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
    }

    public CurrencyDailyRate toCurrencyDailyRate(Currency currency) {
        return new CurrencyDailyRate(currency, rateId, effectiveDate, mid, bid, ask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRate dailyRate = (DailyRate) o;

        if (!rateId.equals(dailyRate.rateId)) return false;
        if (!effectiveDate.equals(dailyRate.effectiveDate)) return false;
        if (mid != null ? !mid.equals(dailyRate.mid) : dailyRate.mid != null) return false;
        if (bid != null ? !bid.equals(dailyRate.bid) : dailyRate.bid != null) return false;
        return ask != null ? ask.equals(dailyRate.ask) : dailyRate.ask == null;

    }

    @Override
    public int hashCode() {
        return rateId.hashCode();
    }

    @Override
    public String toString() {
        return "DailyRate{" +
                "rateId='" + rateId + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", mid=" + mid +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
