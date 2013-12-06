package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Day extends Rule {

    protected final static Duration maxOvertime = new Duration(10 * hoursToMillis);

    protected final static Duration rest = new Duration(11 * hoursToMillis);

    public Day(IntervalList intervals, DateTime when) {
        super(when.minusDays(1), when, 9 * hoursToMillis, intervals);
    }


    @Override
    protected Duration calcAvailable() {

        Duration gap = intervals.findGap(period, rest);

        if (gap.isShorterThan(rest)) {
            return new Duration(0);
        }

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
