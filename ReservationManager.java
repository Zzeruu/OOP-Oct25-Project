import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReservationManager{
	private final List<Reservation> reservationList = new ArrayList<>();
  private final String reservationLog = "reservations.txt";

  //constructor
	public void addReservation(Reservation reservation) {
		if (reservation == null) return;

		if (reservation.getReservationID() == null || reservation.getReservationID().isEmpty()) {
			BookingID bookid = new BookingID("reservations.txt");
			reservation.setReservationID(bookid.getReservationId());
		}

		reservationList.add(reservation);
		saveToFile(reservationLog);
	}

	/*public Reservation findByID(String id) {
		for (Reservation r : reservations) {
			if (r.getReservationID().equals(id)) return r;
		}
		return null;
	}*/ //using later if I can

  //report and total reservation methods=============================================================
	public int getTotalReservations() {
		return reservationList.size();
	}

	public String generateReport() {
		List<String> report = reportLines();
		if (report.isEmpty()) return "No reservations.";
		return report.stream().collect(Collectors.joining("\n"));
	}

	
	public List<String> reportLines() {
		List<String> report = new ArrayList<>();
		if (reservationList.isEmpty()) 
		return report;

		for (Reservation reservation : reservationList) {
			report.add(reservation.getConfirmationDetails());
			report.add("");
		}
		
		return report;
	}

	public List<Reservation> getAllReservations() {
		return Collections.unmodifiableList(reservationList);
	}

	//making separate display for ClientDetails======================================================
	public List<String> getClientDetailsLines() {
		List<String> lines = new ArrayList<>();
		for (Reservation r : reservationList) {
			String id = r.getReservationID() == null ? "" : r.getReservationID();
			String name = r.getPatientName() == null ? "" : r.getPatientName();
			String contact = r.getContactNo() == null ? "" : r.getContactNo();
			String doc = "";
			if (r instanceof DoctorVisit) doc = ((DoctorVisit) r).getDoctorName() == null ? "" : ((DoctorVisit) r).getDoctorName();
			else if (r instanceof LabTest) doc = ((LabTest) r).getDoctorName() == null ? "" : ((LabTest) r).getDoctorName();
			else if (r instanceof SurgerySlot) doc = ((SurgerySlot) r).getDoctorName() == null ? "" : ((SurgerySlot) r).getDoctorName();

			boolean reminder = r.getReminderCheck();
			Reservation.ClientDetails cd = new Reservation.ClientDetails(id, name, contact, doc, reminder);
			// build HTML block for this client
			String display = cd.toDisplayString();
			String html = display.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
			if (reminder) html = "<div style=\"color:red\">" + html + "</div>";
			else html = "<div>" + html + "</div>";
			lines.add(html);
		}
		return lines;
	}

  //SAVEFILE METHOD=================================================================================
	void saveToFile(String resevationLog) {
		List<String> lines = new ArrayList<>();
		for (Reservation r : reservationList) {
			String type = "Reservation";
			if (r instanceof DoctorVisit) type = "DoctorVisit";
			else if (r instanceof LabTest) type = "LabTest";
			else if (r instanceof SurgerySlot) type = "SurgerySlot";

			String patientid = "";
			String doctorName = "";
			String insuranceCode = "";
			String contactNo = "";

			patientid = (r instanceof DoctorVisit) ? (((DoctorVisit) r).getPatientID() == null ? "" : ((DoctorVisit) r).getPatientID()) : "";
			doctorName = (r instanceof DoctorVisit) ? (((DoctorVisit) r).getDoctorName() == null ? "" : ((DoctorVisit) r).getDoctorName()) : "";
			if (r instanceof LabTest) {
				patientid = ((LabTest) r).getPatientID() == null ? "" : ((LabTest) r).getPatientID();
				doctorName = ((LabTest) r).getDoctorName() == null ? "" : ((LabTest) r).getDoctorName();
			}
			if (r instanceof SurgerySlot) {
				patientid = ((SurgerySlot) r).getPatientID() == null ? "" : ((SurgerySlot) r).getPatientID();
				doctorName = ((SurgerySlot) r).getDoctorName() == null ? "" : ((SurgerySlot) r).getDoctorName();
			}

			if (r instanceof DoctorVisit) insuranceCode = ((DoctorVisit) r).getInsuranceCode() == null ? "" : ((DoctorVisit) r).getInsuranceCode();
			if (r instanceof LabTest) insuranceCode = ((LabTest) r).getInsuranceCode() == null ? "" : ((LabTest) r).getInsuranceCode();
			if (r instanceof SurgerySlot) insuranceCode = ((SurgerySlot) r).getInsuranceCode() == null ? "" : ((SurgerySlot) r).getInsuranceCode();

			contactNo = r.getContactNo() == null ? "" : r.getContactNo();

				String line = String.join("|", type, r.getReservationID() == null ? "" : r.getReservationID(),
						patientid, r.getPatientName() == null ? "" : r.getPatientName(),
						r.getReservationDate() == null ? "" : r.getReservationDate(),
						doctorName,insuranceCode,contactNo,Boolean.toString(r.getReminderCheck()));

			lines.add(line);
		}

		Path p = Paths.get(reservationLog);
		BufferedWriter writer = null;

		try {
			writer = Files.newBufferedWriter(p, StandardCharsets.UTF_8);
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (writer != null) {
				try {
					writer.close();

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

  //LOADFILE METHOD=================================================================================
	void loadFromFile(String reservationLog){
		Path p = Paths.get(reservationLog);
		if (!Files.exists(p)) return;
		BufferedReader reader = null;

		try {
			reader = Files.newBufferedReader(p, StandardCharsets.UTF_8);
			reservationList.clear();
			String line;

			while ((line = reader.readLine()) != null) {
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
				String contactNo = (parts.length > 7 && !parts[7].isEmpty()) ? parts[7] : null;
				boolean reminder = (parts.length > 8 && !parts[8].isEmpty()) ? Boolean.parseBoolean(parts[8]) : false;

				Reservation r;

					switch (type) {
					case "DoctorVisit": r = new DoctorVisit(id, patientid, patientName, reservationDate, docName, insCode, contactNo); break;
					case "LabTest": r = new LabTest(id, patientid, patientName, reservationDate, docName, insCode, contactNo); break;
					case "SurgerySlot": r = new SurgerySlot(id, patientid, patientName, reservationDate, docName, insCode, contactNo); break;
					default:
						r = new Reservation(id, patientName, reservationDate, contactNo) {
							@Override
							public String getConfirmationDetails() {
								return "Reservation: " + reservationID;
							}
						};
						
					}

					//setting reminderCheck for all reservations
					if (r != null) r.setReminderCheck(reminder);
				reservationList.add(r);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

}
