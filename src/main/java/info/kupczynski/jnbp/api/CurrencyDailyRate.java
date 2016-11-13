package info.kupczynski.jnbp.api;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

/**
 * Represents a currency rate for a given day.
 */
public class CurrencyDailyRate {

    public final Currency currency;
    public final String rateId;
    public final LocalDate effectiveDate;
    public final BigDecimal mid;
    public final BigDecimal bid;
    public final BigDecimal ask;

    public CurrencyDailyRate(Currency currency, String rateId, LocalDate effectiveDate,
                             BigDecimal mid, BigDecimal bid, BigDecimal ask) {
        this.currency = requireNonNull(currency);
        this.rateId = requireNonNull(rateId);
        this.effectiveDate = requireNonNull(effectiveDate);
        if (mid == null && (bid == null || ask == null)) {
            throw new NullPointerException("Either mid or bid and ask rates are required");
        }
        this.mid = mid;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "CurrencyDailyRate{" +
                "currency=" + currency +
                ", rateId='" + rateId + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", mid=" + mid +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyDailyRate that = (CurrencyDailyRate) o;

        if (currency != that.currency) return false;
        if (!rateId.equals(that.rateId)) return false;
        if (!effectiveDate.equals(that.effectiveDate)) return false;
        if (mid != null ? !mid.equals(that.mid) : that.mid != null) return false;
        if (bid != null ? !bid.equals(that.bid) : that.bid != null) return false;
        return ask != null ? ask.equals(that.ask) : that.ask == null;

    }

    @Override
    public int hashCode() {
        int result = currency.hashCode();
        result = 31 * result + rateId.hashCode();
        result = 31 * result + effectiveDate.hashCode();
        return result;
    }
}
