import java.util.ArrayList;
import java.util.Scanner;

public class ChaseBot {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            String line = sc.nextLine().trim();
            if (line.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (line.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (line.startsWith("mark ")) {
                int idx = Integer.parseInt(line.substring(5).trim()) - 1;
                if (0 <= idx && idx < tasks.size()) {
                    tasks.get(idx).mark();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(idx));
                }
            } else if (line.startsWith("unmark ")) {
                int idx = Integer.parseInt(line.substring(7).trim()) - 1;
                if (0 <= idx && idx < tasks.size()) {
                    tasks.get(idx).unmark();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(idx));
                }
            } else if (line.startsWith("todo")) {
                String desc = line.length() > 4 ? line.substring(4).trim() : "";
                if (desc.isEmpty()) {
                    System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
                    continue;
                }
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println("added: " + t);
            } else if (line.startsWith("deadline")) {
                String rest = line.length() > 8 ? line.substring(8).trim() : "";
                int sep = rest.lastIndexOf("/by");
                if (sep == -1) {
                    System.out.println("☹ OOPS!!! Use: deadline <desc> /by <time>");
                    continue;
                }
                String desc = rest.substring(0, sep).trim();
                String by = rest.substring(sep + 3).trim(); // skip "/by"
                if (desc.isEmpty() || by.isEmpty()) {
                    System.out.println("☹ OOPS!!! Use: deadline <desc> /by <time>");
                    continue;
                }
                Task t = new Deadline(desc, by);
                tasks.add(t);
                System.out.println("added: " + t);
            } else if (line.startsWith("event")) {
                String rest = line.length() > 5 ? line.substring(5).trim() : "";
                int sep = rest.lastIndexOf("/at");
                if (sep == -1) {
                    System.out.println("☹ OOPS!!! Use: event <desc> /at <time>");
                    continue;
                }
                String desc = rest.substring(0, sep).trim();
                String at = rest.substring(sep + 3).trim(); // skip "/at"
                if (desc.isEmpty() || at.isEmpty()) {
                    System.out.println("☹ OOPS!!! Use: event <desc> /at <time>");
                    continue;
                }
                Task t = new Event(desc, at);
                tasks.add(t);
                System.out.println("added: " + t);
            } else {
                Task t = new Todo(line); 
                tasks.add(t);
                System.out.println("added: " + t);
            }
        }
        sc.close();
    }
}