package io.github.axxiss.drivingtime;

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
    h21(21),
    h24(24),
    h45(45);

    long value;

    Hours(int h) {
        value = h * 3600000;
    }

    public long getValue() {
        return value;
    }
}
