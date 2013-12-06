package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.BaseTest;
import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class DayTest extends BaseTest {

    IntervalList intervals;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new Date[0]));
    }

    @Test
    public void normal() {
        assertAvailable(2, 0, nineHours);
        assertAvailable(2, oneHour, eightHours);
        assertAvailable(2, fourHours, fiveHours);
        assertAvailable(2, eightHours, oneHour);
        assertAvailable(2, tenHours, 0);
    }


    @Test
    public void overtime() {
        assertAvailable(0, 0, tenHours);
        assertAvailable(0, oneHour, nineHours);
        assertAvailable(0, fourHours, sixHours);
        assertAvailable(0, nineHours, oneHour);
        assertAvailable(0, tenHours, 0);
    }

    @Test
    public void rest() {
        assertAvailable(0, 0, 0, sixHours);
        assertAvailable(2, 0, nineHours, twelveHours);
        assertAvailable(2, threeHours, sixHours, twelveHours);
    }

    private void assertAvailable(int overtime, long driving, long expected) {
        assertAvailable(overtime, driving, expected, twelveHours);
    }

    private void assertAvailable(int overtime, long driving, long expected, long gap) {

        doReturn(new Duration(gap)).when(intervals).findGap(any(Interval.class), any(Duration.class));
        doReturn(new Duration(driving)).when(intervals).overlap(any(Interval.class));
        doReturn(overtime).when(intervals).countDurationInterval(any(Interval.class), any(Duration.class), any(Duration.class));

        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected), day.getAvailable());
    }
}
