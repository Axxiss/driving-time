package io.github.axxiss.drivingtime.rules.week;

import io.github.axxiss.drivingtime.BaseTest;
import io.github.axxiss.drivingtime.Hours;
import io.github.axxiss.drivingtime.IntervalList;
import io.github.axxiss.drivingtime.rules.day.Day;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static io.github.axxiss.drivingtime.Hours.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 1:46 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class WeekTest extends BaseTest {


    IntervalList intervals;

    Week week;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new Date[0]));
    }

    @Test
    public void calcAvailable() {
        mockData(0);
        week = new Week(intervals, DateTime.now());
        assertEquals(week.getMax(), week.getAvailable());
    }


    @Test
    public void rest() {
        assertRest(h0, h45);
        assertRest(h21, h24);
        assertRest(h45, h0);
    }

    @Test
    public void restReduced_notFound() {
        assertRestReduced(h24, h45, h0);
    }

    @Test
    public void isLastRestReduced() {
        Week week = new Week(intervals, DateTime.now());

        doReturn(null).when(intervals).findGap(any(Interval.class), any(Duration.class));
        assertTrue(week.isLastRestReduced());

        doReturn(h45.getValue()).when(intervals).findGap(any(Interval.class), any(Duration.class));
        assertFalse(week.isLastRestReduced());
    }


    private void mockData(long overlap) {
        Duration d = new Duration(overlap);
        doReturn(d).when(intervals).overlap(any(Interval.class));
    }

    private void assertAvailable(long driving, long expected, long gap) {

        doReturn(new Duration(gap)).when(intervals).findGap(any(Interval.class), any(Duration.class));
        doReturn(new Duration(driving)).when(intervals).overlap(any(Interval.class));

        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected), day.getAvailable());
    }

    private void assertRest(Hours expected, Hours rest) {
        doReturn(new Duration(rest.getValue())).when(intervals).findGap(any(Interval.class), any(Duration.class));
        Week week = new Week(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), week.calcRest());
    }

    private void assertRestReduced(Hours expected, Hours rest, Hours last) {

        Duration gap = rest == null ? null : rest.getValue();


        doReturn(gap).when(intervals).findGap(any(Interval.class), any(Duration.class));


        Week week = spy(new Week(intervals, DateTime.now()));
        assertEquals(expected.getValue(), week.restReduced());
    }
}
