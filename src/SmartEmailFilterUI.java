
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SmartEmailFilterUI extends JFrame {

    JTextField senderField, subjectField, contentField;
    JTextArea outputArea;
    EmailManager manager;

    public SmartEmailFilterUI() {

        manager = new EmailManager();

        setTitle("Smart Email Filtering System");
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Fields
        add(new JLabel("Sender"));
        senderField = new JTextField(20);
        add(senderField);

        add(new JLabel("Subject"));
        subjectField = new JTextField(20);
        add(subjectField);

        add(new JLabel("Content"));
        contentField = new JTextField(20);
        add(contentField);

        // Buttons
        JButton btn = new JButton("Classify");
        add(btn);

        // Output
        outputArea = new JTextArea(10,40);
        add(new JScrollPane(outputArea));

        // Button action
        btn.addActionListener(e -> classifyEmail());

        setVisible(true);
    }

    // ✅ FIXED METHOD
    private void classifyEmail() {

        String sender = senderField.getText();
        String subject = subjectField.getText();
        String content = contentField.getText();

        if(sender.isEmpty() && subject.isEmpty() && content.isEmpty()){
            JOptionPane.showMessageDialog(this, "Enter email details!");
            return;
        }

        Email email = new Email(sender, subject, content);
        String folder = manager.classifyEmail(email);

        outputArea.append("Result: " + folder + "\n");
    }

    public static void main(String[] args) {
        new SmartEmailFilterUI();
    }
}

// ===== Email Class =====
class Email {
    String sender, subject, content;

    public Email(String s, String sub, String c){
        sender = s;
        subject = sub;
        content = c;
    }
}

// ===== Manager =====
class EmailManager {

    List<String> inbox = new ArrayList<>();
    List<String> spam = new ArrayList<>();

    public String classifyEmail(Email e){

        String text = (e.sender + e.subject + e.content).toLowerCase();

        if(text.contains("offer") || text.contains("discount")){
            spam.add(e.sender);
            return "Spam";
        }
        else if(text.contains("meeting") || text.contains("project")){
            return "Work";
        }
        else{
            inbox.add(e.sender);
            return "Inbox";
        }
    }
}