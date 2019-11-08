package software.visionary.pumper;

import software.visionary.pumper.api.Workout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

final class WorkoutsFromOneWeekAgoToNow implements Consumer<Workout> {
    private final List<Workout> found;
    private final Instant oneWeekAgo;

    WorkoutsFromOneWeekAgoToNow() {
        found = new ArrayList<>();
        oneWeekAgo = Instant.now().minus(7, ChronoUnit.DAYS);
    }

    @Override
    public void accept(final Workout workout) {
        if (oneWeekAgo.isBefore(workout.getStartedAt())) {
            found.add(workout);
        }
    }

    boolean contains(final Workout sought) {
        return found.contains(sought);
    }

}
