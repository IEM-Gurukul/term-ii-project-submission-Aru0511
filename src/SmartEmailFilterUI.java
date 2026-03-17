
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class SmartEmailFilterUI extends JFrame {

    private EmailManager manager;
    private JTextField senderField, subjectField, contentField;
    private JTextArea outputArea;
    private JProgressBar classificationProgress;
    private JLabel statusLabel;
    private JPanel classificationPanel;

    public SmartEmailFilterUI() {
        manager = new EmailManager();
        initUI();
    }

    private void initUI() {

        setTitle("Smart Email Classifier");
        setSize(850,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20,30,20,30));

        mainPanel.add(createHeader());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createInputSection());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createClassificationStatus());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createActionButtons());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createOutputSection());

        add(mainPanel);

        setVisible(true);
    }

    private Component createHeader() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Smart Email Classifier",JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Automatic Email Categorization",JLabel.CENTER);
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,14));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private Component createInputSection() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));

        panel.add(new JLabel("Sender:"));
        senderField = new JTextField();
        panel.add(senderField);

        panel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        panel.add(subjectField);

        panel.add(new JLabel("Content:"));
        contentField = new JTextField();
        panel.add(contentField);

        return panel;
    }

    private Component createClassificationStatus() {

        classificationPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel("Ready",JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI",Font.BOLD,14));

        classificationProgress = new JProgressBar();
        classificationProgress.setIndeterminate(true);
        classificationProgress.setVisible(false);

        classificationPanel.add(statusLabel,BorderLayout.CENTER);
        classificationPanel.add(classificationProgress,BorderLayout.SOUTH);

        return classificationPanel;
    }

    private Component createActionButtons() {

        JPanel panel = new JPanel(new FlowLayout());

        JButton classifyBtn = new JButton("Classify Email");
        JButton refreshBtn = new JButton("Refresh Folders");
        JButton clearBtn = new JButton("Clear");

        classifyBtn.addActionListener(e -> classifyEmail());
        refreshBtn.addActionListener(e -> refreshFolders());
        clearBtn.addActionListener(e -> clearFields());

        panel.add(classifyBtn);
        panel.add(refreshBtn);
        panel.add(clearBtn);

        return panel;
    }

    private Component createOutputSection() {

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Results",JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,16));

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(outputArea);

        panel.add(title,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);

        return panel;
    }

    private void classifyEmail() {

        String sender = senderField.getText();
        String subject = subjectField.getText();
        String content = contentField.getText();

        if(sender.isEmpty() && subject.isEmpty() && content.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter email data");
            return;
        }

        classificationProgress.setVisible(true);
        statusLabel.setText("Analyzing email...");

        Timer timer = new Timer(1000,e -> {

            Email email = new Email(sender,subject,content);
            String folder = manager.classifyEmail(email);

            statusLabel.setText("Email classified to: "+folder);
            classificationProgress.setVisible(false);

            outputArea.append(sender+" | "+subject+" -> "+folder+"\n");

        });

        timer.setRepeats(false);
        timer.start();
    }

    private void refreshFolders() {

        outputArea.append("\n--- Folder Contents ---\n");
        manager.showFolders(outputArea);
        outputArea.append("\n");
    }

    private void clearFields(){

        senderField.setText("");
        subjectField.setText("");
        contentField.setText("");
        outputArea.setText("");
        statusLabel.setText("Ready");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }catch(Exception e){}

            new SmartEmailFilterUI();
        });
    }
}

class Email {

    String sender;
    String subject;
    String content;

    public Email(String sender,String subject,String content){
        this.sender=sender;
        this.subject=subject;
        this.content=content;
    }

    public String toString(){
        return "From:"+sender+" Subject:"+subject;
    }
}

class EmailManager {

    private List<String> inbox = new ArrayList<>();
    private List<String> promotions = new ArrayList<>();
    private List<String> social = new ArrayList<>();
    private List<String> spam = new ArrayList<>();

    public String classifyEmail(Email email){

        String text=(email.sender+" "+email.subject+" "+email.content).toLowerCase();

        if(text.contains("sale")||text.contains("discount")||text.contains("offer")){
            promotions.add(email.toString());
            return "Promotions";
        }
        else if(text.contains("facebook")||text.contains("instagram")||text.contains("twitter")){
            social.add(email.toString());
            return "Social";
        }
        else if(text.contains("lottery")||text.contains("free money")||text.contains("click")){
            spam.add(email.toString());
            return "Spam";
        }
        else{
            inbox.add(email.toString());
            return "Inbox";
        }
    }

    public void showFolders(JTextArea area){

        area.append("Inbox: "+inbox+"\n");
        area.append("Promotions: "+promotions+"\n");
        area.append("Social: "+social+"\n");
        area.append("Spam: "+spam+"\n");
    }
}