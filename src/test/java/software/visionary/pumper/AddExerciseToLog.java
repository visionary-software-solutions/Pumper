package software.visionary.pumper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.pumper.api.Exerciser;
import software.visionary.pumper.api.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

final class AddExerciseToLog {
    @Test
    void canLogExercise() {
        // Given: the existence of an Exerciser
        final Exerciser nick = Fixtures.createPumper();
        // And: An Exercise to be logged
        final Exercise exercise = Fixtures.createExercise();
        // When: the Exerciser logs the Exercise
        nick.log(exercise);
        // And: I ask the Pumper about the Exercises they've done
        final WhatExercisesDidYouDo question = new WhatExercisesDidYouDo();
        nick.ask(question);
        // Then: the answer contains the Exercise
        Assertions.assertTrue(question.hasEaten(exercise));
    }

    static final class WhatExercisesDidYouDo implements Consumer<Exercise> {
        private final List<Exercise> consumed;

        WhatExercisesDidYouDo() {
            consumed = new ArrayList<>();
        }

        @Override
        public void accept(final Exercise exercise) {
            consumed.add(Objects.requireNonNull(exercise));
        }

        boolean hasEaten(final Exercise exercise) {
            return consumed.contains(exercise);
        }
    }
}
