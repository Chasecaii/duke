public abstract class Task {
    private final String description;
    private boolean isDone;
    
    /**
     * Base class for all tasks. Encapsulates common state and behavior.
     * Subclasses must implement {@link #toDataString()} to define persistence format.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() { this.isDone = true; }

    public void unmark() { this.isDone = false; }

    public String getStatusIcon() { return isDone ? "X" : " "; }

    public String getDescription() { return description; }

    public boolean isDone() { return isDone; }

    /** Returns a compact one-line representation used by Storage. */
    public abstract String toDataString();

    @Override
    public abstract String toString();
}