package info.kupczynski.jnbp.retrofit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Pair of dates which represent a date range {@code [from, to>}. The resolution is 1 day.
 */
class DateRange {
    /**
     * Start of the date range inclusive
     */
    public final LocalDate from;

    /**
     * End of the date range exclusive
     */
    public final LocalDate to;

    public DateRange(LocalDate from, LocalDate to) {
        if (!to.isAfter(from)) {
            throw new IllegalArgumentException("`from` should be before `to`");
        }
        this.from = requireNonNull(from);
        this.to = requireNonNull(to);
    }

    /**
     * Return the last day included in this {@link DateRange}.
     * <p>
     * Given a range [start, end> it is {@code end - 1 day}
     *
     * @return {@code end - 1 day}
     */
    public LocalDate getLastIncludedDay() {
        return to.minusDays(1);
    }

    /**
     * Split a {@link DateRange} into smaller ranges of at most {@code maxDaysPerBatch}.
     *
     * @param maxDaysPerBatch max batch size
     * @return ordered collection of {@link DateRange}s of at most {@code maxDaysPerBatch}.
     */
    public Collection<DateRange> split(long maxDaysPerBatch) {
        List<DateRange> pairs = new ArrayList<>();
        LocalDate start, end = from;
        do {
            start = end;
            end = start.plusDays(maxDaysPerBatch);
            pairs.add(new DateRange(start, end.isAfter(to) ? to : end));
        } while (end.isBefore(to));
        return pairs;
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateRange dateRange = (DateRange) o;

        if (!from.equals(dateRange.from)) return false;
        return to.equals(dateRange.to);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    /**
     * Create a [start, end+1d> {@link DateRange} from [start, end] dates
     *
     * @param start start date, inclusive
     * @param end   end date, inclusive
     * @return DateRange [start, end+1d>
     */
    public static DateRange fromInclusive(LocalDate start, LocalDate end) {
        return new DateRange(start, end.plusDays(1));
    }
}
