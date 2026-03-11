
import java.util.ArrayList;

public class EmailManager {

    private ArrayList<Email> spamFolder = new ArrayList<>();
    private ArrayList<Email> importantFolder = new ArrayList<>();
    private ArrayList<Email> workFolder = new ArrayList<>();
    private ArrayList<Email> personalFolder = new ArrayList<>();


    public void receiveEmail(Email email) {
        categorizeEmail(email);
    }


    private void categorizeEmail(Email email) {

        FilterStrategy spam = new SpamFilterStrategy();
        FilterStrategy important = new ImportantFilterStrategy();
        FilterStrategy work = new WorkFilterStrategy();

        if (spam.filter(email)) {
            spamFolder.add(email);
            System.out.println("Email '" + email.getSubject() + "' stored in → Spam Folder");
        }

        else if (important.filter(email)) {
            importantFolder.add(email);
            System.out.println("Email '" + email.getSubject() + "' stored in → Important Folder");
        }

        else if (work.filter(email)) {
            workFolder.add(email);
            System.out.println("Email '" + email.getSubject() + "' stored in → Work Folder");
        }

        else {
            personalFolder.add(email);
            System.out.println("Email '" + email.getSubject() + "' stored in → Personal Folder");
        }
    }


    public void showFolders() {

        System.out.println("\n===== Folder Contents =====");

        System.out.println("\nSpam Folder:");
        for (Email e : spamFolder) {
            System.out.println(e.getSubject());
        }

        System.out.println("\nImportant Folder:");
        for (Email e : importantFolder) {
            System.out.println(e.getSubject());
        }

        System.out.println("\nWork Folder:");
        for (Email e : workFolder) {
            System.out.println(e.getSubject());
        }

        System.out.println("\nPersonal Folder:");
        for (Email e : personalFolder) {
            System.out.println(e.getSubject());
        }
    }
}