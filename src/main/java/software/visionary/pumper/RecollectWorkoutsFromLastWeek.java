package software.visionary.pumper;

import software.visionary.pumper.api.Pumper;

final class RecollectWorkoutsFromLastWeek implements Runnable {
    private final Pumper user;

    RecollectWorkoutsFromLastWeek(final String[] args) {
        final Name name = new Name(args[0]);
        this.user = new PersistToFilePumper(name);
    }

    public void run() {
        final WorkoutsFromOneWeekAgoToNow query = new WorkoutsFromOneWeekAgoToNow();
        user.recollect(query.andThen(System.out::println));
    }
}
