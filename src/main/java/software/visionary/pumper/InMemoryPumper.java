package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.Workout;
import software.visionary.pumper.api.Pumper;
import software.visionary.pumper.api.MutableWorkout;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

final class InMemoryPumper implements Pumper {
    private final Name name;
    private final List<Workout> consumed;

    InMemoryPumper(final Name name) {
        this.name = Objects.requireNonNull(name);
        consumed = new ArrayList<>();
    }

    @Override
    public void log(final Exercise exercise) {
        final Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
        final WorkoutsWithinTimeRange anyThingEaten = new WorkoutsWithinTimeRange(oneHourAgo, Instant.now());
        recollect(anyThingEaten);
        anyThingEaten.mostRecent()
                .ifPresentOrElse(meal -> ((MutableWorkout) meal).add(exercise), () -> log(InMemoryWorkout.fromFood(exercise)));
    }

    @Override
    public void ask(final Consumer<Exercise> question) {
        consumed.stream()
        .map(Workout::getExercises)
        .forEach(foods -> foods.forEach(question));
    }

    @Override
    public void log(final Workout workout) {
        consumed.add(Objects.requireNonNull(workout));
    }

    @Override
    public void recollect(final Consumer<Workout> query) {
        consumed.forEach(query::accept);
    }

    @Override
    public String toString() { return name.toString(); }
}
