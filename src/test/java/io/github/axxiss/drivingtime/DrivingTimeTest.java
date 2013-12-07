package io.github.axxiss.drivingtime;

import io.github.axxiss.drivingtime.rules.Fortnight;
import io.github.axxiss.drivingtime.rules.Week;
import io.github.axxiss.drivingtime.rules.day.Day;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/18/13
 * Time: 2:53 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class DrivingTimeTest extends BaseTest {
    Day day;
    Week week;
    Fortnight fortnight;

    @Before
    public void setUp() throws Exception {
        day = mock(Day.class);
        week = mock(Week.class);
        fortnight = mock(Fortnight.class);

        drivingTime = new DrivingTime(day, week, fortnight);
    }


    @Test
    public void nextDay() {
        assertNextDay(0, 0, 0, new Duration(0));
        assertNextDay(oneHour, 0, 0, new Duration(0));
        assertNextDay(threeHours, 0, 0, new Duration(0));
        assertNextDay(eightHours, 0, 0, new Duration(0));

        assertNextDay(oneHour, fourHours, tenHours, new Duration(oneHour));
        assertNextDay(threeHours, fourHours, tenHours, new Duration(threeHours));
        assertNextDay(eightHours, fourHours, tenHours, new Duration(fourHours));

        assertNextDay(oneHour, tenHours, threeHours, new Duration(oneHour));
        assertNextDay(threeHours, tenHours, threeHours, new Duration(threeHours));
        assertNextDay(eightHours, tenHours, threeHours, new Duration(threeHours));
    }

    @Test
    public void nextWeek() {
        assertNextWeek(0, 0, new Duration(0));

        assertNextWeek(oneHour, eightHours, new Duration(oneHour));
        assertNextWeek(fiveHours, eightHours, new Duration(fiveHours));
        assertNextWeek(eightHours, eightHours, new Duration(eightHours));

        assertNextWeek(oneHour, fiveHours, new Duration(oneHour));
        assertNextWeek(fiveHours, fiveHours, new Duration(fiveHours));
        assertNextWeek(eightHours, fiveHours, new Duration(fiveHours));

    }

    private void assertNextDay(long dayMillis, long weekMillis, long fortnightMillis, Duration expected) {
        mockTime(dayMillis, weekMillis, fortnightMillis);
        assertEquals(expected, drivingTime.nextDay());
    }

    private void assertNextWeek(long weekMillis, long fortnightMillis, Duration expected) {
        mockTime(0, weekMillis, fortnightMillis);
        assertEquals(expected, drivingTime.nextWeek());
    }

    private void mockTime(long dayMillis, long weekMillis, long fortnightMillis) {
        Duration dayDuration = new Duration(dayMillis);
        Duration weekDuration = new Duration(weekMillis);
        Duration fortnightDuration = new Duration(fortnightMillis);

        when(day.getAvailable()).thenReturn(dayDuration);
        when(week.getAvailable()).thenReturn(weekDuration);
        when(fortnight.getAvailable()).thenReturn(fortnightDuration);
    }
}
