package io.github.axxiss.drivingtime;

import org.joda.time.Duration;

/**
 * @author Alexis Mas
 */
public class Driving {


    private static final long minutesToMillis = 60 * 1000;
    private static final long hoursToMillis = 60 * minutesToMillis;


    /**
     * Normal daily driving time, 9 hours.
     */
    public static final Duration DAILY = new Duration(8 * hoursToMillis);


    /**
     * Maximum nonstop driving time, 4,5 hours
     */
    public static final Duration NONSTOP = new Duration(4 * hoursToMillis + (30 * minutesToMillis));

    /**
     * * Maximum weekly driving time, 56 hours
     */
    public static final Duration WEEKLY = new Duration(56 * hoursToMillis);

    /**
     * * Maximum fortnightly driving time, 90 hours
     */
    public static final Duration FORTNIGHTLY = new Duration(90 * hoursToMillis);
}
