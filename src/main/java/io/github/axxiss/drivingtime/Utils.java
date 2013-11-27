package io.github.axxiss.drivingtime;

import org.joda.time.Duration;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/27/13
 * Time: 9:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {
    /**
     * Given a maximum and a current duration calculates the difference between the maximum
     * and the current duration. If the duration is greater than the maximum the duration returned
     * will be zero.
     *
     * @param maximum the maximum duration.
     * @param current the current duration.
     * @return maximum - current
     */
    public static Duration available(Duration maximum, Duration current) {
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
    public static Duration minDuration(Duration a, Duration b) {
        if (a.isShorterThan(b)) {
            return a;
        } else {
            return b;
        }
    }
}
