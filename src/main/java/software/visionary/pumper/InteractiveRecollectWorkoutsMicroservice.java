package software.visionary.pumper;

import software.visionary.pumper.api.Pumper;

import java.util.Scanner;

public enum InteractiveRecollectWorkoutsMicroservice {
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
                case "0" : new RecollectWorkoutsFromLastWeek(new String[] { theMuncher.toString()}).run(); break;
                case "1" : new RecollectWorkoutsFromTimeRange(new String[]{in.nextLine(), in.nextLine(), theMuncher.toString()}).run(); break;
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
        System.out.println("0 - Recollect logged Workout for the last week");
        System.out.println("1 - Recollect logged Workout for a custom time range");
        System.out.println();
    }
}
