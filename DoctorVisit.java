public class DoctorVisit extends Reservation {
    
    //fields
    private String doctorName;
    private String patientID;
    private String insuranceCode;

    public DoctorVisit(String reservationID, String patientName, String reservationDate) {
        super(reservationID, patientName, reservationDate);
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

    //overriding method from Reservation
    @Override
    public String getConfirmationDetails() {
        return "Doctor Visit Details:" +
               "\nReservation ID: " + reservationID + 
               "\nPatient ID: " + patientID +
               "\nPatient Name: " + patientName +
               "\nReservation Date: " + reservationDate +
               "\nDoctor Name: " + doctorName +
               "\nInsurance Code: " + insuranceCode;
    }
}
