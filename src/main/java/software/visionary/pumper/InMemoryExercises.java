package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.MutableExercises;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

final class InMemoryExercises implements MutableExercises {
    private final List<Exercise> exercises;

    InMemoryExercises() {
        exercises = new ArrayList<>();
    }

    @Override
    public boolean has(final Exercise exercise) {
        return exercises.contains(exercise);
    }

    @Override
    public void add(final Exercise exercise) {
        exercises.add(Objects.requireNonNull(exercise));
    }

    @Override
    public Iterator<Exercise> iterator() {
        return exercises.iterator();
    }
}
