import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final String byRaw;
    private final LocalDate byDate;

    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String by) {
        super(description);
        this.byRaw = by;
        this.byDate = tryParseDate(by);
    }

    private LocalDate tryParseDate(String s) {
        try {
            return LocalDate.parse(s.trim());
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        String when = (byDate != null) ? byDate.format(OUT_FMT) : byRaw;
        return "[" + TaskType.DEADLINE.code() + "][" + getStatusIcon() + "] "
                + getDescription() + " (by: " + when + ")";
    }

    @Override
    public String toDataString() {
        String saveBy = (byDate != null) ? byDate.toString() : byRaw;
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + saveBy;
    }
}