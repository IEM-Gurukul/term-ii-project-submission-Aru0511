public class SpamFilterStrategy implements FilterStrategy {

    public boolean filter(Email email) {

        if(email.getSubject().toLowerCase().contains("offer")) {
            return true;
        }

        return false;
    }

}