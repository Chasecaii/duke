import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String bot_name = "Chase Bot";
        System.out.println("Hello! I'm " + bot_name);
        System.out.println("What can I do for you?");

        Scanner sc = new Scanner(System.in);
        while (true){
            String line = sc.nextLine();
            if (line.equals("bye")){
                System.out.println("Bye. Hope to See you again soon!");
                break;
            }
        }
        sc.close();
    }
}
