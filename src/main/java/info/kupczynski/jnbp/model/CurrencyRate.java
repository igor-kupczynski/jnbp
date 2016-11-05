package info.kupczynski.jnbp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class CurrencyRate {

    private final String number;
    private final String effectiveDate;
    private final BigDecimal rate;

    @JsonCreator
    public CurrencyRate(
            @JsonProperty("no") String tableName,
            @JsonProperty("effectiveDate") String effectiveDate,
            @JsonProperty("mid") BigDecimal rate) {
        this.number = requireNonNull(tableName);
        this.effectiveDate = requireNonNull(effectiveDate);
        this.rate = requireNonNull(rate);
    }

    public String getNumber() {
        return number;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (!number.equals(that.number)) return false;
        if (!effectiveDate.equals(that.effectiveDate)) return false;
        return rate.equals(that.rate);

    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "number='" + number + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", rate=" + rate +
                '}';
    }
}
