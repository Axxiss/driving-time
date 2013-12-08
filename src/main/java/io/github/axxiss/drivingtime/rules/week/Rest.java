package io.github.axxiss.drivingtime.rules.week;

import org.joda.time.Duration;

/**
 * Weekly rest time.
 */
public enum Rest {
    NORMAL(45),
    REDUCED(24);

    private final Duration value;

    Rest(int hours) {
        value = new Duration(hours * 3600000);
    }

    public Duration getValue() {
        return value;
    }
}
