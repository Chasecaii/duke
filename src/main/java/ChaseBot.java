import java.util.ArrayList;
import java.util.Scanner;

public class ChaseBot {
    public static void main(String[] args) {
        String bot_name = "Chase Bot";
        System.out.println("Hello! I'm " + bot_name);
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        while (true) {
            String line = sc.nextLine();
            if (line.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (line.equals("list")) {
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else {
                tasks.add(line);
                System.out.println("added: " + line);
            }
        }
        sc.close();
    }
}
