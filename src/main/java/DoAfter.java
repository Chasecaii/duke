public class DoAfter extends Task {
    private final String after;

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    @Override
    public String toString() {
        return "[" + TaskType.AFTER.code() + "][" + getStatusIcon() + "] "
                + getDescription() + " (after: " + after + ")";
    }

    @Override
    public String toDataString() {
        return "A | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + after;
    }
}