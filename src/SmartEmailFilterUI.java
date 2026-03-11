
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SmartEmailFilterUI extends JFrame {
    
    // Data structures to store emails
    private ArrayList<Email> allEmails;
    private HashMap<String, ArrayList<Email>> folderEmails;
    
    // UI Components that need updating
    private JPanel folderGrid;
    private JTextArea contentArea;
    private JTextField senderField;
    private JTextField subjectField;
    private JLabel[] folderCountLabels;
    
    public SmartEmailFilterUI() {
        // Initialize data storage
        allEmails = new ArrayList<>();
        folderEmails = new HashMap<>();
        folderEmails.put("Primary", new ArrayList<>());
        folderEmails.put("Promotions", new ArrayList<>());
        folderEmails.put("Social", new ArrayList<>());
        folderEmails.put("Updates", new ArrayList<>());
        folderEmails.put("Starred", new ArrayList<>());
        folderEmails.put("Attachments", new ArrayList<>());
        folderEmails.put("Reports", new ArrayList<>());
        folderEmails.put("Work", new ArrayList<>());
        
        // Add some sample data
        addSampleEmails();
        
        setTitle("Smart Email Filtering System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLocationRelativeTo(null);
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(30, 40, 60), 
                                                          0, getHeight(), new Color(15, 25, 40));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        
        // Folders Panel
        JPanel foldersPanel = createFoldersPanel();
        
        // Bottom Panel
        JPanel bottomPanel = createBottomPanel();
        
        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(foldersPanel, BorderLayout.CENTER);
        southPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void addSampleEmails() {
        // Add sample emails to folders
        addEmailToFolder("Primary", "news@company.com", "Weekly Update", "Here's what's new this week...");
        addEmailToFolder("Primary", "hr@company.com", "Meeting Reminder", "Team meeting at 3 PM");
        addEmailToFolder("Promotions", "shop@store.com", "50% Off Sale", "Limited time offer...");
        addEmailToFolder("Promotions", "deals@offers.com", "Flash Sale Today", "Don't miss out!");
        addEmailToFolder("Social", "facebook@notif.com", "John liked your post", "Your photo got 10 likes");
        addEmailToFolder("Updates", "system@update.com", "Software Update v2.0", "New features available");
        addEmailToFolder("Work", "manager@company.com", "Project Deadline", "Please submit by Friday");
        addEmailToFolder("Work", "team@project.com", "Sprint Planning", "Meeting at 10 AM tomorrow");
    }
    
    private void addEmailToFolder(String folder, String sender, String subject, String content) {
        Email email = new Email(sender, subject, content);
        allEmails.add(email);
        
        if (folderEmails.containsKey(folder)) {
            folderEmails.get(folder).add(email);
        } else {
            ArrayList<Email> newFolder = new ArrayList<>();
            newFolder.add(email);
            folderEmails.put(folder, newFolder);
        }
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("✉️ smart filter");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel aiBadge = new JLabel("⚡ AI ready");
        aiBadge.setForeground(new Color(100, 255, 200));
        aiBadge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 255, 200, 100)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(aiBadge, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        // Sender Field
        senderField = new JTextField();
        styleTextField(senderField, "📧");
        senderField.setText("boss@company.com");
        gbc.gridy = 0;
        formPanel.add(createFieldWithIcon("📧", senderField), gbc);
        
        // Subject Field
        subjectField = new JTextField();
        styleTextField(subjectField, "📧");
        subjectField.setText("urgent meeting");
        gbc.gridy = 1;
        formPanel.add(createFieldWithIcon("📧", subjectField), gbc);
        
        // Content Field
        contentArea = new JTextArea();
        contentArea.setRows(3);
        styleTextArea(contentArea);
        contentArea.setText("meeting");
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 200)));
        scrollPane.setBackground(new Color(30, 40, 60));
        gbc.gridy = 2;
        formPanel.add(createFieldWithIcon("📝", scrollPane), gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);
        
        JButton addEmailBtn = createButton("Add Email", new Color(70, 130, 200));
        JButton showFoldersBtn = createButton("Show Folders", new Color(100, 100, 150));
        JButton viewAllBtn = createButton("View All", new Color(150, 100, 150));
        
        // Add action listeners
        addEmailBtn.addActionListener(e -> addNewEmail());
        showFoldersBtn.addActionListener(e -> showFoldersDialog());
        viewAllBtn.addActionListener(e -> showAllEmails());
        
        buttonPanel.add(addEmailBtn);
        buttonPanel.add(showFoldersBtn);
        buttonPanel.add(viewAllBtn);
        
        gbc.gridy = 3;
        formPanel.add(buttonPanel, gbc);
        
        return formPanel;
    }
    
    private JPanel createFieldWithIcon(String icon, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        iconLabel.setForeground(new Color(71, 148, 255));
        
        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addNewEmail() {
        String sender = senderField.getText().trim();
        String subject = subjectField.getText().trim();
        String content = contentArea.getText().trim();
        
        if (sender.isEmpty() || subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in sender and subject fields!", 
                "Missing Information", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Ask which folder to add to
        String[] folders = folderEmails.keySet().toArray(new String[0]);
        String selectedFolder = (String) JOptionPane.showInputDialog(
            this,
            "Select folder to save this email:",
            "Save Email",
            JOptionPane.QUESTION_MESSAGE,
            null,
            folders,
            folders[0]
        );
        
        if (selectedFolder != null) {
            Email newEmail = new Email(sender, subject, content);
            allEmails.add(newEmail);
            folderEmails.get(selectedFolder).add(newEmail);
            
            JOptionPane.showMessageDialog(this, 
                "Email added to " + selectedFolder + " folder!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Update folder counts
            updateFolderCounts();
            
            // Clear fields
            senderField.setText("");
            subjectField.setText("");
            contentArea.setText("");
        }
    }
    
    private void showFoldersDialog() {
        JDialog foldersDialog = new JDialog(this, "Email Folders", true);
        foldersDialog.setSize(500, 400);
        foldersDialog.setLocationRelativeTo(this);
        
        JPanel dialogPanel = new JPanel(new BorderLayout());
        dialogPanel.setBackground(new Color(30, 40, 60));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(40, 55, 80));
        tabbedPane.setForeground(Color.WHITE);
        
        // Create tabs for each folder
        for (Map.Entry<String, ArrayList<Email>> entry : folderEmails.entrySet()) {
            String folderName = entry.getKey();
            ArrayList<Email> emails = entry.getValue();
            
            JPanel folderPanel = createEmailListPanel(folderName, emails);
            tabbedPane.addTab(folderName + " (" + emails.size() + ")", folderPanel);
        }
        
        dialogPanel.add(tabbedPane, BorderLayout.CENTER);
        foldersDialog.add(dialogPanel);
        foldersDialog.setVisible(true);
    }
    
    private JPanel createEmailListPanel(String folderName, ArrayList<Email> emails) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 40, 60));
        
        if (emails.isEmpty()) {
            JLabel emptyLabel = new JLabel("No emails in this folder", SwingConstants.CENTER);
            emptyLabel.setForeground(new Color(150, 150, 200));
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            panel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Email email : emails) {
                listModel.addElement(email.toString());
            }
            
            JList<String> emailList = new JList<>(listModel);
            emailList.setBackground(new Color(40, 55, 80));
            emailList.setForeground(Color.WHITE);
            emailList.setFont(new Font("Arial", Font.PLAIN, 14));
            emailList.setSelectionBackground(new Color(70, 130, 200));
            
            // Add double-click listener to view email details
            emailList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int index = emailList.locationToIndex(evt.getPoint());
                        if (index >= 0) {
                            showEmailDetails(emails.get(index));
                        }
                    }
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(emailList);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 200)));
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        return panel;
    }
    
    private void showEmailDetails(Email email) {
        JDialog detailsDialog = new JDialog(this, "Email Details", true);
        detailsDialog.setSize(400, 300);
        detailsDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 40, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        // From
        JLabel fromLabel = new JLabel("From: " + email.sender);
        fromLabel.setForeground(new Color(100, 200, 255));
        fromLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(fromLabel, gbc);
        
        // Subject
        JLabel subjectLabel = new JLabel("Subject: " + email.subject);
        subjectLabel.setForeground(Color.WHITE);
        subjectLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(subjectLabel, gbc);
        
        // Content
        JTextArea contentArea = new JTextArea(email.content);
        contentArea.setEditable(false);
        contentArea.setBackground(new Color(40, 55, 80));
        contentArea.setForeground(Color.WHITE);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setPreferredSize(new Dimension(350, 150));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 200)));
        panel.add(scrollPane, gbc);
        
        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> detailsDialog.dispose());
        closeBtn.setBackground(new Color(70, 130, 200));
        closeBtn.setForeground(Color.WHITE);
        panel.add(closeBtn, gbc);
        
        detailsDialog.add(panel);
        detailsDialog.setVisible(true);
    }
    
    private void showAllEmails() {
        JDialog allEmailsDialog = new JDialog(this, "All Emails", true);
        allEmailsDialog.setSize(600, 500);
        allEmailsDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 40, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Email email : allEmails) {
            listModel.addElement(email.toString());
        }
        
        JList<String> emailList = new JList<>(listModel);
        emailList.setBackground(new Color(40, 55, 80));
        emailList.setForeground(Color.WHITE);
        emailList.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(emailList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 200)));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        allEmailsDialog.add(panel);
        allEmailsDialog.setVisible(true);
    }
    
    private JPanel createFoldersPanel() {
        JPanel foldersPanel = new JPanel(new BorderLayout());
        foldersPanel.setOpaque(false);
        foldersPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 255, 100)),
            " your smart folders ",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(200, 200, 255)
        ));
        
        // Folder header with +new folder
        JPanel folderHeader = new JPanel(new BorderLayout());
        folderHeader.setOpaque(false);
        
        JLabel newFolderLabel = new JLabel("➕ new folder");
        newFolderLabel.setForeground(new Color(150, 200, 255));
        newFolderLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newFolderLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                createNewFolder();
            }
        });
        folderHeader.add(newFolderLabel, BorderLayout.EAST);
        
        // Folder grid
        folderGrid = new JPanel(new GridLayout(2, 4, 10, 10));
        folderGrid.setOpaque(false);
        
        updateFolderGrid();
        
        foldersPanel.add(folderHeader, BorderLayout.NORTH);
        foldersPanel.add(folderGrid, BorderLayout.CENTER);
        
        return foldersPanel;
    }
    
    private void updateFolderGrid() {
        folderGrid.removeAll();
        
        String[][] folders = {
            {"📥", "Primary", String.valueOf(folderEmails.get("Primary").size())},
            {"🏷️", "Promotions", String.valueOf(folderEmails.get("Promotions").size())},
            {"👥", "Social", String.valueOf(folderEmails.get("Social").size())},
            {"📋", "Updates", String.valueOf(folderEmails.get("Updates").size())},
            {"⭐", "Starred", String.valueOf(folderEmails.get("Starred").size())},
            {"📎", "Attachments", String.valueOf(folderEmails.get("Attachments").size())},
            {"📊", "Reports", String.valueOf(folderEmails.get("Reports").size())},
            {"⚙️", "Work", String.valueOf(folderEmails.get("Work").size())}
        };
        
        folderCountLabels = new JLabel[folders.length];
        
        for (int i = 0; i < folders.length; i++) {
            String[] folder = folders[i];
            JPanel folderItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
            folderItem.setBackground(new Color(40, 55, 80));
            folderItem.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255, 100)));
            folderItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            final String folderName = folder[1];
            folderItem.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showFolderContents(folderName);
                }
            });
            
            JLabel iconLabel = new JLabel(folder[0]);
            iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            
            JLabel nameLabel = new JLabel(folder[1]);
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            
            folderItem.add(iconLabel);
            folderItem.add(nameLabel);
            
            if (!folder[2].equals("0")) {
                JLabel countLabel = new JLabel(folder[2]);
                countLabel.setForeground(new Color(150, 200, 255));
                countLabel.setBackground(new Color(50, 70, 100));
                countLabel.setOpaque(true);
                countLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
                countLabel.setFont(new Font("Arial", Font.BOLD, 10));
                folderItem.add(countLabel);
                folderCountLabels[i] = countLabel;
            }
            
            folderGrid.add(folderItem);
        }
        
        folderGrid.revalidate();
        folderGrid.repaint();
    }
    
    private void updateFolderCounts() {
        Component[] components = folderGrid.getComponents();
        int index = 0;
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel folderItem = (JPanel) comp;
                String folderName = ((JLabel) folderItem.getComponent(1)).getText();
                int count = folderEmails.get(folderName).size();
                
                // Update or add count label
                if (folderItem.getComponentCount() > 2) {
                    // Update existing count label
                    JLabel countLabel = (JLabel) folderItem.getComponent(2);
                    countLabel.setText(String.valueOf(count));
                    countLabel.setVisible(count > 0);
                } else if (count > 0) {
                    // Add new count label
                    JLabel countLabel = new JLabel(String.valueOf(count));
                    countLabel.setForeground(new Color(150, 200, 255));
                    countLabel.setBackground(new Color(50, 70, 100));
                    countLabel.setOpaque(true);
                    countLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
                    countLabel.setFont(new Font("Arial", Font.BOLD, 10));
                    folderItem.add(countLabel);
                }
                folderItem.revalidate();
                folderItem.repaint();
            }
            index++;
        }
    }
    
    private void showFolderContents(String folderName) {
        ArrayList<Email> emails = folderEmails.get(folderName);
        
        JDialog folderDialog = new JDialog(this, folderName + " Folder", true);
        folderDialog.setSize(500, 400);
        folderDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(30, 40, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        if (emails.isEmpty()) {
            JLabel emptyLabel = new JLabel("No emails in " + folderName, SwingConstants.CENTER);
            emptyLabel.setForeground(new Color(150, 150, 200));
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            panel.add(emptyLabel, BorderLayout.CENTER);
        } else {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (Email email : emails) {
                listModel.addElement(email.toString());
            }
            
            JList<String> emailList = new JList<>(listModel);
            emailList.setBackground(new Color(40, 55, 80));
            emailList.setForeground(Color.WHITE);
            emailList.setFont(new Font("Arial", Font.PLAIN, 14));
            
            emailList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        int index = emailList.locationToIndex(evt.getPoint());
                        if (index >= 0) {
                            showEmailDetails(emails.get(index));
                        }
                    }
                }
            });
            
            JScrollPane scrollPane = new JScrollPane(emailList);
            scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 120, 200)));
            panel.add(scrollPane, BorderLayout.CENTER);
        }
        
        folderDialog.add(panel);
        folderDialog.setVisible(true);
    }
    
    private void createNewFolder() {
        String folderName = JOptionPane.showInputDialog(this, 
            "Enter new folder name:", 
            "Create Folder", 
            JOptionPane.QUESTION_MESSAGE);
        
        if (folderName != null && !folderName.trim().isEmpty()) {
            if (!folderEmails.containsKey(folderName)) {
                folderEmails.put(folderName, new ArrayList<>());
                JOptionPane.showMessageDialog(this, 
                    "Folder '" + folderName + "' created!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                updateFolderGrid();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Folder already exists!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        JLabel askLabel = new JLabel("Ask anything 💬");
        askLabel.setForeground(new Color(150, 150, 200));
        askLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        bottomPanel.add(askLabel);
        
        return bottomPanel;
    }
    
    private void styleTextField(JTextField field, String icon) {
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBackground(new Color(30, 40, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 120, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
    
    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setBackground(new Color(30, 40, 60));
        area.setForeground(Color.WHITE);
        area.setCaretColor(Color.WHITE);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    // Email class to store email data
    private class Email {
        String sender;
        String subject;
        String content;
        
        Email(String sender, String subject, String content) {
            this.sender = sender;
            this.subject = subject;
            this.content = content;
        }
        
        @Override
        public String toString() {
            return sender + " - " + subject;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new SmartEmailFilterUI().setVisible(true);
        });
    }
}