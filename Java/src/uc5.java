import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType);
    }
}

class BookingQueue {
    Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    void displayQueue() {
        System.out.println("----- Booking Requests Queue -----");
        for (Reservation r : queue) {
            r.display();
        }
    }
}

public class uc5 {
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Sai", "Single Room"));
        bookingQueue.addRequest(new Reservation("Arun", "Double Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Suite Room"));

        bookingQueue.displayQueue();
    }
}