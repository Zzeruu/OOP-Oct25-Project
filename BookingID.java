import java.util.Objects;
import java.util.regex.Pattern;

//this one is completely AI to understand the structure, will manually re-code later
//this one is completely AI to understand the structure, will manually re-code later
//this one is completely AI to understand the structure, will manually re-code later
//this one is completely AI to understand the structure, will manually re-code later
//this one is completely AI to understand the structure, will manually re-code later

public final class BookingID {
	private static final Pattern FORMAT = Pattern.compile("^[A-Z]{3}-\\d{4}-\\d{3}$");

	private final String id;
	private final String prefix; // e.g. LIB
	private final int year;      // e.g. 2025
	private final int sequence;  // e.g. 1

	public BookingID(String id) throws InvalidReservationException {
		if (id == null) throw new InvalidReservationException("BookingID cannot be null");
		if (!FORMAT.matcher(id).matches()) throw new InvalidReservationException("Invalid BookingID format: " + id);
		this.id = id;

		String[] parts = id.split("-");
		this.prefix = parts[0];
		int parsedYear;
		int parsedSeq;
		try {
			parsedYear = Integer.parseInt(parts[1]);
			parsedSeq = Integer.parseInt(parts[2]);
		} catch (NumberFormatException ex) {
			throw new InvalidReservationException("Invalid numeric parts in BookingID: " + id);
		}

		// Basic range check for year and sequence
		if (parsedYear < 1900 || parsedYear > 9999) {
			throw new InvalidReservationException("Year part out of range in BookingID: " + parsedYear);
		}
		if (parsedSeq < 0) {
			throw new InvalidReservationException("Sequence must be non-negative in BookingID: " + parsedSeq);
		}

		this.year = parsedYear;
		this.sequence = parsedSeq;
	}

	public String getId() { return id; }
	public String getPrefix() { return prefix; }
	public int getYear() { return year; }
	public int getSequence() { return sequence; }

	public static BookingID of(String id) throws InvalidReservationException {
		return new BookingID(id);
	}

	@Override
	public String toString() { return id; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookingID)) return false;
		BookingID other = (BookingID) o;
		return id.equals(other.id);
	}

	@Override
	public int hashCode() { return Objects.hash(id); }
}