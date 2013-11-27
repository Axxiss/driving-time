package io.github.axxiss.drivingtime;

import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Date;

/**
 * IntervalList adds time manipulation functionality to an {@link ArrayList}.
 */
public class IntervalList extends ArrayList<Interval> {

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

        for (Interval i : this) {
            Interval match = interval.overlap(i);

            if (match != null) {
                amount.plus(match.toDurationMillis());
            }
        }

        return amount;
    }

    /**
     * Given an {@link Interval} finds the first overlapping interval.
     *
     * @param interval the interval to search.
     * @return the index of the overlapping interval.
     */
    public int overlapStart(Interval interval) {
        int last = size() - 1;

        int i = 0;
        while (i < last && !get(i).overlaps(interval)) {
            i++;
        }

        if (i > last) {
            return -1;
        } else {
            return i;
        }
    }

    public int findGap(int start, Duration gap) {

        int i = start;

        boolean found = false;

        while (i < size() - 1 && !found) {
            Duration currentGap = get(i).gap(get(i + 1)).toDuration();

            if (currentGap.compareTo(gap) >= 0) {
                found = true;
            }
        }

        return i;
    }
}
