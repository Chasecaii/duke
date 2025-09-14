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
            } else {
                Task t = new Task(line);
                tasks.add(t);
                System.out.println("added: " + line);
            }
        }
        sc.close();
    }
}
