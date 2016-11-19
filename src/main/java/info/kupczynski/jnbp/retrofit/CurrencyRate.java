package info.kupczynski.jnbp.retrofit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency mid rate (or bid/ask pair) as stored in {@link DailyTable}
 */
@JsonIgnoreProperties(value = { "currency" })
class CurrencyRate {

    public final String currencyCode;
    public final BigDecimal mid;
    public final BigDecimal bid;
    public final BigDecimal ask;

    @JsonCreator
    public CurrencyRate(
            @JsonProperty("code") String currencyCode,
            @JsonProperty("mid") BigDecimal mid,
            @JsonProperty("bid") BigDecimal bid,
            @JsonProperty("ask") BigDecimal ask) {
        this.currencyCode = requireNonNull(currencyCode);
        if (mid == null && (bid == null || ask == null)) {
            throw new NullPointerException("Either mid or bid and ask rates are required");
        }
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (!currencyCode.equals(that.currencyCode)) return false;
        if (mid != null ? !mid.equals(that.mid) : that.mid != null) return false;
        if (bid != null ? !bid.equals(that.bid) : that.bid != null) return false;
        return ask != null ? ask.equals(that.ask) : that.ask == null;

    }

    @Override
    public int hashCode() {
        int result = currencyCode.hashCode();
        result = 31 * result + (mid != null ? mid.hashCode() : 0);
        result = 31 * result + (bid != null ? bid.hashCode() : 0);
        result = 31 * result + (ask != null ? ask.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "currencyCode='" + currencyCode + '\'' +
                ", mid=" + mid +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
