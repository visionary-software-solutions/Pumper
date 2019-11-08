package software.visionary.pumper.api;

import java.util.function.Consumer;

public interface Pumper extends Exerciser {
    void log(Workout workout);

    void recollect(Consumer<Workout> query);
}
