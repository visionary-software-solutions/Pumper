package software.visionary.pumper;

import software.visionary.pumper.api.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

final class Fixtures {
    private Fixtures() {}

    static Workout createWorkoutFromXDaysAgo(final int daysAgo) {
        final Instant startTime = Instant.now().minus(daysAgo, ChronoUnit.DAYS);
        final Instant endTime = startTime.plus(1, ChronoUnit.HOURS);
        return new InMemoryWorkout(startTime, endTime);
    }

    static Pumper createPumper() {
        return new InMemoryPumper(new Name("fakePumper"));
    }

    static Pumper namedPumper(final Name name) {
        return new InMemoryPumper(name);
    }

    private static final class DumbbellBicepsCurl implements Exercise {

    }

    static Exercise createExercise() {
        return new DumbbellBicepsCurl();
    }
}
