package io.github.axxiss.drivingtime;

import org.joda.time.*;

import java.util.Date;

/**
 * Calculates a driver's available driving time.
 *
 * @author Alexis Mas
 */
public class DrivingTime {

    protected final static int DAY = 24;
    protected final static int WEEK = 7 * 24;
    protected final static int FORTNIGHT = 15 * 24;

    /**
     * Driving intervals.
     */
    private final Interval[] intervals;

    /**
     * Index of the interval where the current day start, i.e. 24 hours ago.
     */
    private int dayStart;

    /**
     * Index of the interval where the current week start, i.e. 7 days ago.
     */
    private int weekStart;

    /**
     * Index of the interval where the current fortnight start, i.e. 2 weeks ago.
     */
    private int fortnightStart;

    /**
     * Time drove on the last 24 hours.
     */
    private Duration day = new Duration(0);

    /**
     * Time drove on the last week hours.
     */
    private Duration week = new Duration(0);

    /**
     * Time drove on the last 2 weeks.
     */
    private Duration fortnight = new Duration(0);


    /**
     * Creates a new driving time instance.
     *
     * @param history driving history. <b><MUST/b> be sorted from oldest to newest.
     */
    public DrivingTime(Date[] history) {
        final int instants = history.length;

        if (instants % 2 != 0) {
            throw new IllegalArgumentException("History parameter must be an odd number.");
        }

        intervals = new Interval[instants / 2];


        int j = 0;
        for (int i = 0; i < instants; i += 2) {
            intervals[j] = new Interval(history[i].getTime(), history[i + 1].getTime());
            j++;
        }
    }

    /**
     * Analyze the provided history and calculates the available driving time at the present time.
     */
    public void analyze() {
        analyze(new DateTime(DateTimeZone.UTC));
    }


    /**
     * Analyze the provided history and calculates the available driving time on the date specified
     * by {@code when}
     */
    public void analyze(DateTime when) {
        final Interval fortnightInterval = new Interval(when.minusHours(FORTNIGHT), when);
        final Interval weekInterval = new Interval(when.minusHours(WEEK), when);
        final Interval dayInterval = new Interval(when.minusHours(DAY), when);

        final int intervalCount = intervals.length;

        //find where each period start
        fortnightStart = findStart(0, fortnightInterval);
        weekStart = findStart(fortnightStart, weekInterval);
        dayStart = findStart(weekStart, dayInterval);


        //get the total amount of drove time
        fortnight = fortnight.plus(cumulativeMillis(fortnightStart, weekStart));
        week = week.plus(cumulativeMillis(weekStart, dayStart));
        day = day.plus(cumulativeMillis(dayStart, intervals.length));

        //add the pending result to complete the period's time
        week = week.plus(day);
        fortnight = fortnight.plus(week);
    }

    /**
     * Gets the cumulative milliseconds on a interval list.
     *
     * @param start start index
     * @param end   end index
     * @return cumulative millis.
     */
    private long cumulativeMillis(int start, int end) {
        long temp = 0;
        for (int i = start; i < end; i++) {
            temp += intervals[i].toDurationMillis();
        }
        return temp;
    }


    /**
     * Search into {@link #intervals} for the index where a week, day or a fortnight start.
     *
     * @param start    index to start the search
     * @param interval interval to search for
     * @return interval's start index
     */
    private int findStart(int start, ReadableInterval interval) {
        while (start < intervals.length && !intervals[start].overlaps(interval)) {
            start++;
        }
        return start;
    }


    /**
     * Calculates how much time the driver is allowed to drive in the next 24 hours.
     *
     * @return available minutes
     */
    public Duration day() {
        if (week().getMillis() == 0 || !Driving.DAILY.isLongerThan(day)) {
            return new Duration(0);
        } else {
            return Driving.DAILY.minus(day);
        }
    }


    /**
     * Calculates how much time the driver is allowed to drive in the next 7 days.
     *
     * @return available minutes
     */
    public Duration week() {

        if (fortnight().getMillis() == 0 || !Driving.WEEKLY.isLongerThan(week)) {
            return new Duration(0);
        } else {
            return Driving.WEEKLY.minus(week);
        }
    }

    /**
     * Calculates how much time the driver is allowed to drive in the next 15 days.
     *
     * @return available minutes
     */
    public Duration fortnight() {
        if (Driving.FORTNIGHTLY.isLongerThan(fortnight)) {
            return Driving.FORTNIGHTLY.minus(fortnight);
        } else {
            return new Duration(0);
        }
    }
}
