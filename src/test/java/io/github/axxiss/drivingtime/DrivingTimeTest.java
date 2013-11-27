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
        DateTime now = DateTime.now();

        DateTime dt = now;


        Duration day = new Duration(now.getMillis(), dt.plusHours(0).getMillis());
        Duration week = new Duration(now.getMillis(), dt.plusHours(40).getMillis());
        Duration fortnight = new Duration(now.getMillis(), dt.plusHours(80).getMillis());

        doReturn(day).when(drivingTime).lastDay(any(DateTime.class));
        doReturn(week).when(drivingTime).lastWeek(any(DateTime.class));
        doReturn(fortnight).when(drivingTime).lastFortnight(any(DateTime.class));


        Duration available = drivingTime.nextDay();

        assertEquals(8.0, available.getStandardHours(), 0);
    }
}
