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
     */
    protected Duration max;

    protected Interval period;

    protected IntervalList intervals;

    public Rule(DateTime start, DateTime end, long max, IntervalList intervals) {
        this.max = new Duration(max);
        this.period = new Interval(start, end);
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

    protected Duration safeAvailable(Duration max, Duration current) {
        if (max.isShorterThan(current)) {
            return new Duration(0);
        } else {
            return max.minus(current);
        }
    }

    /**
     * Allowed time to drive.
     *
     * @return
     */
    protected abstract Duration calcAvailable();
}
