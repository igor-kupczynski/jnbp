package info.kupczynski.jnbp.retrofit;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DateRangeTest {

    @Test
    public void shouldCreateFromInclusive() {
        assertThat(DateRange.fromInclusive(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 3)),
                is(new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 4))));
    }

    @Test
    public void shouldReturnLastDayIncludedInTheDateRange() {
        assertThat(DateRange.fromInclusive(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 3)).getLastIncludedDay(),
                is(LocalDate.of(2000, 1, 3)));
    }

    @Test
    public void shouldSplitDateRangeIntoSmallerChunks() {
        DateRange range = new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 3));
        assertThat(range.split(1), contains(
                new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 2)),
                new DateRange(LocalDate.of(2000, 1, 2), LocalDate.of(2000, 1, 3))
        ));
    }

    @Test
    public void shouldSupportBatchBiggerThanTheRange() {
        DateRange range = new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 3));
        assertThat(range.split(7), contains(
                new DateRange(LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 3))
        ));
    }



}
