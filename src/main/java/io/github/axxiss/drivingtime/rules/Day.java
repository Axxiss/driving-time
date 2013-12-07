package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * Definition of the rules that applies in a day.
 */
public class Day extends Rule {

    /**
     * Maximum allowed overtime to drive no more than two days a week.
     */
    protected final static Duration maxOvertime = new Duration(10 * hoursToMillis);

    /**
     * @param intervals
     * @param when
     */
    public Day(IntervalList intervals, DateTime when) {
        super(when.minusDays(1), when, 9 * hoursToMillis, intervals);
        rest = new Duration(11 * hoursToMillis);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Duration calcAvailable() {
        Interval outer = new Interval(period.getEnd().minusWeeks(1), period.getEnd());

        Duration aDay = new Duration(24 * hoursToMillis);
        int overtime = intervals.countDurationInterval(outer, aDay, max);

        Duration dayMax;

        if (overtime < 2) {
            dayMax = maxOvertime;
        } else {
            dayMax = max;
        }

        if (driving.isShorterThan(dayMax)) {
            return dayMax.minus(driving);
        } else {
            return new Duration(0);
        }
    }
}
