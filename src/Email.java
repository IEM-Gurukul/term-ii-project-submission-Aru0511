
public class Email {

    private String sender;
    private String subject;
    private String content;

    public Email(String sender, String subject, String content) {
        this.sender = sender;
        this.subject = subject;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}