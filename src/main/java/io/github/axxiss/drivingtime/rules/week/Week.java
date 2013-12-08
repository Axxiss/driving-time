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
        if (isLastRestReduced()) {
            return null;
        }

        return Utils.safeMinus(Rest.REDUCED.getValue(), intervals.lastGap());
    }

    /**
     * Check is last week rest was reduced or not.
     *
     * @return
     */
    protected boolean isLastRestReduced() {
        DateTime start = period.getStart().minusWeeks(1);
        DateTime end = period.getEnd().minusWeeks(1);

        Interval lastWeek = new Interval(start, end);

        return intervals.findGap(lastWeek, Rest.NORMAL.getValue()) == null;
    }
}
