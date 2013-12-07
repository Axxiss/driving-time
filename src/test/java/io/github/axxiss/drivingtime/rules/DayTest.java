package io.github.axxiss.drivingtime.rules;

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
    public void rest() {
        assertRest(h0, h11);
        assertRest(h5, h6);
        assertRest(h11, h0);
    }


    private void assertAvailable(int overtime, Hours driving, Hours expected) {
        assertAvailable(overtime, driving, expected, h11);
    }

    private void assertAvailable(int overtime, Hours driving, Hours expected, Hours gap) {

        doReturn(new Duration(gap.getValue())).when(intervals).findGap(any(Interval.class), any(Duration.class));
        doReturn(new Duration(driving.getValue())).when(intervals).overlap(any(Interval.class));
        doReturn(overtime).when(intervals).countDurationInterval(any(Interval.class), any(Duration.class), any(Duration.class));

        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), day.getAvailable());
    }

    private void assertRest(Hours expected, Hours rest) {
        doReturn(new Duration(rest.getValue())).when(intervals).findGap(any(Interval.class), any(Duration.class));
        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), day.calcRest());
    }
}
