package io.github.axxiss.drivingtime.rules.day;

import org.joda.time.Duration;

/**
 * Created by alexis on 12/7/13.
 */
public enum Drive {
    NORMAL(9),
    OVERTIME(10);

    private final Duration value;

    Drive(int hours) {
        value = new Duration(hours * 3600000);
    }

    public Duration getValue() {
        return value;
    }
}
