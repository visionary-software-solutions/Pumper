package software.visionary.pumper;

import org.junit.jupiter.api.Test;
import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.Workout;
import software.visionary.pumper.api.Pumper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class EatingExercisesLeadsToLoggingAWorkout {
    @Test
    void consumingFoodResultsInCreationOfAMeal() {
        // Given: the existence of a Pumper
        final Pumper nick = Fixtures.createPumper();
        // And: Some Exercise to be logged
        final Exercise exercise = Fixtures.createExercise();
        // When: the pumper logs the exercise
        nick.log(exercise);
        // And: I ask the pumper about the Workouts they've had
        final WorkoutsContainingExercise query = new WorkoutsContainingExercise(exercise);
        // When: I query
        nick.recollect(query);
        // Then: the workout is found
        assertTrue(query.foundWorkoutFromExercise());
    }

    private static final class WorkoutsContainingExercise implements Consumer<Workout> {
        private final Exercise sought;
        private final List<Workout> workouts;

        WorkoutsContainingExercise(final Exercise toFind) {
            sought = Objects.requireNonNull(toFind);
            workouts = new ArrayList<>();
        }

        @Override
        public void accept(final Workout workout) {
            if (workout.getExercises().has(sought)) {
                workouts.add(workout);
            }
        }

        boolean foundWorkoutFromExercise() {
            return !workouts.isEmpty();
        }
    }
}
