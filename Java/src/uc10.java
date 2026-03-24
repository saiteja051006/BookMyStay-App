import java.util.*;

class Reservation {
    String reservationId;
    String guestName;
    String roomType;
    String roomId;

    Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class RoomInventory {
    Map<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    void increase(String type) {
        inventory.put(type, inventory.getOrDefault(type, 0) + 1);
    }

    void decrease(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingHistory {
    Map<String, Reservation> history;

    BookingHistory() {
        history = new HashMap<>();
    }

    void add(Reservation r) {
        history.put(r.reservationId, r);
    }

    Reservation get(String id) {
        return history.get(id);
    }

    void remove(String id) {
        history.remove(id);
    }
}

class CancellationService {
    RoomInventory inventory;
    BookingHistory history;
    Stack<String> rollbackStack;

    CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    void cancel(String reservationId) {
        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation Failed: Reservation not found");
            return;
        }

        rollbackStack.push(r.roomId);

        inventory.increase(r.roomType);

        history.remove(reservationId);

        System.out.println("Cancelled Reservation: " + reservationId + " | Room Released: " + r.roomId);
    }

    void showRollback() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class uc10 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R1", "Sai", "Single Room", "S1");
        Reservation r2 = new Reservation("R2", "Arun", "Double Room", "D1");

        history.add(r1);
        history.add(r2);

        CancellationService service = new CancellationService(inventory, history);

        service.cancel("R1");
        service.cancel("R3");

        service.showRollback();
    }
}