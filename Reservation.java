abstract class Reservation implements Payable{

    //fields
    protected String reservationID;
    protected String patientName;
    protected String reservationDate;
    protected double amount;
    protected boolean checkPay;
    protected String contactNo;
    protected boolean reminderCheck;

    //constructor
    public Reservation(String reservationID, String patientName, String reservationDate, String contactNo){
        this.reservationID = reservationID;
        this.patientName = patientName;
        this.reservationDate = reservationDate;
        this.contactNo = contactNo;
    }

    public boolean getReminderCheck() {
        return reminderCheck;
    }

    public void setReminderCheck(boolean reminderCheck) {
        this.reminderCheck = reminderCheck;
    }

    //getters
    public String getReservationID(){
        return reservationID;
    }

    public String getPatientName(){
        return patientName;
    }
 
    public String getReservationDate(){
        return reservationDate;
    }

    public String getContactNo(){
        return contactNo;
    }

     //setter for reservationID so managers can assign IDs
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    
    //implementing Payable
    @Override
    public void processPayment() {
        if (!checkPay) {
            // Simulate payment processing logic
            System.out.println("Please confirm payment");
        } else {
            System.out.println("Thank you for your payment.");
        }
    }

    @Override
    public void applyDiscount(double discount) {
        if (discount > 0 && discount <= 100) {
            amount = amount - (amount * (discount / 100));
            System.out.println("Discount of " + discount + "% applied. New amount: $" + amount);
        } else {
            System.out.println("An error has occured, please check again.");
        }
    }

    public abstract String getConfirmationDetails();


    //Nested class

    public static class ClientDetails{
        
        public String clientReservationId;
        public String clientName;
        public String clientContactNo;
        public String clientAssignedDoc;
        public boolean reminderCheck;

        public ClientDetails(String clientReservationId, String clientName, String clientContactNo, String clientAssignedDoc, boolean reminderCheck){
            this.clientReservationId = clientReservationId;
            this.clientName = clientName;
            this.clientContactNo = clientContactNo;
            this.clientAssignedDoc = clientAssignedDoc;
            this.reminderCheck = reminderCheck;
        }

        public String toDisplayString() {
            String doc = clientAssignedDoc == null ? "" : clientAssignedDoc;
            String contact = clientContactNo == null ? "" : clientContactNo;
            return clientReservationId + " | " + clientName + " | " + doc + "\n" + contact;
        }

        public class Reminder{
            public boolean reminderCheck = false;

            public Reminder(boolean reminderCheck){
                this.reminderCheck = reminderCheck;
            }

            public boolean getReminderCheck() {
            return reminderCheck;
        }
        }
    }
}