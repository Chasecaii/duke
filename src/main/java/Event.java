import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Event extends Task {
    private final String atRaw;
    private final LocalDate atDate;

    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    public Event(String description, String at) {
        super(description);
        this.atRaw = at;
        this.atDate = tryParseNaturalDate(at);
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

    /* ------- same helpers as Deadline ------- */

    private static LocalDate tryParseNaturalDate(String s) {
        if (s == null) return null;
        String text = s.trim().toLowerCase(Locale.ENGLISH);
        if (text.equals("today"))   return LocalDate.now();
        if (text.equals("tomorrow") || text.equals("tmr")) return LocalDate.now().plusDays(1);
        if (text.startsWith("next ")) {
            DayOfWeek target = parseDayOfWeek(text.substring(5).trim());
            if (target != null) return next(target);
        }
        String[] patterns = new String[] {
                "yyyy-MM-dd", "yyyy/MM/dd",
                "dd/MM/yyyy", "d/M/yyyy",
                "dd-MM-yyyy", "d-M-yyyy",
                "d-MMM-yyyy", "MMM d yyyy", "MMMM d yyyy"
        };
        for (String p : patterns) {
            try {
                DateTimeFormatter f = DateTimeFormatter.ofPattern(p, Locale.ENGLISH);
                return LocalDate.parse(s.trim(), f);
            } catch (DateTimeParseException ignored) {}
        }
        try { return LocalDate.parse(s.trim()); } catch (Exception e) { return null; }
    }

    private static DayOfWeek parseDayOfWeek(String s) {
        String t = s.toLowerCase(Locale.ENGLISH);
        if (t.startsWith("mon")) return DayOfWeek.MONDAY;
        if (t.startsWith("tue")) return DayOfWeek.TUESDAY;
        if (t.startsWith("wed")) return DayOfWeek.WEDNESDAY;
        if (t.startsWith("thu")) return DayOfWeek.THURSDAY;
        if (t.startsWith("fri")) return DayOfWeek.FRIDAY;
        if (t.startsWith("sat")) return DayOfWeek.SATURDAY;
        if (t.startsWith("sun")) return DayOfWeek.SUNDAY;
        return null;
    }

    private static LocalDate next(DayOfWeek target) {
        LocalDate d = LocalDate.now();
        int add = (target.getValue() - d.getDayOfWeek().getValue() + 7) % 7;
        add = (add == 0) ? 7 : add;
        return d.plusDays(add);
    }
}