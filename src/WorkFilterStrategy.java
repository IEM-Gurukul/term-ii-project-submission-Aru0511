public class WorkFilterStrategy implements FilterStrategy {

    public boolean filter(Email email) {

        if(email.getSender().contains("company.com")) {
            return true;
        }

        return false;
    }

}