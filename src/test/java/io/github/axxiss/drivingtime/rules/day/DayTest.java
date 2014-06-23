package io.github.axxiss.drivingtime.rules.day;

import io.github.axxiss.drivingtime.BaseTest;
import io.github.axxiss.drivingtime.Hours;
import io.github.axxiss.drivingtime.IntervalList;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.github.axxiss.drivingtime.Hours.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(JUnit4.class)
public class DayTest extends BaseTest {

    @Test
    public void normal() {
        assertAvailable(2, h0, h9);
        assertAvailable(2, h1, h8);
        assertAvailable(2, h4, h5);
        assertAvailable(2, h8, h1);
        assertAvailable(2, h10, h0);
    }


    @Test
    public void overtime() {
        assertAvailable(0, h0, h10);
        assertAvailable(0, h1, h9);
        assertAvailable(0, h4, h6);
        assertAvailable(0, h9, h1);
        assertAvailable(0, h10, h0);
    }

    @Test
    public void restNormal_found() {
        assertRestNormal(h0, h11, h0);
        assertRestNormal(h0, h11, h5);
        assertRestNormal(h0, h11, h12);
    }

    @Test
    public void restNormal_notFound() {
        assertRestNormal(h11, hNull, h0);
        assertRestNormal(h10, hNull, h1);
        assertRestNormal(h6, hNull, h5);
        assertRestNormal(h0, hNull, h12);
    }


    @Test
    public void restNormal_reduced() {
        assertRestReduced(h0, 0, h10, h3);
        assertRestReduced(h0, 1, h10, h3);
        assertRestReduced(h0, 2, h10, h3);

        assertRestReduced(h9, 0, hNull, h0);
        assertRestReduced(h9, 1, hNull, h0);
        assertRestReduced(h9, 2, hNull, h0);

        assertRestReduced(h8, 0, hNull, h1);
        assertRestReduced(h6, 1, hNull, h3);
        assertRestReduced(h1, 2, hNull, h8);
    }

    @Test
    public void restNormal_reduced_maxReached() {
        assertRestReduced(hNull, 3, h10, h3);
        assertRestReduced(hNull, 3, hNull, h3);
        assertRestReduced(hNull, 3, hNull, h3);

        assertRestReduced(hNull, 6, h10, h3);
        assertRestReduced(hNull, 6, hNull, h3);
        assertRestReduced(hNull, 6, hNull, h3);
    }

    @Test
    public void restSplit_found_both() {
        assertRestSplit(h0, h9, h3, h0);
        assertRestSplit(h0, h9, h3, h3);
        assertRestSplit(h0, h9, h3, h6);
        assertRestSplit(h0, h9, h3, h12);

        assertRestSplit(h0, h3, h9, h0);
        assertRestSplit(h0, h3, h9, h3);
        assertRestSplit(h0, h3, h9, h6);
        assertRestSplit(h0, h3, h9, h12);
    }

    @Test
    public void restSplit_found_one() {
        assertRestSplit(h3, h9, hNull, h0);
        assertRestSplit(h2, h9, hNull, h1);
        assertRestSplit(h0, h9, hNull, h3);
        assertRestSplit(h0, h9, hNull, h6);
        assertRestSplit(h0, h9, hNull, h12);
    }

    @Test
    public void restSplit_notFound() {
        assertRestSplit(hNull, hNull, hNull, h6);
    }

    private void assertAvailable(int overtime, Hours driving, Hours expected) {
        assertAvailable(overtime, driving, expected, h11);
    }

    private void assertAvailable(int overtime, Hours driving, Hours expected, Hours gap) {
        mockFindGap(gap);
        mockOverlap(driving);
        mockCountDurationInterval(overtime);

        Day day = new Day(intervals, DateTime.now());
        assertEquals(new Duration(expected.getValue()), day.getAvailable());
    }

    /**
     * Assert normal rest
     *
     * @param expected Expected pending rest
     * @param rest     Gap found
     * @param last     gap from the las activity.
     */
    private void assertRestNormal(Hours expected, Hours rest, Hours last) {
        mockFindGap(rest);
        mockLastGap(last);

        Day day = spy(new Day(intervals, DateTime.now()));

        doReturn(null).when(day).restReduced();
        doReturn(null).when(day).restSplit();

        assertEquals(new Duration(expected.getValue()), day.calcRest());
    }

    /**
     * @param expected
     * @param rest1
     * @param rest2
     * @param last
     */
    private void assertRestSplit(Hours expected, Hours rest1, Hours rest2, Hours last) {
        mockFindGap(rest1, rest2);
        mockLastGap(last);

        Day day = new Day(intervals, DateTime.now());
        assertEquals(expected.getValue(), day.restSplit());
    }


    /**
     * Assert a reduced rest period.
     *
     * @param expected expected duration
     * @param reduced  amount of reduced rest done in the last 6 days
     */
    private void assertRestReduced(Hours expected, int reduced, Hours rest, Hours last) {
        mockCountGaps(reduced);
        mockFindGap(rest);
        mockLastGap(last);

        Day day = new Day(intervals, DateTime.now());
        assertEquals(expected.getValue(), day.restReduced());
    }
}
