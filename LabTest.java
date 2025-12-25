public class LabTest extends Reservation {
    
    //fields
    private String typeOfTest;
    private String patientID;
    private String insuranceCode;
    private String doctorName;

    public LabTest(String reservationID, String patientName, String reservationDate){
        super(reservationID, patientName, reservationDate);
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
