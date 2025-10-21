public class Deadline extends Task {
    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[" + TaskType.DEADLINE.code() + "][" + getStatusIcon() + "] "
                + getDescription() + " (by: " + by + ")";
    }
}