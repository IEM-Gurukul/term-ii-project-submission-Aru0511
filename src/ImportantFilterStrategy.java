public class ImportantFilterStrategy implements FilterStrategy {

    public boolean filter(Email email) {

        if(email.getSubject().toLowerCase().contains("urgent")) {
            return true;
        }

        return false;
    }

}