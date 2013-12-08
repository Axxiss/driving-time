package io.github.axxiss.drivingtime.rules.week;

import io.github.axxiss.drivingtime.BaseTest;
import io.github.axxiss.drivingtime.Hours;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static io.github.axxiss.drivingtime.Hours.*;
import static org.junit.Assert.assertEquals;
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

    @Test
    public void calcAvailable() {
        mockOverlap(h0);
        Week week = new Week(intervals, DateTime.now());
        assertEquals(week.getMax(), week.getAvailable());
    }


    @Test
    public void rest() {
        assertRest(h0, h45);
        assertRest(h21, h24);
        assertRest(h45, h0);
    }

    @Test
    public void restReduced_lastWeekNormal() {
        assertRestReduced(h24, h45, h0);
        assertRestReduced(h12, h45, h12);
        assertRestReduced(h3, h45, h21);
        assertRestReduced(h0, h45, h24);
    }

    @Test
    public void restReduced_lastWeekReduced() {
        assertRestReduced(hNull, h30, h0);
        assertRestReduced(hNull, h30, h12);
        assertRestReduced(hNull, h30, h21);
        assertRestReduced(hNull, h30, h24);
    }

    @Test
    public void lastWeekRest() {
        Week week = new Week(intervals, DateTime.now());

        mockFindGap(h30);
        assertEquals(h30.getValue(), week.lastWeekRest());

        mockFindGap(h45);
        assertEquals(h45.getValue(), week.lastWeekRest());
    }

    @Test
    public void restRecovery_completed() {
        assertRestRecovery(h0, h24, h21, h0);
        assertRestRecovery(h0, h24, h21, h10);
        assertRestRecovery(h0, h24, h21, h30);

        assertRestRecovery(h0, h30, h11, h0);
        assertRestRecovery(h0, h30, h11, h10);
        assertRestRecovery(h0, h30, h11, h30);
    }

    @Test
    public void restRecovery_uncompleted() {
        assertRestRecovery(h18, h24, hNull, h3);
        assertRestRecovery(h1, h24, hNull, h20);
        assertRestRecovery(h1, h30, hNull, h10);
    }

    private void assertRest(Hours expected, Hours rest) {
        mockFindGap(rest);

        Week week = new Week(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), week.calcRest());
    }

    private void assertRestReduced(Hours expected, Hours rest, Hours last) {
        mockLastGap(last);

        Week week = spy(new Week(intervals, DateTime.now()));

        doReturn(rest.getValue()).when(week).lastWeekRest();

        assertEquals(expected.getValue(), week.restReduced());
    }

    private void assertRestRecovery(Hours expected, Hours first, Hours second, Hours last) {
        Week week = spy(new Week(intervals, DateTime.now()));
        assertEquals(expected.getValue(), week.restRecovery());
    }
}
