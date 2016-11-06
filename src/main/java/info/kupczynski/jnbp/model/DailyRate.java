package info.kupczynski.jnbp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency mid for a given day.
 *
 * It can be either a mid market rate or bid and ask rates.
 */
public class DailyRate {

    private final String rateId;
    private final LocalDate effectiveDate;
    private final BigDecimal mid;
    private final BigDecimal bid;
    private final BigDecimal ask;

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

    public String getRateId() {
        return rateId;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public BigDecimal getMid() {
        return mid;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
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
