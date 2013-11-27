package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.joda.time.Duration;
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
 * Date: 11/18/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class DrivingTimeTest {

    DrivingTime drivingTime;

    @Before
    public void setUp() throws Exception {
        drivingTime = spy(new DrivingTime(new Date[0]));
    }

    @Test
    public void lastDay() {

    }

    @Test
    public void lastWeek() {

    }

    @Test
    public void lastFortnight() {

    }

    @Test
    public void nextDay() {

        Duration day = new Duration(0);
        Duration week = new Duration(40 * Driving.hoursToMillis);
        Duration fortnight = new Duration(80 * Driving.hoursToMillis);

        mockWorkTime(day, week, fortnight);

        Duration available = drivingTime.nextDay();

        assertEquals(9.0, available.getStandardHours(), 0);
    }

    @Test
    public void nextWeek() {
        Duration day = new Duration(0);
        Duration fortnight = new Duration(34 * Driving.hoursToMillis);

        mockWorkTime(day, day, fortnight);

        Duration available = drivingTime.nextWeek();
        assertEquals(56.0, available.getStandardHours(), 0);
    }

    /**
     * Do a partial mock of driving time, returning the passed duration when
     * {@link DrivingTime#lastDay(org.joda.time.DateTime)},
     * {@link DrivingTime#lastWeek(org.joda.time.DateTime)} or
     * {@link DrivingTime#lastFortnight(org.joda.time.DateTime)} are called.
     *
     * @param day       the mock duration of a last day.
     * @param week      the mock duration of a last week.
     * @param fortnight the mock duration of a last fortnight.
     */
    private void mockWorkTime(Duration day, Duration week, Duration fortnight) {
        doReturn(day).when(drivingTime).lastDay(any(DateTime.class));
        doReturn(week).when(drivingTime).lastWeek(any(DateTime.class));
        doReturn(fortnight).when(drivingTime).lastFortnight(any(DateTime.class));
    }
}
