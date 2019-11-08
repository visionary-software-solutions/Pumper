package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;

import java.util.Objects;

final class InMemoryExercise implements Exercise {
    private final Name name;

    InMemoryExercise(final Name name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName().toString();
    }
}
