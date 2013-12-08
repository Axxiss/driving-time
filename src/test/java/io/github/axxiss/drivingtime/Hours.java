package io.github.axxiss.drivingtime;

import org.joda.time.Duration;

/**
 * Created by alexis on 12/7/13.
 */
public enum Hours {
    hNull(-1),
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
    h14(14),
    h15(15),
    h18(18),
    h20(20),
    h21(21),
    h24(24),
    h30(30),
    h45(45);

    Duration value;

    Hours(int h) {
        if (h < 0) {
            value = null;
        } else {
            value = new Duration(h * 3600000);
        }
    }

    public Duration getValue() {
        return value;
    }
}
