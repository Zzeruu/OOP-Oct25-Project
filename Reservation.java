abstract class Reservation{

    //fields
    protected String reservationID;
    protected String customerName;
    protected String reservationDate;

    //constructor
    public Reservation(String reservationID, String customerName, String reservationDate){
        this.reservationID = reservationID;
        this.customerName = customerName;
        this.reservationDate = reservationDate;
    }

    //getters
    public String getReservationID(){
        return reservationID;
    }

    public String getCustomerName(){
        return customerName;
    }
 
    public String getReservationDate(){
        return reservationDate;
    }
    public abstract String getConfirmationDetails();

    // static nested class for client details
    public static class ClientDetails {
        private final String name;
        private final String contact;

        public ClientDetails(String name, String contact) {
            this.name = name;
            this.contact = contact;
        }

        public String getName() { return name; }
        public String getContact() { return contact; }
    }

    // inner class for reminders (non-static)
    public class Reminder {
        private int daysBefore;

        public Reminder(int daysBefore) {
            this.daysBefore = daysBefore;
        }

        public void alert() {
            System.out.println("Reminder: Reservation " + reservationID + " for " + customerName + " is coming up on " + reservationDate + ".");
        }
    }
}