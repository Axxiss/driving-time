package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link DrivingTime}.
 *
 * @author Alexis Mas
 */
@RunWith(JUnit4.class)
public class DrivingTimeTest {

    private static final String NO_TIME_EXPECTED = "No remaining time expected.";

    /**
     * Completed working time intervals, i.e. no driving time available.
     */
    private Date[] completedWorkingTime;


    /**
     * Current time.
     */
    private final DateTime now = new DateTime();

    private DrivingTime drivingTime;


    @Before
    public void setUp() {
        completedWorkingTime = new Date[30];

        DateTime start = now.minusDays(15);
        DateTime end = start.plusHours(8);


        for (int i = 0; i < completedWorkingTime.length; i += 2) {
            completedWorkingTime[i] = start.toDate();
            completedWorkingTime[i + 1] = end.toDate();

            start = start.plusDays(1);
            end = end.plusDays(1);
        }

        drivingTime = new DrivingTime(completedWorkingTime);
        drivingTime.analyze(now);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDrivingTime_oddInput() {
        DrivingTime dt = new DrivingTime(new Date[]{now.toDate()});
    }


    @Test
    public void testFullWorkingTime() {
        assertEquals(NO_TIME_EXPECTED, 0, drivingTime.day().getMillis());
        assertEquals(NO_TIME_EXPECTED, 0, drivingTime.week().getMillis());
        assertEquals(NO_TIME_EXPECTED, 0, drivingTime.fortnight().getMillis());
    }
}
