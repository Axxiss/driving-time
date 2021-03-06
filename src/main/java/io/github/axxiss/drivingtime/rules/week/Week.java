package io.github.axxiss.drivingtime.rules.week;

import io.github.axxiss.drivingtime.IntervalList;
import io.github.axxiss.drivingtime.Utils;
import io.github.axxiss.drivingtime.rules.Rule;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

/**
 * Rules applied to a week.
 */
public class Week extends Rule {
    public Week(IntervalList intervals, DateTime when) {
        super(when.minusWeeks(1), when, 56 * hoursToMillis, intervals);
        rest = Rest.NORMAL.getValue();
    }

    @Override
    protected Duration available() {
        return Utils.safeMinus(max, driving);
    }


    /**
     * Calculates how much time is left to complete a reduced rest.
     *
     * @return
     */
    protected Duration restReduced() {
        Duration gap = lastWeekRest();
        if (gap.isShorterThan(Rest.NORMAL.getValue())) {
            return null;
        }

        return Utils.safeMinus(Rest.REDUCED.getValue(), intervals.lastGap());
    }

    /**
     * Check is last week rest was reduced or not.
     *
     * @return true if last week rest was reduced
     */
    protected Duration lastWeekRest() {
        DateTime start = period.getStart().minusWeeks(1);
        DateTime end = period.getEnd().minusWeeks(1);

        Interval lastWeek = new Interval(start, end);

        return intervals.findGap(lastWeek, Rest.REDUCED.getValue());
    }


    /**
     * @return
     */
    protected Duration restRecovery() {
        Duration gap = lastWeekRest();

        if (gap.isShorterThan(Rest.NORMAL.getValue())) {
            DateTime start = period.getStart().minusWeeks(1);
            DateTime end = period.getEnd();

            Interval lastTwoWeek = new Interval(start, end);


            Duration pending = Rest.NORMAL.getValue().minus(gap);
            Duration found = intervals.findGap(lastTwoWeek, pending);

            if (found == null) {
                found = intervals.lastGap();
            }

            return Utils.safeMinus(pending, found);
        }

        return new Duration(0);
    }
}
