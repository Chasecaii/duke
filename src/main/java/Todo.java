public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[" + TaskType.TODO.code() + "][" + getStatusIcon() + "] " + getDescription();
    }

    @Override
    public String toDataString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }
}