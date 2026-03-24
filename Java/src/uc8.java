import java.util.*;

class Reservation {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    void display() {
        System.out.println("Guest: " + guestName + ", Room: " + roomType + ", Room ID: " + roomId);
    }
}

class BookingHistory {
    List<Reservation> history;

    BookingHistory() {
        history = new ArrayList<>();
    }

    void addReservation(Reservation r) {
        history.add(r);
    }

    List<Reservation> getHistory() {
        return history;
    }
}

class BookingReportService {
    void generateReport(List<Reservation> history) {
        System.out.println("----- Booking History Report -----");
        for (Reservation r : history) {
            r.display();
        }
    }

    void summary(List<Reservation> history) {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            countMap.put(r.roomType, countMap.getOrDefault(r.roomType, 0) + 1);
        }

        System.out.println("----- Summary -----");
        for (String type : countMap.keySet()) {
            System.out.println(type + " Booked: " + countMap.get(type));
        }
    }
}

public class uc8 {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation("Sai", "Single Room", "S1"));
        history.addReservation(new Reservation("Arun", "Double Room", "D1"));
        history.addReservation(new Reservation("Priya", "Single Room", "S2"));

        BookingReportService reportService = new BookingReportService();

        reportService.generateReport(history.getHistory());
        System.out.println();
        reportService.summary(history.getHistory());
    }
}