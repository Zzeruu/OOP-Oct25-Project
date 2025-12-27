public class DoctorVisit extends Reservation {
    
    //fields
    private String typeOfTest = "Doctor Visit";
    private String doctorName;
    private String patientID;
    private String insuranceCode;
    

    //constructor 
    public DoctorVisit(String reservationID, String patientID, String patientName, String reservationDate, String doctorName, String insuranceCode) {
        super(reservationID, patientName, reservationDate);
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.insuranceCode = insuranceCode;
    }
    

    //getters
    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public String getTypeOfTest() {
        return typeOfTest;
    }

    //setters
    public void setReservationID(String reservationID){
        this.reservationID = reservationID;
    }
    public void setPatientName(String patientName){
        this.patientName = patientName;
    }
    public void setReservationDate(String reservationDate){
        this.reservationDate = reservationDate;
    }

    //overriding method from Reservation
    @Override
    public String getConfirmationDetails() {
        return "Doctor Visit Details:" +
               "\nReservation ID: " + reservationID + 
               "\nPatient ID: " + patientID +
               "\nPatient Name: " + patientName +
               "\nReservation Date: " + reservationDate +
               "\nDoctor Name: " + doctorName +
               "\nInsurance Code: " + insuranceCode +
               "\nType of Test: " + typeOfTest;
    }
}
