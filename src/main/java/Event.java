public class Event extends Task {
    private final String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[" + TaskType.EVENT.code() + "][" + getStatusIcon() + "] "
                + getDescription() + " (at: " + at + ")";
    }
}