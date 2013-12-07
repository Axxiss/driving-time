package io.github.axxiss.drivingtime;

import org.joda.time.Duration;

/**
 * Created by alexis on 12/7/13.
 */
public enum Hours {
    h0(0),
    h1(1),
    h2(2),
    h3(3),
    h4(4),
    h5(5),
    h6(6),
    h7(7),
    h8(8),
    h9(9),
    h10(10),
    h11(11),
    h12(12),
    h21(21),
    h24(24),
    h45(45);

    Duration value;

    Hours(int h) {
        value = new Duration(h * 3600000);
    }

    public Duration getValue() {
        return value;
    }
}
