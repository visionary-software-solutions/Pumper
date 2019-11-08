package software.visionary.pumper;

import org.junit.jupiter.api.Test;
import software.visionary.pumper.api.Workout;
import software.visionary.pumper.api.Pumper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class QueryForWorkoutsForATimeRange {
    @Test
    void canQueryPumperForWorkoutsWithinTheLastWeek() {
        // Given: A Pumper
        final Pumper mom = Fixtures.createPumper();
        // And: that person logs a Workout from 14 days ago
        final Workout first = Fixtures.createWorkoutFromXDaysAgo(14);
        mom.log(first);
        // And: that person logs a Workout from 3 days ago
        final Workout second = Fixtures.createWorkoutFromXDaysAgo(3);
        mom.log(second);
        // And: that person logs a Workout from 2 days ago
        final Workout third = Fixtures.createWorkoutFromXDaysAgo(2);
        mom.log(third);
        // And: that person logs a Workout from yesterday
        final Workout fourth = Fixtures.createWorkoutFromXDaysAgo(1);
        mom.log(fourth);
        // And: A query for Workout
        final WorkoutsFromOneWeekAgoToNow query = new WorkoutsFromOneWeekAgoToNow();
        // When: I query
        mom.recollect(query);
        // Then: the fourth Workout is stored
        assertTrue(query.contains(fourth));
        // And: the third Workout is stored
        assertTrue(query.contains(third));
        // And: the second Workout is stored
        assertTrue(query.contains(second));
        // And: the first Workout is not stored
        assertFalse(query.contains(first));
    }

    @Test
    void canQueryPumperForWorkoutsTwoWeeksAgo() {
        // Given: A Muncher
        final Pumper mom = Fixtures.createPumper();
        // And: that person logs a Workout from 14 days ago
        final Workout first = Fixtures.createWorkoutFromXDaysAgo(14);
        mom.log(first);
        // And: that person logs a Workout from 3 days ago
        final Workout second = Fixtures.createWorkoutFromXDaysAgo(3);
        mom.log(second);
        // And: that person logs a Workout from 2 days ago
        final Workout third = Fixtures.createWorkoutFromXDaysAgo(2);
        mom.log(third);
        // And: that person logs a Workout from yesterday
        final Workout fourth = Fixtures.createWorkoutFromXDaysAgo(1);
        mom.log(fourth);
        // And: A query for Workout
        final WorkoutsWithinTimeRange query = WorkoutsWithinTimeRange.mealsEatenLastWeek();
        // When: I query
        mom.recollect(query);
        // Then: the fourth Workout is not stored
        assertFalse(query.contains(fourth));
        // And: the third Workout is not stored
        assertFalse(query.contains(third));
        // And: the second Workout is not stored
        assertFalse(query.contains(second));
        // And: the first Workout is stored
        assertTrue(query.contains(first));
    }

}
