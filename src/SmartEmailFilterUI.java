
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SmartEmailFilterUI extends JFrame {

    private JTextField senderField, subjectField, contentField;
    private JTextArea outputArea;
    private EmailManager manager;

    public SmartEmailFilterUI() {
        manager = new EmailManager();
        initUI();
    }

    private void initUI() {

        setTitle("Smart Email Filtering System");
        setSize(700,550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(240, 248, 255)); // light blue
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20,20,20,20));

        // ===== Title =====
        JLabel title = new JLabel("Smart Email Filter", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(25, 25, 112)); // dark blue
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(3,2,10,10));
        inputPanel.setBackground(new Color(224, 255, 255)); // cyan

        senderField = new JTextField();
        subjectField = new JTextField();
        contentField = new JTextField();

        inputPanel.add(new JLabel("Sender:"));
        inputPanel.add(senderField);

        inputPanel.add(new JLabel("Subject:"));
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("Content:"));
        inputPanel.add(contentField);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton classifyBtn = new JButton("Classify Email");
        JButton showBtn = new JButton("Show Folders");
        JButton clearBtn = new JButton("Clear");

        // Button colors
        classifyBtn.setBackground(new Color(60, 179, 113)); // green
        classifyBtn.setForeground(Color.WHITE);

        showBtn.setBackground(new Color(70, 130, 180)); // blue
        showBtn.setForeground(Color.WHITE);

        clearBtn.setBackground(new Color(220, 20, 60)); // red
        clearBtn.setForeground(Color.WHITE);

        buttonPanel.add(classifyBtn);
        buttonPanel.add(showBtn);
        buttonPanel.add(clearBtn);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // ===== Output Area =====
        outputArea = new JTextArea(10,40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 250, 240)); // light cream
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(outputArea);

        mainPanel.add(scroll);

        add(mainPanel);

        // ===== Actions =====

        classifyBtn.addActionListener(e -> classifyEmail());
        showBtn.addActionListener(e -> showFolders());
        clearBtn.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void classifyEmail() {

        String sender = senderField.getText();
        String subject = subjectField.getText();
        String content = contentField.getText();

        if(sender.isEmpty() && subject.isEmpty() && content.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter email details!");
            return;
        }

        Email email = new Email(sender,subject,content);
        String folder = manager.classifyEmail(email);

        outputArea.append("📧 " + sender + "\n");
        outputArea.append("📌 " + subject + "\n");
        outputArea.append("➡ Folder: " + folder + "\n\n");
    }

    private void showFolders() {
        outputArea.append("\n=== Folder Contents ===\n");
        manager.showFolders(outputArea);
    }

    private void clearFields() {
        senderField.setText("");
        subjectField.setText("");
        contentField.setText("");
        outputArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SmartEmailFilterUI());
    }
}

// ===== Email Class =====
class Email {
    String sender, subject, content;

    public Email(String sender, String subject, String content) {
        this.sender = sender;
        this.subject = subject;
        this.content = content;
    }

    public String toString() {
        return sender + " | " + subject;
    }
}

// ===== EmailManager =====
class EmailManager {

    private List<String> inbox = new ArrayList<>();
    private List<String> spam = new ArrayList<>();
    private List<String> work = new ArrayList<>();

    public String classifyEmail(Email email) {

        String text = (email.sender + " " + email.subject + " " + email.content).toLowerCase();

        if(text.contains("offer") || text.contains("discount")){
            spam.add(email.toString());
            return "Spam";
        }
        else if(text.contains("meeting") || text.contains("project")){
            work.add(email.toString());
            return "Work";
        }
        else{
            inbox.add(email.toString());
            return "Inbox";
        }
    }

    public void showFolders(JTextArea area){

        area.append("Inbox: " + inbox + "\n");
        area.append("Work: " + work + "\n");
        area.append("Spam: " + spam + "\n");
    }
}