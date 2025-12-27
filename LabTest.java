public class LabTest extends Reservation {
    
    //fields
    private String typeOfTest = "Lab Test";
    private String patientID;
    private String insuranceCode;
    private String doctorName;


    //constructor
    public LabTest(String reservationID, String patientID, String patientName, String reservationDate,
                   String doctorName, String insuranceCode) {
        super(reservationID, patientName, reservationDate);
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.insuranceCode = insuranceCode;
    }

    //getters
    public String getTypeOfTest(){
        return typeOfTest;
    }

    public String getPatientID(){
        return patientID;
    }

    public String getInsuranceCode(){
        return insuranceCode;
    }

    public String getDoctorName(){
        return doctorName;
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
    public String getConfirmationDetails(){
        return "Lab Test Details:" +
               "\nReservation ID: " + reservationID +
               "\nPatient ID: " + patientID +
               "\nPatient Name: " + patientName +
               "\nReservation Date: " + reservationDate +
               "\nInsurance Code: " + insuranceCode +
               "\nDoctor Name: " + doctorName +
               "\nType of Test: " + typeOfTest;
    }
}
