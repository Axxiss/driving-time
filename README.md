Driving Time and Rest Periods
=============================

[![Build Status](https://travis-ci.org/Axxiss/driving-time.png?branch=master)](https://travis-ci.org/Axxiss/driving-time)

Driving time and rest periods according to [Regulation (EC) 561/2006][1]

These rules establish that:

- Daily driving period shall not exceed 9 hours, with an exemption of twice a week when it can be extended to 10 hours.
- Total weekly driving time may not exceed 56 hours and the total fortnightly driving time may not exceed 90 hours.
- Daily rest period shall be at least 11 hours, with an exception of going down to 9 hours maximum three times a week.
- Daily rest can be split into 3 hours rest followed by 9 hour rest to make a total of 12 hours daily rest.
- Weekly rest is 45 continuous hours, which can be reduced every second week to 24 hours.
- Compensation arrangements apply for reduced weekly rest period.
- Weekly rest is to be taken after six days of working, except for coach drivers engaged in a single occasional service of international transport of passengers who may postpone their weekly rest period after 12 days in order to facilitate coach holidays.
- Breaks of at least 45 minutes (separable into 15 minutes followed by 30 minutes) should be taken after 4 ½ hours at the latest.


_Warning!_ this library is not tested with large amount of data, many linear search are used. If you're experiencing
performance issues open a ticket on the issue tracker.


TODO
====

Drive
-------

- [x] daily: max 9hours
- [x] daily: max 10hours (2 times a week)
- [x] weekly: max 56 hours
- [x] fortnightly: 90hours max
- [x] nonstop: 4:30 hours

Rest
----
- [x] normal: 11 hours nonstop
- [ ] split: 3 hours + 9 hours nonstop, over a period of 24hours
- [ ] reduced: 9 hours 3 times a week
- [ ] weekly: 45 hours
- [ ] reduced: 24 hours minimum, recover reduction before the 3rd week.



[1]: http://ec.europa.eu/transport/modes/road/social_provisions/driving_time/