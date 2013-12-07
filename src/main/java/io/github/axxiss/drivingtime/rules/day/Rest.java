package io.github.axxiss.drivingtime.rules.day;

import org.joda.time.Duration;


/**
 * Daily rest period.
 */
public enum Rest {
    NORMAL(11),
    SPLIT_LONG(9),
    SPLIT_SHORT(3),
    REDUCED(9);

    private final Duration value;

    Rest(int hours) {
        value = new Duration(hours * 3600000);
    }

    public Duration getValue() {
        return value;
    }
}
