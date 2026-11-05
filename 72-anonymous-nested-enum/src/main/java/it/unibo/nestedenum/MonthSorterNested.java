package it.unibo.nestedenum;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
@SuppressWarnings("javadoc")
public final class MonthSorterNested implements MonthSorter {

    private static final Comparator<String> BY_DAYS = new SortByDays();
    private static final Comparator<String> BY_ORDER = new SortByMonthOrder();

    @Override
    public Comparator<String> sortByDays() {
        return BY_DAYS;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return BY_ORDER;
    }

    /**
     * Represents the twelve months of the year.
     */
    public enum Month {
        /** January, with 31 days. */
        JANUARY(31),
        /** February, with 28 days. */
        FEBRUARY(28),
        /** March, with 31 days. */
        MARCH(31),
        /** April, with 30 days. */
        APRIL(30),
        /** May, with 31 days. */
        MAY(31),
        /** June, with 30 days. */
        JUNE(30),
        /** July, with 31 days. */
        JULY(31),
        /** August, with 31 days. */
        AUGUST(31),
        /** September, with 30 days. */
        SEPTEMBER(30),
        /** October, with 31 days. */
        OCTOBER(31),
        /** November, with 30 days. */
        NOVEMBER(30),
        /** December, with 31 days. */
        DECEMBER(31);

        private final int days;

        /**
         * Creates a month with the specified number of days.
         *
         * @param days number of days in the month
         */
        Month(final int days) {
            this.days = days;
        }

        /**
         * Converts a string into its corresponding month.
         * Accepts full month names and unambiguous prefixes.
         *
         * @param monthName name or prefix of the month
         * @return the corresponding month
         * @throws NullPointerException if monthName is null
         * @throws IllegalArgumentException if no month matches or the name is ambiguous
         */
        public static Month fromString(final String monthName) {
            Objects.requireNonNull(monthName);
            try {
                return valueOf(monthName);
            } catch (final IllegalArgumentException exception) {
                Month match = null;
                for (final Month month : values()) {
                    if (month.toString().toLowerCase(Locale.ROOT)
                            .startsWith(monthName.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                monthName + " is ambiguous: both " + match + " and " + month
                                    + " would be valid matches",
                                exception
                            );
                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + monthName, exception);
                }
                return match;
            }
        }
    }

    /**
     * Comparator that orders month names by their number of days.
     */
    private static final class SortByDays implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final String o1, final String o2) {
            final var m1 = Month.fromString(o1);
            final var m2 = Month.fromString(o2);

            return Integer.compare(m1.days, m2.days);
        }
    }

    /**
     * Comparator that orders month names according to their calendar order.
     */
    private static final class SortByMonthOrder implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final String s1, final String s2) {
            return Month.fromString(s1).compareTo(Month.fromString(s2));
        }
    }
}
