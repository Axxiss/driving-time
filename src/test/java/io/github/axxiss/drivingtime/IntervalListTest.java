package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class IntervalListTest {

    List<Calendar> times;


    @Before
    public void setUp() throws Exception {
        String[] moments = {
        "01/01/2014 11:11","01/01/2014 11:28",
        "01/01/2014 11:29","01/01/2014 12:08",
        "01/01/2014 12:14","01/01/2014 12:42",
        "01/01/2014 13:37","01/01/2014 14:17",
        "01/01/2014 14:20","01/01/2014 14:23",
        "01/01/2014 14:35","01/01/2014 14:45",
        "01/01/2014 15:03","01/01/2014 15:30",
        "01/01/2014 16:01","01/01/2014 16:25",
        "01/01/2014 16:27","01/01/2014 16:35",
        "01/01/2014 16:37","01/01/2014 16:42",
        "01/01/2014 16:44","01/01/2014 16:59",
        "01/01/2014 17:25","01/01/2014 17:34",
        "01/01/2014 17:36","01/01/2014 18:02",
        "01/01/2014 18:04","01/01/2014 18:29",
        "01/01/2014 18:30","01/01/2014 18:57",
        "01/01/2014 19:00","01/01/2014 19:01",
        "01/01/2014 19:04","01/01/2014 19:08"};


        times = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for(String m :moments){
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(m));
            times.add(cal);
        }

    }

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
    public void overlap() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Calendar start = Calendar.getInstance();
        start.setTime(sdf.parse("01/01/2014 17:30"));

        Calendar end = Calendar.getInstance();
        end.setTime(sdf.parse("02/01/2014 17:30"));


        Interval interval = new Interval(start.getTimeInMillis(), end.getTimeInMillis());
        
        
        IntervalList intervalList = new IntervalList(times);
        Duration overlap = new Duration(intervalList.overlap(interval));
        assertNotNull(overlap);
        //87 minutos
        assertEquals(87, overlap.getStandardMinutes());
    }


//    @Test
//    public void overlap_data() throws IOException, ClassNotFoundException, ParseException {
//        IntervalList intervals;
//        FileInputStream fis = new FileInputStream("/home/alexis/intervals");
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        intervals = (IntervalList) ois.readObject();
//        ois.close();
//
//        assertNotNull(intervals);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//        Calendar start = Calendar.getInstance();
//        start.setTime(sdf.parse("28/01/2014 17:30"));
//
//        Calendar end = Calendar.getInstance();
//        end.setTime(sdf.parse("29/01/2014 17:30"));
//
//
////        Interval interval = new Interval(start.getTimeInMillis(), end.getTimeInMillis());
//        Interval interval = new Interval(DateTime.now().minusDays(1).getMillis(), DateTime.now().getMillis());
//
//        assertTrue(intervals.overlap(interval) > 0);
//    }

    @Test
    public void findGap() {

    }

    @Test
    public void countIntervalDuration() {

    }

}
