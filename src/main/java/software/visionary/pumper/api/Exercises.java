package software.visionary.pumper.api;

public interface Exercises extends Iterable<Exercise> {
    boolean has(Exercise exercise);
}
