abstract class Reservation{

    //fields
    protected String reservationID;
    protected String patientName;
    protected String reservationDate;

    //constructor
    public Reservation(String reservationID, String patientName, String reservationDate){
        this.reservationID = reservationID;
        this.patientName = patientName;
        this.reservationDate = reservationDate;
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

     //setter for reservationID so managers can assign IDs
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }
    
    public abstract String getConfirmationDetails();
}