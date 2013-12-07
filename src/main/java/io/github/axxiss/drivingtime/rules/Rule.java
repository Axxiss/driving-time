package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * Common class to handle driving and rest time.
 */
public abstract class Rule {

    public static final long minutesToMillis = 60 * 1000;
    public static final long hoursToMillis = 60 * minutesToMillis;

    /**
     * Time driving on this period
     */
    protected Duration driving;

    /**
     * Time available to drive in this period.
     */
    protected Duration available;

    /**
     * Maximum amount of time to drive on this period.
     * <p/>
     * This value MUST be set by the subclass.
     */
    protected Duration max;

    protected Interval period;


    /**
     * The normal amount of time that the driver need to rest.
     * <p/>
     * This value MUST be set by the subclass.
     */
    protected Duration rest;


    /**
     * List of intervals.
     */
    protected IntervalList intervals;

    public Rule(DateTime start, DateTime end, long max, IntervalList intervals) {
        this.max = new Duration(max);
        period = new Interval(start, end);
        this.intervals = intervals;
        driving = intervals.overlap(period);
        available = calcAvailable();
    }

    public Duration getDriving() {
        return driving;
    }

    public void setDriving(Duration driving) {
        this.driving = driving;
    }

    public Duration getAvailable() {
        return available;
    }

    public void setAvailable(Duration available) {
        this.available = available;
    }

    public Duration getMax() {
        return max;
    }

    /**
     * If current duration is greater than max, zero is returned. Otherwise max minus current is returned.
     *
     * @param max
     * @param current
     * @return
     */
    protected Duration safeAvailable(Duration max, Duration current) {
        if (max.isShorterThan(current)) {
            return new Duration(0);
        } else {
            return max.minus(current);
        }
    }

    /**
     * Calculates the allowed time to drive, without considering rest periods.
     *
     * @return
     */
    protected abstract Duration calcAvailable();


    /**
     * Calculates the duration that the driver needs to rest in the current rule.
     *
     * @return rest duration.
     */
    protected Duration calcRest() {

        Duration gap = intervals.findGap(period, rest);

        if (gap.isShorterThan(rest)) {
            return rest.minus(gap);
        } else {
            return new Duration(0);
        }
    }
}
