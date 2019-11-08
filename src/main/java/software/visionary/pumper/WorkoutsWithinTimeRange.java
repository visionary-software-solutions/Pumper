package software.visionary.pumper;

import software.visionary.pumper.api.Workout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;

final class WorkoutsWithinTimeRange implements Consumer<Workout> {
    private final List<Workout> found;
    private final Instant start;
    private final Instant end;

    WorkoutsWithinTimeRange(final Instant start, final Instant end) {
        found = new ArrayList<>();
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
    }

    static WorkoutsWithinTimeRange mealsEatenLastWeek() {
        return new WorkoutsWithinTimeRange(Instant.now().minus(15, ChronoUnit.DAYS), Instant.now().minus(7, ChronoUnit.DAYS));
    }

    @Override
    public void accept(final Workout workout) {
        if (start.isBefore(workout.getStartedAt()) && end.isAfter(workout.getEndedAt())) {
            found.add(workout);
        }
    }

    boolean contains(final Workout sought) {
        return found.contains(sought);
    }

    Optional<Workout> mostRecent() {
        final List<Workout> toOrganize = new ArrayList<>(found);
        toOrganize.sort(Comparator.comparing(Workout::getStartedAt));
        return toOrganize.stream().findFirst();
    }
}
