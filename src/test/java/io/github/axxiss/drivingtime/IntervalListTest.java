package io.github.axxiss.drivingtime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        List<Calendar> list = new ArrayList<Calendar>(1);
        list.add(Calendar.getInstance());
        new IntervalList(list);
    }

    @Test
    public void construction() {
        int size = 30;
        List<Calendar> dates = new ArrayList<>(30);

        for (int i = 0; i < size; i++) {
            dates.add(Calendar.getInstance());
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
