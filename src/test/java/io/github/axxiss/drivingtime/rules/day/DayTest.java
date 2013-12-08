package io.github.axxiss.drivingtime.rules.day;

import io.github.axxiss.drivingtime.BaseTest;
import io.github.axxiss.drivingtime.Hours;
import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static io.github.axxiss.drivingtime.Hours.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class DayTest extends BaseTest {

    IntervalList intervals;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new Date[0]));
    }

    @Test
    public void normal() {
        assertAvailable(2, h0, h9);
        assertAvailable(2, h1, h8);
        assertAvailable(2, h4, h5);
        assertAvailable(2, h8, h1);
        assertAvailable(2, h10, h0);
    }


    @Test
    public void overtime() {
        assertAvailable(0, h0, h10);
        assertAvailable(0, h1, h9);
        assertAvailable(0, h4, h6);
        assertAvailable(0, h9, h1);
        assertAvailable(0, h10, h0);
    }

    @Test
    public void restNormal_found() {
        assertRestNormal(h0, h11, h0);
        assertRestNormal(h0, h11, h5);
        assertRestNormal(h0, h11, h12);
    }

    @Test
    public void restNormal_notFound() {
        assertRestNormal(h11, null, h0);
        assertRestNormal(h10, null, h1);
        assertRestNormal(h6, null, h5);
        assertRestNormal(h0, null, h12);
    }


    @Test
    public void restNormal_reduced() {
        assertRestReduced(h0, 0, h10, h3);
        assertRestReduced(h0, 1, h10, h3);
        assertRestReduced(h0, 2, h10, h3);

        assertRestReduced(h9, 0, null, h0);
        assertRestReduced(h9, 1, null, h0);
        assertRestReduced(h9, 2, null, h0);

        assertRestReduced(h8, 0, null, h1);
        assertRestReduced(h6, 1, null, h3);
        assertRestReduced(h1, 2, null, h8);
    }

    @Test
    public void restNormal_reduced_maxReached() {
        assertRestReduced(null, 3, h10, h3);
        assertRestReduced(null, 3, null, h3);
        assertRestReduced(null, 3, null, h3);

        assertRestReduced(null, 6, h10, h3);
        assertRestReduced(null, 6, null, h3);
        assertRestReduced(null, 6, null, h3);
    }

    @Test
    public void restSplit_found_both() {
        assertRestSplit(h0, h9, h3, h0);
        assertRestSplit(h0, h9, h3, h3);
        assertRestSplit(h0, h9, h3, h6);
        assertRestSplit(h0, h9, h3, h12);

        assertRestSplit(h0, h3, h9, h0);
        assertRestSplit(h0, h3, h9, h3);
        assertRestSplit(h0, h3, h9, h6);
        assertRestSplit(h0, h3, h9, h12);
    }

    @Test
    public void restSplit_found_one() {
        assertRestSplit(h3, h9, null, h0);
        assertRestSplit(h2, h9, null, h1);
        assertRestSplit(h0, h9, null, h3);
        assertRestSplit(h0, h9, null, h6);
        assertRestSplit(h0, h9, null, h12);
    }

    @Test
    public void restSplit_notFound() {
        assertRestSplit(null, null, null, h6);
    }

    private void assertAvailable(int overtime, Hours driving, Hours expected) {
        assertAvailable(overtime, driving, expected, h11);
    }

    private void assertAvailable(int overtime, Hours driving, Hours expected, Hours gap) {

        doReturn(gap.getValue()).when(intervals).findGap(any(Interval.class), any(Duration.class));
        doReturn(driving.getValue()).when(intervals).overlap(any(Interval.class));
        doReturn(overtime).when(intervals).countDurationInterval(any(Interval.class), any(Duration.class), any(Duration.class));

        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), day.getAvailable());
    }

    /**
     * Assert normal rest
     *
     * @param expected Expected pending rest
     * @param rest     Gap found
     * @param last     gap from the las activity.
     */
    private void assertRestNormal(Hours expected, Hours rest, Hours last) {
        Duration gap;
        if (rest == null) {
            gap = null;
        } else {
            gap = rest.getValue();
        }

        doReturn(gap).when(intervals).findGap(any(Interval.class), any(Duration.class));
        doReturn(last.getValue()).when(intervals).lastGap();

        Day day = spy(new Day(intervals, DateTime.now()));

        doReturn(null).when(day).restReduced();
        doReturn(null).when(day).restSplit();

        assertEquals(new Duration(expected.getValue()), day.calcRest());
    }

    /**
     * @param expected
     * @param rest1
     * @param rest2
     * @param last
     */
    private void assertRestSplit(Hours expected, Hours rest1, Hours rest2, Hours last) {
        Duration gap1 = null;
        Duration gap2 = null;

        if (rest1 != null) {
            gap1 = rest1.getValue();
        }

        if (rest2 != null) {
            gap2 = rest2.getValue();
        }

        Duration expectedDuration = null;


        doReturn(gap1)
                .doReturn(gap2)
                .when(intervals).findGap(any(Interval.class), any(Duration.class));

        doReturn(last.getValue()).when(intervals).lastGap();

        if (expected != null) {
            expectedDuration = expected.getValue();
        }

        Day day = new Day(intervals, DateTime.now());
        assertEquals(expectedDuration, day.restSplit());
    }


    /**
     * Assert a reduced rest period.
     *
     * @param expected expected duration
     * @param reduced  amount of reduced rest done in the last 6 days
     */
    private void assertRestReduced(Hours expected, int reduced, Hours rest, Hours last) {
        Duration expectedDuration = expected == null ? null : expected.getValue();
        Duration gap = rest == null ? null : rest.getValue();


        doReturn(reduced).when(intervals).countGaps(any(Interval.class), any(Duration.class), any(Duration.class), any(Duration.class));

        doReturn(gap).when(intervals).findGap(any(Interval.class), any(Duration.class));

        doReturn(last.getValue()).when(intervals).lastGap();


        Day day = new Day(intervals, DateTime.now());
        assertEquals(expectedDuration, day.restReduced());
    }

}
