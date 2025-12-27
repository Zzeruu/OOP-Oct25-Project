import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReservationManager{
	private final List<Reservation> reservationList = new ArrayList<>();
  private final String reservationLog = "reservations.txt";


  
	public void addReservation(Reservation reservation) {
		if (reservation == null) return;

		// assign a reservation ID if missing, using BookingID
		if (reservation.getReservationID() == null || reservation.getReservationID().isEmpty()) {
			BookingID bid = new BookingID("reservations.txt");
			reservation.setReservationID(bid.getReservationId());
		}

		reservationList.add(reservation);
		// persist after adding
		saveToFile(reservationLog);
	}

	/*public Reservation findByID(String id) {
		for (Reservation r : reservations) {
			if (r.getReservationID().equals(id)) return r;
		}
		return null;
	}*/ //using later if I can

	public int getTotalReservations() {
		return reservationList.size();
	}

	public String generateReport() {
		List<String> report = generateReportLines();
		if (report.isEmpty()) return "No reservations.";
		return report.stream().collect(Collectors.joining("\n"));
	}

	/**
	 * Produce a list of report lines (one entry per element). GUI can use these directly.
	 */
	public List<String> generateReportLines() {
		List<String> report = new ArrayList<>();
		if (reservationList.isEmpty()) return report;
		report.add("Reservation Report");
		report.add("==================");
		report.add("");
		for (Reservation reservation : reservationList) {
			report.add(reservation.getConfirmationDetails());
			report.add("");
		}
		report.add("Total: " + getTotalReservations());
		return report;
	}

	public List<Reservation> getAllReservations() {
		return Collections.unmodifiableList(reservationList);
	}

  /* */
	void saveToFile(String resevationLog) {
		List<String> lines = new ArrayList<>();
		for (Reservation r : reservationList) {
			String type = "Reservation";
			if (r instanceof DoctorVisit) type = "DoctorVisit";
			else if (r instanceof LabTest) type = "LabTest";
			else if (r instanceof SurgerySlot) type = "SurgerySlot";

			String pid = "";
			String doc = "";
			String ins = "";
			if (r instanceof DoctorVisit) {
				DoctorVisit d = (DoctorVisit) r;
				pid = d.getPatientID() == null ? "" : d.getPatientID();
				doc = d.getDoctorName() == null ? "" : d.getDoctorName();
				ins = d.getInsuranceCode() == null ? "" : d.getInsuranceCode();
			} else if (r instanceof LabTest) {
				LabTest l = (LabTest) r;
				pid = l.getPatientID() == null ? "" : l.getPatientID();
				doc = l.getDoctorName() == null ? "" : l.getDoctorName();
				ins = l.getInsuranceCode() == null ? "" : l.getInsuranceCode();
			} else if (r instanceof SurgerySlot) {
				SurgerySlot s = (SurgerySlot) r;
				pid = s.getPatientID() == null ? "" : s.getPatientID();
				doc = s.getDoctorName() == null ? "" : s.getDoctorName();
				ins = s.getInsuranceCode() == null ? "" : s.getInsuranceCode();
			}

			// format: type|reservationID|patientID|patientName|reservationDate|doctorName|insuranceCode
			String line = String.join("|", type,
					r.getReservationID() == null ? "" : r.getReservationID(),
					pid,
					r.getPatientName() == null ? "" : r.getPatientName(),
					r.getReservationDate() == null ? "" : r.getReservationDate(),
					doc,
					ins);
			lines.add(line);
		}

		Path p = Paths.get(reservationLog);
		try {
			Files.write(p, lines, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void loadFromFile(String reservationLog){
		Path p = Paths.get(reservationLog);
		if (!Files.exists(p)) return;
		try {
			List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
			reservationList.clear();
			for (String line : lines) {
				if (line.trim().isEmpty()) continue;
				String[] parts = line.split("\\|", -1);
				if (parts.length < 7) continue;
				String type = parts[0];
				String id = parts[1];
				String patientid = parts[2].isEmpty() ? null : parts[2];
				String patientName = parts[3].isEmpty() ? null : parts[3];
				String reservationDate = parts[4].isEmpty() ? null : parts[4];
				String docName = parts[5].isEmpty() ? null : parts[5];
				String insCode = parts[6].isEmpty() ? null : parts[6];

				Reservation r;
				switch (type) {
					case "DoctorVisit": r = new DoctorVisit(id, patientid, patientName, reservationDate, docName, insCode); break;
					case "LabTest": r = new LabTest(id, patientid, patientName, reservationDate, docName, insCode); break;
					case "SurgerySlot": r = new SurgerySlot(id, patientid, patientName, reservationDate, docName, insCode); break;
					default:
						r = new Reservation(id, patientName, reservationDate) {
              
							@Override
							public String getConfirmationDetails() { 
                return "Reservation: " + reservationID; 
              }
						};
				}
				reservationList.add(r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
