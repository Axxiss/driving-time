package io.github.axxiss.drivingtime.rules.day;

import io.github.axxiss.drivingtime.IntervalList;
import io.github.axxiss.drivingtime.Utils;
import io.github.axxiss.drivingtime.rules.Rule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * Definition of the rules that applies in a day.
 */
public class Day extends Rule {

    private static final int MAX_REDUCED = 3;

    /**
     * @param intervals
     * @param when
     */
    public Day(IntervalList intervals, DateTime when) {
        super(when.minusDays(1), when, 9 * hoursToMillis, intervals);
        rest = Rest.NORMAL.getValue();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected Duration available() {
        Interval outer = new Interval(period.getEnd().minusWeeks(1), period.getEnd());

        Duration aDay = new Duration(24 * hoursToMillis);
        int overtime = intervals.countDurationInterval(outer, aDay, max);

        Duration dayMax;

        if (overtime < 2) {
            dayMax = Drive.OVERTIME.getValue();
        } else {
            dayMax = max;
        }

        if (driving.isShorterThan(dayMax)) {
            return dayMax.minus(driving);
        } else {
            return new Duration(0);
        }
    }

    /**
     * On a single day we have three kinds of rest periods.
     * <p/>
     * - normal: 11 hours
     * - split: 9 + 3 hours
     * - reduced: 9 hours, three times a week
     *
     * @return Pending rest time.
     */
    protected Duration calcRest() {
        Duration normal = super.calcRest();
        Duration split = restSplit();
        Duration reduced = restReduced();

        Duration min;

        if (split != null) {
            min = Utils.minDuration(normal, split);
        } else {
            min = normal;
        }

        if (reduced == null) {
            return min;
        }

        return Utils.minDuration(min, reduced);
    }

    /**
     * Check how much time is left to do a reduced rest.
     *
     * @return
     */
    protected Duration restReduced() {
        Interval outer = new Interval(period.getEnd().minusWeeks(1), period.getEnd());
        Duration aDay = new Duration(24 * hoursToMillis);

        int reduced = intervals.countGaps(outer, aDay, Rest.REDUCED.getValue(), Rest.NORMAL.getValue());

        if (reduced >= MAX_REDUCED) {
            return null;
        }

        Duration gap = intervals.findGap(period, Rest.REDUCED.getValue());

        if (gap == null) {
            Duration currentGap = intervals.lastGap();
            return Rest.REDUCED.getValue().minus(currentGap);
        } else {
            return new Duration(0);
        }
    }

    /**
     * Check how much time is left to do a split rest.
     * <p/>
     * Split rest it is done in two periods of 9 and 3 hours.
     *
     * @return duration left to fulfill a split rest.
     */
    protected Duration restSplit() {
        Duration restLong = intervals.findGap(period, Rest.SPLIT_LONG.getValue());
        Duration restShort = intervals.findGap(period, Rest.SPLIT_SHORT.getValue());

        if (restLong == null) {
            if (restShort == null) {
                return null;
            } else {
                return Rest.SPLIT_LONG.getValue().minus(intervals.lastGap());
            }
        } else {
            if (restShort == null) {
                return Rest.SPLIT_SHORT.getValue().minus(intervals.lastGap());
            } else {
                return new Duration(0);
            }
        }
    }


}
