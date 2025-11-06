import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Handles reading from and writing to the local data file.
 * The file format is a simple, human-readable text format.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    private void ensureParentDir() {
        File f = new File(filePath);
        File parent = f.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    /**
     * Loads tasks from the backing file. Non-parsable lines are skipped safely.
     * @return list of tasks loaded from disk (may be empty if file is missing).
     * @throws RuntimeException if an unrecoverable I/O error occurs.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                Task t = parseLine(line);
                assert t != null : "Parsed task should not be null for line: " + line;
                if (t != null) tasks.add(t);
                
            }
        } catch (Exception e) {
            System.out.println("☹ OOPS!!! Failed to load save file. Starting with an empty list.");
        }
        return tasks;
    }

    /**
     * Saves the given tasks to disk using the unified data format.
     * @param tasks list of tasks to persist
     */
    public void save(ArrayList<Task> tasks) {
        ensureParentDir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task t : tasks) {
                bw.write(t.toDataString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("☹ OOPS!!! Failed to save tasks.");
        }
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) return null;
        String type = parts[0].trim();
        boolean done = "1".equals(parts[1].trim());
        String desc = parts[2].trim();
        Task t;

        switch (type) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) return null;
                t = new Deadline(desc, parts[3].trim());
                break;
            case "E":
                if (parts.length < 4) return null;
                t = new Event(desc, parts[3].trim());
                break;
            case "A":
                if (parts.length < 4) return null;
                t = new DoAfter(desc, parts[3].trim());
                break;
            default:
                return null;
        }
        if (done) t.mark();
        return t;
    }
}