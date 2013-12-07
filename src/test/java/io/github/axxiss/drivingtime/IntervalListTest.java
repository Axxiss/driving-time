package io.github.axxiss.drivingtime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class IntervalListTest {

    @Test(expected = IllegalArgumentException.class)
    public void evenArguments() {
        Date[] dates = new Date[1];
        dates[0] = new Date();
        new IntervalList(dates);
    }

    @Test
    public void construction() {
        int size = 30;
        Date[] dates = new Date[size];

        for (int i = 0; i < size; i++) {
            dates[i] = new Date();
        }

        IntervalList list = new IntervalList(dates);
        assertEquals(size / 2, list.size());
    }

    @Test
    public void overlap() {

    }

    @Test
    public void findGap() {

    }

    @Test
    public void countIntervalDuration() {

    }

}
