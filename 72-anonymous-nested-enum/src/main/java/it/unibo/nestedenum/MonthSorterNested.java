package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
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
     * Month
     */
    public enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public static Month fromString(String monthName){
            Objects.requireNonNull(monthName);
            try {
                return valueOf(monthName);
            } catch (final IllegalArgumentException exception) {
                Month match = null;
                for (final Month month: values()) {
                    if (month.toString().toLowerCase(Locale.ROOT).startsWith(monthName.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                monthName + " is ambiguous: both " + match + " and " + month + " would be valid matches",
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

    private static final class SortByDays implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            final var m1 = Month.fromString(o1);
            final var m2 = Month.fromString(o2);

            return Integer.compare(m1.days,m2.days);
        }
        
    }

    private static final class SortByMonthOrder implements Comparator<String>{

        @Override
        public int compare(String s1, String s2) {
            return Month.fromString(s1).compareTo(Month.fromString(s2));
        }
        
    }

}
