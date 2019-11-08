package software.visionary.pumper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.Exercises;
import software.visionary.pumper.api.Workout;
import software.visionary.pumper.api.Pumper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class PersistWorkoutLogAsAFile {
    @Test
    void canSaveWorkoutsToAFile() {
        // Given: A Pumper that persists data to disk
        final Pumper mom = new PersistToFilePumper(new Name("Mom"));
        // When: that person logs a Workout from 14 days ago
        final Workout first = Fixtures.createWorkoutFromXDaysAgo(14);
        mom.log(first);
        // Then: A file should exist for that Pumper
        Assertions.assertTrue(Files.exists(Paths.get(mom.toString())));
    }

    @Test
    void canLoadWorkoutsFromAFile() {
        // Given: A Pumper that persists data to disk
        final Pumper mom = new PersistToFilePumper(new Name("Mom"));
        // And: that person logs a Workout from 14 days ago
        final Workout first = Fixtures.createWorkoutFromXDaysAgo(14);
        mom.log(first);
        // And: that person logs a Workout from 3 days ago
        final Workout second = Fixtures.createWorkoutFromXDaysAgo(3);
        mom.log(second);
        // And: that person logs a Workout from 2 days ago
        final Workout third = Fixtures.createWorkoutFromXDaysAgo(2);
        mom.log(third);
        // And: that person logs a Workout from yesterday
        final Workout fourth = Fixtures.createWorkoutFromXDaysAgo(1);
        mom.log(fourth);
        // When: I query the Pumper for all Workouts stored
        final List<Workout> sought = new ArrayList<>(4);
        mom.recollect(meal -> sought.add(new SoughtWorkout(meal)));
        // Then: every Workout logged should be stored
        Assertions.assertTrue(sought.containsAll(Stream.of(first,second,third,fourth).map(SoughtWorkout::new).collect(Collectors.toList())));
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get("Mom"));
    }

    private static final class SoughtWorkout implements Workout {
        private final Instant start, end;
        private final List<Exercise> exercises;

        SoughtWorkout(final Workout workout) {
            this.start = workout.getStartedAt();
            this.end = workout.getEndedAt();
            exercises = new ArrayList<>();
            workout.getExercises().iterator().forEachRemaining(exercises::add);
        }

        @Override
        public Exercises getExercises() {
            return new Exercises() {
                @Override
                public boolean has(final Exercise exercise) {
                    return exercises.contains(exercise);
                }

                @Override
                public Iterator<Exercise> iterator() {
                    return exercises.iterator();
                }
            };
        }

        @Override
        public Instant getStartedAt() {
            return start;
        }

        @Override
        public Instant getEndedAt() {
            return end;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final SoughtWorkout that = (SoughtWorkout) o;
            return start.equals(that.start) &&
                    end.equals(that.end) &&
                    exercises.equals(that.exercises);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, exercises);
        }
    }
}
