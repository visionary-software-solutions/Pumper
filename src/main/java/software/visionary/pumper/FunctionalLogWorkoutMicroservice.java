package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.MutableWorkout;
import software.visionary.pumper.api.Pumper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;

public enum FunctionalLogWorkoutMicroservice {
    INSTANCE;

    public static void main(final String[] args) {
        if (args.length == 0 || Arrays.toString(args).contains("help")) {
            printHelp();
        }
        new LogAWorkout(args).run();
    }

    private static void printHelp() {
        System.out.println("Welcome to Visionary Software Solutions Workout Logging service!");
        System.out.println("The first argument should be a name, which is a non-null, non-empty String. This is used to identify the user data to save.");
        System.out.println("The second argument should be the year-month-day and time the meal started, e.g. 2019-11-05T23:45");
        System.out.println("The third argument should be the year-month-day and time the meal ended, e.g. 2019-11-05T23:59");
        System.out.println("Every argument thereafter should be a string representing Exercise to add to the meal, e.g. \"Dumbbell Biceps Curl\"");
    }

    private static class LogAWorkout implements Runnable {
        private final Deque<String> args;

        LogAWorkout(final String[] args) {
            this.args = new ArrayDeque<>(Arrays.asList(args));
        }

        @Override
        public void run() {
            final Name theMuncher = new Name(Objects.requireNonNull(args.pop()));
            final Pumper user = new PersistToFilePumper(theMuncher);
            final LocalDateTime startedAt = LocalDateTime.parse(args.pop(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            final LocalDateTime endedAt = LocalDateTime.parse(args.pop(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            final MutableWorkout toLog = new InMemoryWorkout(startedAt.toInstant(ZoneOffset.UTC), endedAt.toInstant(ZoneOffset.UTC));
            while (!args.isEmpty()) {
                final Exercise f = new InMemoryExercise(new Name(args.pop()));
                System.out.println("Adding the exercise " + f);
                toLog.add(f);
            }
            user.log(toLog);
        }
    }
}
