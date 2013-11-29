package io.github.axxiss.drivingtime;

import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Date;

/**
 * IntervalList adds time manipulation functionality to an {@link ArrayList}.
 */
public class IntervalList extends ArrayList<Interval> {

    /**
     * Creates a new list with the given dates.
     *
     * @param intervals size must be odd.
     */
    public IntervalList(Date[] intervals) {
        super(intervals.length);

        if (size() % 2 != 0) {
            throw new IllegalArgumentException("History parameter must be an odd number.");
        }

        for (int i = 0; i < size(); i += 2) {
            add(new Interval(intervals[i].getTime(), intervals[i + 1].getTime()));
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
    public int findGap(int start, Interval interval, Duration gap) {
        boolean found = false;

        while (start < size() - 2 && !found) {
            Duration currentGap = get(start).gap(get(start + 1)).toDuration();

            if (currentGap.compareTo(gap) >= 0) {
                found = true;
            } else {
                start++;
            }
        }

        if (!found) {
            if (start == size() - 1) {
            }
        }

        return start;
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
}
