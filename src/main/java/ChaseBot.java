import java.util.ArrayList;
import java.util.Scanner;

public class ChaseBot {
    private static final String BANNER = """
      ____ _                      ____        _   
     / ___| |__   __ _ ___  ___  | __ )  ___ | |_ 
    | |   | '_ \\ / _` / __|/ _ \\ |  _ \\ / _ \\| __|
    | |___| | | | (_| \\__ \\  __/ | |_) | (_) | |_ 
     \\____|_| |_|\\__,_|___/\\___| |____/ \\___/ \\__|
                                               
    """;

    public static void main(String[] args) {
        System.out.print(BANNER); 
        System.out.println("Hello! I'm Chase");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);

        Storage storage = new Storage("data/chase.txt");
        ArrayList<Task> tasks = storage.load();

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equals("bye")) {
                storage.save(tasks);
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }
            try {
                boolean changed = handleLine(line, tasks);
                if (changed) {
                    storage.save(tasks);
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("☹ OOPS!!! Something went wrong.");
            }
        }
        sc.close();
    }

    private static boolean handleLine(String line, ArrayList<Task> tasks) throws DukeException {
        if (line.equals("list")) {
            if (tasks.isEmpty()) {
                System.out.println("You have no tasks yet.");
                return false;
            }
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            return false;
        }

        if (line.startsWith("mark ")) {
            int idx = parseIndex(line.substring(5).trim());
            if (!isValidIndex(idx, tasks)) {
                throw new DukeException("☹ OOPS!!! The task index is invalid.");
            }
            tasks.get(idx).mark();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(idx));
            return true;
        }

        if (line.startsWith("unmark ")) {
            int idx = parseIndex(line.substring(7).trim());
            if (!isValidIndex(idx, tasks)) {
                throw new DukeException("☹ OOPS!!! The task index is invalid.");
            }
            tasks.get(idx).unmark();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(idx));
            return true;
        }

        if (line.startsWith("delete ")) {
            int idx = parseIndex(line.substring(7).trim());
            if (!isValidIndex(idx, tasks)) {
                throw new DukeException("☹ OOPS!!! The task index is invalid.");
            }
            Task removed = tasks.remove(idx);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removed);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            return true;
        }

        if (line.startsWith("find")) {
            String keyword = line.length() > 4 ? line.substring(4).trim() : "";
            if (keyword.isEmpty()) {
                throw new DukeException("☹ OOPS!!! Use: find <keyword>");
            }
            String kw = keyword.toLowerCase();

            ArrayList<Task> matches = new ArrayList<>();
            for (Task t : tasks) {
                if (t.getDescription().toLowerCase().contains(kw)) {
                    matches.add(t);
                }
            }

            if (matches.isEmpty()) {
                System.out.println("No matching tasks found.");
            } else {
                System.out.println("Here are the matching tasks in your list:");
                for (int i = 0; i < matches.size(); i++) {
                    System.out.println((i + 1) + ". " + matches.get(i));
                }
            }
            return false;
        }

        if (line.startsWith("todo")) {
            String desc = line.length() > 4 ? line.substring(4).trim() : "";
            if (desc.isEmpty()) {
                throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
            }
            Task t = new Todo(desc);
            tasks.add(t);
            System.out.println("added: " + t);
            return true;
        }

        if (line.startsWith("deadline")) {
            String rest = line.length() > 8 ? line.substring(8).trim() : "";
            int sep = rest.lastIndexOf("/by");
            if (sep == -1) {
                throw new DukeException("☹ OOPS!!! Use: deadline <desc> /by <time>");
            }
            String desc = rest.substring(0, sep).trim();
            String by = rest.substring(sep + 3).trim();
            if (desc.isEmpty() || by.isEmpty()) {
                throw new DukeException("☹ OOPS!!! Use: deadline <desc> /by <time>");
            }
            Task t = new Deadline(desc, by);
            tasks.add(t);
            System.out.println("added: " + t);
            return true;
        }

        if (line.startsWith("event")) {
            String rest = line.length() > 5 ? line.substring(5).trim() : "";
            int sep = rest.lastIndexOf("/at");
            if (sep == -1) {
                throw new DukeException("☹ OOPS!!! Use: event <desc> /at <time>");
            }
            String desc = rest.substring(0, sep).trim();
            String at = rest.substring(sep + 3).trim();
            if (desc.isEmpty() || at.isEmpty()) {
                throw new DukeException("☹ OOPS!!! Use: event <desc> /at <time>");
            }
            Task t = new Event(desc, at);
            tasks.add(t);
            System.out.println("added: " + t);
            return true;
        }

        if (line.startsWith("doafter")) {
            String rest = line.length() > 7 ? line.substring(7).trim() : "";
            int sep = rest.lastIndexOf("/after");
            if (sep == -1) {
                throw new DukeException("☹ OOPS!!! Use: doafter <desc> /after <prerequisite>");
            }
            String desc = rest.substring(0, sep).trim();
            String after = rest.substring(sep + 6).trim();
            if (desc.isEmpty() || after.isEmpty()) {
                throw new DukeException("☹ OOPS!!! Use: doafter <desc> /after <prerequisite>");
            }
            Task t = new DoAfter(desc, after);
            tasks.add(t);
            System.out.println("added: " + t);
            return true;
        }

        throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }

    private static int parseIndex(String s) throws DukeException {
        try {
            int oneBased = Integer.parseInt(s.trim());
            return oneBased - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("☹ OOPS!!! Please provide a valid task number.");
        }
    }

    private static boolean isValidIndex(int idx, ArrayList<Task> tasks) {
        return 0 <= idx && idx < tasks.size();
    }
}