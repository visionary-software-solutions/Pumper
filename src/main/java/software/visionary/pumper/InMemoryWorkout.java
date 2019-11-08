package software.visionary.pumper;

import software.visionary.pumper.api.*;

import java.time.Instant;
import java.util.Objects;

final class InMemoryWorkout implements MutableWorkout {
    private final Instant startTime;
    private final Instant endTime;
    private final MutableExercises foods;

    InMemoryWorkout(final Instant startTime, final Instant endTime) {
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.foods = new InMemoryExercises();
    }

    static Workout fromFood(final Exercise exercise) {
        final InMemoryWorkout newMeal = new InMemoryWorkout(Instant.now(), Instant.now());
        newMeal.add(exercise);
        return newMeal;
    }

    @Override
    public Instant getStartedAt() {
        return startTime;
    }

    @Override
    public Instant getEndedAt() {
        return endTime;
    }

    @Override
    public Exercises getExercises() {
        return foods;
    }

    @Override
    public void add(final Exercise exercise) {
        foods.add(Objects.requireNonNull(exercise));
    }
}
