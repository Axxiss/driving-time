package io.github.axxiss.drivingtime;

import io.github.axxiss.drivingtime.rules.Day;
import io.github.axxiss.drivingtime.rules.Fortnight;
import io.github.axxiss.drivingtime.rules.Week;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

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


    private Day day;
    private Week week;
    private Fortnight fortnight;


    /**
     * Creates a new driving time instance.
     */
    public DrivingTime(Day day, Week week, Fortnight fortnight) {
        this.day = day;
        this.week = week;
        this.fortnight = fortnight;
    }

    /**
     * Calculates how much time is available for driving in the next 24 hours.
     *
     * @return
     */
    public Duration nextDay() {
        Duration min = minDuration(day.getAvailable(), week.getAvailable());
        min = minDuration(fortnight.getAvailable(), min);
        return min;
    }

    /**
     * Calculates how much time is available for driving in the next 7 days.
     *
     * @return
     */
    public Duration nextWeek() {
        return minDuration(fortnight.getAvailable(), week.getAvailable());
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

        Duration overlap = driveIntervals.overlap(interval);

        return Driving.NONSTOP.minus(overlap);
    }
}
