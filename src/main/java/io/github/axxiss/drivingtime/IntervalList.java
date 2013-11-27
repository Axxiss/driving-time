package io.github.axxiss.drivingtime;

import org.joda.time.DateTime;
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
     * Get the duration of all intervals found from {@code start}
     *
     * @param start
     * @return the sum of all intervals.
     */
    public Duration getDriveDuration(DateTime start, DateTime end) {
        if (end == null) {
            end = DateTime.now();
        }

        int index = overlapStart(new Interval(start, end));

        int size = size();

        long millis = 0;

        for (int i = index; i < size; i++) {
            millis += get(i).toDurationMillis();
        }

        return Duration.millis(millis);
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
}
