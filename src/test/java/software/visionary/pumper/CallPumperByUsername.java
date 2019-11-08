package software.visionary.pumper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import software.visionary.pumper.api.Pumper;

final class CallPumperByUsername {
    @Test
    void pumpersShouldHaveAName() {
        // Given: A name the Pumper would like to be known as
        final Name superPumper = new Name("Super Pumper");
        // When: I create a Pumper with that name
        final Pumper pumper = Fixtures.namedPumper(superPumper);
        // Then: The name should be reflected in the Muncher's toString
        Assertions.assertEquals(superPumper.toString(), pumper.toString());
    }
}