package software.visionary.pumper.api;

import java.time.Instant;

public interface Event {
    Instant getStartedAt();

    Instant getEndedAt();
}
