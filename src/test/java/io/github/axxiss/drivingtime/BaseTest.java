package io.github.axxiss.drivingtime;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.stubbing.Stubber;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public abstract class BaseTest {
    protected DrivingTime drivingTime;

    protected IntervalList intervals;

    @Before
    public void setUp() throws Exception {
        intervals = spy(new IntervalList(new Date[0]));
    }

    protected void mockFindGap(Hours... args) {

        Stubber stubber = doReturn(args[0].getValue());

        for (int i = 1; i < args.length; i++) {
            stubber = stubber.doReturn(args[i].getValue());
        }

        stubber.when(intervals).findGap(any(Interval.class), any(Duration.class));
    }

    protected void mockOverlap(Hours overlap) {
        doReturn(overlap.getValue()).when(intervals).overlap(any(Interval.class));
    }

    protected void mockCountDurationInterval(int count) {
        doReturn(count).when(intervals).countDurationInterval(any(Interval.class), any(Duration.class), any(Duration.class));
    }

    protected void mockLastGap(Hours gap) {
        doReturn(gap.getValue()).when(intervals).lastGap();
    }

    protected void mockCountGaps(int reduced) {
        doReturn(reduced).when(intervals).countGaps(any(Interval.class), any(Duration.class), any(Duration.class), any(Duration.class));
    }
}
