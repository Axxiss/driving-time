package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Date;

import static io.github.axxiss.drivingtime.Utils.available;
import static io.github.axxiss.drivingtime.Utils.minDuration;

/**
 * Calculates a driver's available driving time.
 *
 * @author Alexis Mas
 */
public class DrivingTime {

    /**
     * Driving intervals.
     */
    protected IntervalList driveIntervals;


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
        return driveIntervals.overlap(new Interval(when.minusDays(1), when));
    }

    /**
     * Return the {@link Duration} worked over the last 7 days.
     *
     * @return
     */
    public Duration lastWeek(DateTime when) {
        return driveIntervals.overlap(new Interval(when.minusWeeks(1), when));
    }

    /**
     * Return the {@link Duration} worked over the last 15 days.
     *
     * @return
     */
    public Duration lastFortnight(DateTime when) {
        return driveIntervals.overlap(new Interval(when.minusWeeks(2), when));
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
     * Calculates how much time is available for nonstop-driving
     *
     * @return
     */
    public Duration nonstop() {
        DateTime now = DateTime.now();
        DateTime start = now.minus(Driving.NONSTOP);

        Interval interval = new Interval(start, now);

        int index = driveIntervals.overlapStart(interval);

        if (index == -1) {
            return Driving.NONSTOP;
        }


        int gap = driveIntervals.findGap(index, Driving.NONSTOP);


        return Driving.FORTNIGHTLY;

    }


}
