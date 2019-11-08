package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.Exercises;
import software.visionary.pumper.api.Workout;
import software.visionary.pumper.api.Pumper;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

final class PersistToFilePumper implements Pumper {
    private final Name name;

    PersistToFilePumper(final Name theMuncher) {
        this.name = Objects.requireNonNull(theMuncher);
    }

    @Override
    public void log(final Exercise exercise) {
        // TODO: does the whole Meal creation/appending thing need to happen here?
    }

    @Override
    public void ask(final Consumer<Exercise> question) {
        // TODO: does the whole Meal creation/appending thing need to happen here?
    }

    @Override
    public synchronized void log(final Workout workout) {
        final List<Workout> toWrite = new ArrayList<>();
        recollect(toWrite::add);
        toWrite.add(new SerializedWorkout(workout));
        //TODO: Refactor to Dependency Injection if necessary
        ObjectSerializer.INSTANCE.writeAllObjectsToFile(toWrite, ObjectSerializer.getFileToSaveAs(toString()));
    }

    @Override
    public void recollect(final Consumer<Workout> query) {
        //TODO: Refactor to Dependency Injection if necessary
        ObjectSerializer.INSTANCE.readAllObjects(ObjectSerializer.getFileToSaveAs(toString())).stream().map(SerializedWorkout.class::cast).forEach(query);
    }

    @Override
    public String toString() { return name.toString(); }

    private static final class SerializedWorkout implements Serializable, Workout {

        private static final class SerializedExercise implements Serializable, Exercise {
            private String food;

            @Override
            public Name getName() {
                return new Name(food);
            }

            @Override
            public String toString() {
                return getName().toString();
            }
        }

        private static final class SerializedExercises implements Serializable, Exercises {
            private final List<SerializedExercise> foods;

            private SerializedExercises() {
                this.foods = new ArrayList<>();
            }

            @Override
            public boolean has(final Exercise exercise) {
                return (exercise instanceof SerializedExercise) && foods.contains(exercise);
            }

            @Override
            public Iterator<Exercise> iterator() {
                return foods.stream().map(Exercise.class::cast).iterator();
            }

            @Override
            public String toString() {
                final StringBuilder builder = new StringBuilder();
                foods.forEach(food -> builder.append(String.format("%n%s%n", food)));
                return builder.toString();
            }
        }

        private final Instant start, end;
        private final SerializedExercises foods;

        private SerializedWorkout(final Workout workout) {
            start = workout.getStartedAt();
            end = workout.getEndedAt();
            foods = new SerializedExercises();
            workout.getExercises().forEach(f -> {
                final SerializedExercise food = new SerializedExercise();
                food.food = f.toString();
                foods.foods.add(food);
            });
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
        public Exercises getExercises() {
            return foods;
        }

        @Override
        public String toString() {
            return String.format("Started @ %s%nEnded @ %s%nContained:%s%n", start, end, foods);
        }
    }
}
