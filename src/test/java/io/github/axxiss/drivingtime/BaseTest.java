package io.github.axxiss.drivingtime;

import static io.github.axxiss.drivingtime.rules.Rule.hoursToMillis;

/**
 * Created with IntelliJ IDEA.
 * User: alexis
 * Date: 11/29/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseTest {
    protected DrivingTime drivingTime;


    protected long oneHour = 1 * hoursToMillis;
    protected long threeHours = 3 * hoursToMillis;
    protected long fourHours = 4 * hoursToMillis;
    protected long fiveHours = 5 * hoursToMillis;
    protected long sixHours = 6 * hoursToMillis;
    protected long eightHours = 8 * hoursToMillis;
    protected long nineHours = 9 * hoursToMillis;
    protected long tenHours = 10 * hoursToMillis;
    protected long twelveHours = 12 * hoursToMillis;
    protected long fourtyFiveHourse = 45 * hoursToMillis;
}
