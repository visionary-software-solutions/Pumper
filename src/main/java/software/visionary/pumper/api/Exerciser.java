package software.visionary.pumper.api;

import java.util.function.Consumer;

public interface Exerciser {
    void log(Exercise exercise);

    void ask(Consumer<Exercise> question);
}
