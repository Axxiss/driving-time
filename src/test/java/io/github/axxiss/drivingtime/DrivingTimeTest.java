package io.github.axxiss.drivingtime;

import io.github.axxiss.drivingtime.rules.Fortnight;
import io.github.axxiss.drivingtime.rules.day.Day;
import io.github.axxiss.drivingtime.rules.week.Week;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static io.github.axxiss.drivingtime.Hours.*;
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

        drivingTime = new DrivingTime(day, week, fortnight, null);
    }


    @Test
    public void nextDay() {
        assertNextDay(h0, h0, h0, h0);
        assertNextDay(h1, h0, h0, h0);
        assertNextDay(h3, h0, h0, h0);
        assertNextDay(h8, h0, h0, h0);

        assertNextDay(h1, h4, h10, h1);
        assertNextDay(h3, h4, h10, h3);
        assertNextDay(h8, h4, h10, h4);

        assertNextDay(h1, h10, h3, h1);
        assertNextDay(h3, h10, h3, h3);
        assertNextDay(h8, h10, h3, h3);
    }

    @Test
    public void nextWeek() {
        assertNextWeek(h0, h0, h0);

        assertNextWeek(h1, h8, h1);
        assertNextWeek(h5, h8, h5);
        assertNextWeek(h8, h8, h8);

        assertNextWeek(h1, h5, h1);
        assertNextWeek(h5, h5, h5);
        assertNextWeek(h8, h5, h5);

    }

    private void assertNextDay(Hours day, Hours week, Hours fortnight, Hours expected) {
        mockTime(day, week, fortnight);
        assertEquals(expected.getValue(), drivingTime.nextDay());
    }

    private void assertNextWeek(Hours week, Hours fortnight, Hours expected) {
        mockTime(h0, week, fortnight);
        assertEquals(expected.getValue(), drivingTime.nextWeek());
    }

    private void mockTime(Hours day, Hours week, Hours fortnight) {
        when(this.day.getAvailable()).thenReturn(day.getValue());
        when(this.week.getAvailable()).thenReturn(week.getValue());
        when(this.fortnight.getAvailable()).thenReturn(fortnight.getValue());
    }
}
