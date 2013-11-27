package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.Date;

/**
 * Calculates a driver's available driving time.
 *
 * @author Alexis Mas
 */
public class DrivingTime {

    /**
     * Driving intervals.
     */
    private final IntervalList driveIntervals;


    /**
     * Creates a new driving time instance.
     *
     * @param history driving history, <b><MUST/b> be sorted from oldest to newest.
     */
    public DrivingTime(Date[] history) {
        final int instants = history.length;
        driveIntervals = new IntervalList(history);
    }


    /**
     * Return the {@link Duration} worked over the last 24 hours.
     *
     * @return
     */
    public Duration lastDay(DateTime when) {
        return driveIntervals.getDriveDuration(when.minusDays(1), when);
    }

    /**
     * Return the {@link Duration} worked over the last 7 days.
     *
     * @return
     */
    public Duration lastWeek(DateTime when) {
        return driveIntervals.getDriveDuration(when.minusWeeks(1), when);
    }

    /**
     * Return the {@link Duration} worked over the last 15 days.
     *
     * @return
     */
    public Duration lastFortnight(DateTime when) {
        return driveIntervals.getDriveDuration(when.minusWeeks(2), when);
    }

    /**
     * Calculates how much time is available for driving in the next 24 hours.
     *
     * @return
     */
    public Duration nextDay() {
        DateTime when = DateTime.now().plusDays(1);

        Duration day = available(Driving.DAILY, lastDay(when));
        Duration week = available(Driving.WEEKLY, lastWeek(when));
        Duration fortnight = available(Driving.FORTNIGHTLY, lastFortnight(when));

        Duration min = minDuration(day, Driving.DAILY);
        min = minDuration(week, min);
        min = minDuration(fortnight, min);

        return min;
    }

    /**
     * Calculates how much time is available for driving in the next 7 days.
     *
     * @return
     */
    public Duration nextWeek() {
        DateTime when = DateTime.now().plusWeeks(1);

        Duration fortnight = available(Driving.FORTNIGHTLY, lastFortnight(when));
        Duration min = minDuration(fortnight, Driving.FORTNIGHTLY);

        return min;
    }

    /**
     * Calculates how much time is available for driving in the next 14 days.
     *
     * @return
     */
    public Duration nextFortnight() {
        return Driving.FORTNIGHTLY;
    }

    /**
     * Given a maximum and a current duration calculates the difference between the maximum
     * and the current duration. If the duration is greater than the maximum the duration returned
     * will be zero.
     *
     * @param maximum the maximum duration.
     * @param current the current duration.
     * @return maximum - current
     */
    public Duration available(Duration maximum, Duration current) {
        if (current.isLongerThan(maximum)) {
            return new Duration(0);
        } else {
            return maximum.minus(current);
        }
    }

    /**
     * Returns the minimum {@link Duration}
     *
     * @param a a duration.
     * @param b another duration.
     * @return min(a, b)
     */
    private Duration minDuration(Duration a, Duration b) {
        if (a.isShorterThan(b)) {
            return a;
        } else {
            return b;
        }
    }


}
