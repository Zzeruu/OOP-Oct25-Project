
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class BookingID {

    private final String prefix = "LIB";
    private final int year = java.time.Year.now().getValue();
    private final int bookID;
    private final String id;

    public BookingID(String reservationsFile) {
        reservationsFile = "reservations.txt";
        int count = 0;
        Path p = Paths.get(reservationsFile);
        try {
                List<String> lines = Files.readAllLines(p, StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (line != null && !line.trim().isEmpty()) count++;
            }
        } catch (IOException e) {
            // on error, treat as zero records
            count = 0;
        }

        if (count <= 0) this.bookID = 0;
        else this.bookID = count + 1; // next id

        this.id = prefix + "-" + year + "-" + this.bookID;
    }
    
    public String getReservationId() {
        return id;
    }   

}

