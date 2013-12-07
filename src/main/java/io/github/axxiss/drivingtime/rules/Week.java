package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import io.github.axxiss.drivingtime.Utils;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Week extends Rule {
    public Week(IntervalList intervals, DateTime when) {
        super(when.minusWeeks(1), when, 56 * hoursToMillis, intervals);
        rest = new Duration(45 * hoursToMillis);
    }

    @Override
    protected Duration available() {
        return Utils.safeMinus(max, driving);
    }
}
