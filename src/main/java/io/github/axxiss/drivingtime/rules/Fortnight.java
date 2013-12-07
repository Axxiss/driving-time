package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import io.github.axxiss.drivingtime.Utils;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class Fortnight extends Rule {

    public Fortnight(IntervalList intervals, DateTime when) {
        super(when.minusWeeks(2), when, 90 * hoursToMillis, intervals);
        rest = new Duration(0);
    }

    @Override
    protected Duration available() {
        return Utils.safeMinus(max, driving);
    }
}
