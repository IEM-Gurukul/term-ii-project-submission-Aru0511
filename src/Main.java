
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EmailManager manager = new EmailManager();
        Scanner sc = new Scanner(System.in);

        System.out.println("How many emails do you want to enter?");
        int n = sc.nextInt();
        sc.nextLine();

        for(int i = 1; i <= n; i++) {

            System.out.println("\nEnter Email " + i);

            System.out.print("Sender: ");
            String sender = sc.nextLine();

            System.out.print("Subject: ");
            String subject = sc.nextLine();

            System.out.print("Content: ");
            String content = sc.nextLine();

            Email email = new Email(sender, subject, content);

            manager.receiveEmail(email);
        }

        manager.showFolders();   // show all folders

        sc.close();
    }
}