package software.visionary.pumper.api;

import software.visionary.pumper.Name;

public interface Exercise {
    default Name getName() {
        return new Name(Exercise.class.getName());
    }
}
