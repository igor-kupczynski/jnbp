package info.kupczynski.jnbp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency rate for a given day
 */
public class DailyRate {

    private final String rateId;
    private final LocalDate effectiveDate;
    private final BigDecimal rate;

    @JsonCreator
    public DailyRate(
            @JsonProperty("no") String rateId,
            @JsonProperty("effectiveDate") LocalDate effectiveDate,
            @JsonProperty("mid") BigDecimal rate) {
        this.rateId = requireNonNull(rateId);
        this.effectiveDate = requireNonNull(effectiveDate);
        this.rate = requireNonNull(rate);
    }

    public String getRateId() {
        return rateId;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyRate that = (DailyRate) o;

        if (!rateId.equals(that.rateId)) return false;
        if (!effectiveDate.equals(that.effectiveDate)) return false;
        return rate.equals(that.rate);

    }

    @Override
    public int hashCode() {
        return rateId.hashCode();
    }

    @Override
    public String toString() {
        return "DailyRate{" +
                "rateId='" + rateId + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", rate=" + rate +
                '}';
    }
}
