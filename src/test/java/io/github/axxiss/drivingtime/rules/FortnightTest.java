package io.github.axxiss.drivingtime.rules;

import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
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
public class FortnightTest {

    IntervalList intervals;

    Fortnight fortnight;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new ArrayList<Calendar>()));
    }

    @Test
    public void calcAvailable() {
        mockData(0);
        fortnight = new Fortnight(intervals, DateTime.now());
        assertEquals(fortnight.max, fortnight.getAvailable());
    }

    private void mockData(long overlap) {
        Duration d = new Duration(overlap);
        doReturn(overlap).when(intervals).overlap(any(Interval.class));
    }


}
