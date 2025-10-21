import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final String atRaw;
    private final LocalDate atDate;

    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("MMM d yyyy");

    public Event(String description, String at) {
        super(description);
        this.atRaw = at;
        this.atDate = tryParseDate(at);
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
        String when = (atDate != null) ? atDate.format(OUT_FMT) : atRaw;
        return "[" + TaskType.EVENT.code() + "][" + getStatusIcon() + "] "
                + getDescription() + " (at: " + when + ")";
    }

    @Override
    public String toDataString() {
        String saveAt = (atDate != null) ? atDate.toString() : atRaw;
        return "E | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + saveAt;
    }
}