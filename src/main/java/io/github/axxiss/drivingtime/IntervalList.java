package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * IntervalList adds time manipulation functionality to an {@link ArrayList}.
 */
public class IntervalList extends ArrayList<Interval> {

    /**
     * Creates a new list with the given dates.
     *
     * @param intervals size must be odd.
     */
    public IntervalList(List<Calendar> intervals) {
        super(intervals.size());

        int size = intervals.size();
        if (size % 2 != 0) {
            throw new IllegalArgumentException("Intervals parameter must be an odd number.");
        }

        Iterator<Calendar> it = intervals.iterator();

        while (it.hasNext()) {
            Calendar start = it.next();
            Calendar end = it.next();
            add(new Interval(start.getTimeInMillis(), end.getTimeInMillis()));
        }
    }

    /**
     * Given an interval return the amount of time that the interval list overlap.
     *
     * @param interval the interval to check the overlap.
     * @return the overlap.
     */
    public Duration overlap(Interval interval) {
        Duration amount = new Duration(0);

        //To handle large amount of data it is best to use binary search.
        for (Interval i : this) {
            Interval match = interval.overlap(i);

            if (match != null) {
                amount.plus(match.toDurationMillis());
            }
        }

        return amount;
    }

    /**
     * Count how much time is present inside {@code outer}
     *
     * @param outer
     * @param next
     * @param threshold
     * @return
     */
    public int countDurationInterval(Interval outer, Duration next, Duration threshold) {
        int count = 0;
        int index = overlapStart(outer);

        Interval inner = new Interval(outer.getStartMillis(), outer.getStartMillis() + next.getMillis());

        long duration = next.getMillis();
        while (index < size() - 1) {
            long start = inner.getStartMillis() + duration;
            long end = inner.getEndMillis() + duration;

            inner = new Interval(start, end);

            if (overlap(inner).isLongerThan(threshold)) {
                count++;
            }
            index++;
        }

        return count;
    }


    /**
     * Given an interval and a duration, search for a gap equal or greater than the duration inside
     * the interval.
     *
     * @param interval interval to search
     * @param gap      the minimum gap to find
     * @return the index of the interval before the gap
     */
    public Duration findGap(Interval interval, Duration gap) {
        boolean found = false;

        int start = overlapStart(interval);

        Duration currentGap = null;
        while (start < size() - 2 && !found) {
            currentGap = get(start).gap(get(start + 1)).toDuration();

            if (currentGap.compareTo(gap) >= 0) {
                found = true;
            } else {
                start++;
            }
        }

        if (!found) {
            return null;
        }

        return currentGap;
    }

    /**
     * Given two intervals, the first one defines the outer limits to search, and the second
     * one the inner limits. Inner interval fits several times inside the outer limit.
     *
     * @param outer
     * @param inner
     * @return
     */
    public int countGaps(Interval outer, Duration inner, Duration min, Duration max) {
        int amount = 0;


        return amount;
    }

    /**
     * Find the index where {@code interval} starts.
     *
     * @param interval
     * @return the index.
     */
    public int overlapStart(Interval interval) {
        int i = 0;

        while (i < size() - 1 && !get(i).overlaps(interval)) {
            i++;
        }

        if (i >= size()) {
            return -1;
        }

        return i;
    }

    public Duration lastGap() {
        DateTime last = get(size() - 1).getEnd();
        Interval gap = new Interval(last, DateTime.now());
        return gap.toDuration();
    }
}
