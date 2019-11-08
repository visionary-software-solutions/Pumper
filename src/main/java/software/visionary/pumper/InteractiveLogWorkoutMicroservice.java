package software.visionary.pumper;

import software.visionary.pumper.api.Exercise;
import software.visionary.pumper.api.MutableWorkout;
import software.visionary.pumper.api.Pumper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public enum InteractiveLogWorkoutMicroservice {
    INSTANCE;

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Muncher, a Visionary Software Solutions Vision for Workout logging!");
        System.out.println("What is your name?");
        final Name theMuncher = new Name(in.nextLine());
        busyWait(theMuncher, in);
    }

    private static void busyWait(final Name theMuncher, final Scanner in) {
        final Pumper user = new PersistToFilePumper(theMuncher);
        do {
            printMenu();
            switch (in.nextLine()) {
                case "0" : new LogAWorkout(user, in).run(); break;
                case "q":
                case "Q":
                    System.exit(1);
                default:
                    throw new UnsupportedOperationException("Please select from valid options");
            }
        } while (true);
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Choose an action, Q to quit");
        System.out.println("0 - Log A Workout");
        System.out.println();
    }

    private static class LogAWorkout implements Runnable {
        private final Pumper thePumper;
        private final Scanner scanner;

        LogAWorkout(final Pumper user, final Scanner in) {
            thePumper = Objects.requireNonNull(user);
            scanner = Objects.requireNonNull(in);
        }

        @Override
        public void run() {
            System.out.println("Enter the year-month-day and time the Workout started, e.g. 2019-11-05T23:45");
            final LocalDateTime startedAt = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            System.out.println("Enter the year-month-day and time the Workout ended, e.g. 2019-11-05T23:59");
            final LocalDateTime endedAt = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            final MutableWorkout toLog = new InMemoryWorkout(startedAt.toInstant(ZoneOffset.UTC), endedAt.toInstant(ZoneOffset.UTC));
            addFood(toLog);
            thePumper.log(toLog);
        }

        private void addFood(final MutableWorkout toLog) {
            while (true) {
                System.out.println("+ to add the Name of Exercise you did in this Workout. / to stop");
                switch (scanner.nextLine()) {
                    case "/":
                        return;
                    case "+":
                        final Exercise f = new InMemoryExercise(new Name(scanner.nextLine()));
                        System.out.println("Adding the Exercise " + f);
                        toLog.add(f);
                        break;
                    default:
                        System.out.println("Unsupported option, choose + or /");
                        break;
                }
            }
        }
    }
}
