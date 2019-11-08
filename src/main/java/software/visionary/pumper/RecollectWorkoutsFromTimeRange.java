package software.visionary.pumper;

import software.visionary.pumper.api.Pumper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

final class RecollectWorkoutsFromTimeRange implements Runnable {
    private final String startTime, endTime;
    private final Pumper user;

    RecollectWorkoutsFromTimeRange(final String[] args) {
        final Deque<String> toProcess = new ArrayDeque<>(Arrays.asList(args));
        final Name theMuncher = new Name(toProcess.pop());
        final PersistToFilePumper user = new PersistToFilePumper(theMuncher);
        this.startTime = toProcess.pop();
        this.endTime = toProcess.pop();
        this.user = user;
    }

    public void run() {
        System.out.println("Enter the year-month-day and time the meal started, e.g. 2019-11-05T23:45");
        final LocalDateTime startedAt = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("Enter the year-month-day and time the meal ended, e.g. 2019-11-05T23:59");
        final LocalDateTime endedAt = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final WorkoutsWithinTimeRange query = new WorkoutsWithinTimeRange(startedAt.toInstant(ZoneOffset.UTC), endedAt.toInstant(ZoneOffset.UTC));
        user.recollect(query.andThen(System.out::println));
    }
}
