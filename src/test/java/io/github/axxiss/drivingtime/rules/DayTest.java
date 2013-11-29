package io.github.axxiss.drivingtime.rules;

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
public class DayTest {

    IntervalList intervals;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new Date[0]));
    }

    @Test
    public void calcAvailable_normal() {
        mockData(2, 0);
        Day day = new Day(intervals, DateTime.now());
        assertEquals(day.getMax(), day.getAvailable());


        long twoHours = 2 * Rule.hoursToMillis;
        mockData(2, twoHours);
        Day aDay = new Day(intervals, DateTime.now());
        assertEquals(aDay.getMax().minus(twoHours), aDay.getAvailable());
    }


    @Test
    public void calcAvailable_overtime() {
        mockData(1, 0);
        Day day = new Day(intervals, DateTime.now());
        assertEquals(day.maxOvertime, day.getAvailable());
    }

    private void mockData(int countDuration, long overlap) {
        Duration d = new Duration(overlap);
        doReturn(d).when(intervals).overlap(any(Interval.class));
        doReturn(countDuration).when(intervals).countDurationInterval(any(Interval.class), any(Duration.class), any(Duration.class));
    }
}
