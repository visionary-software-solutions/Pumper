package software.visionary.pumper.api;

public interface MutableWorkout extends Workout {
    void add(Exercise exercise);
}
