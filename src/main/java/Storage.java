import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

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
                if (t != null) tasks.add(t);
            }
        } catch (Exception e) {
            System.out.println("☹ OOPS!!! Failed to load save file. Starting with an empty list.");
        }
        return tasks;
    }

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
            default:
                return null;
        }
        if (done) t.mark();
        return t;
    }
}