import java.util.*;

class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    Map<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    void reduceAvailability(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        int count = inventory.get(type);
        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for: " + type);
        }
        inventory.put(type, count - 1);
    }
}

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingService {
    RoomInventory inventory;
    int counter = 1;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    void book(Reservation r) {
        try {
            validate(r);
            inventory.reduceAvailability(r.roomType);
            String roomId = r.roomType.substring(0, 1).toUpperCase() + counter++;
            System.out.println("Booking Confirmed for " + r.guestName + " | Room ID: " + roomId);
        } catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    void validate(Reservation r) throws InvalidBookingException {
        if (r.guestName == null || r.guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }
        if (r.roomType == null || r.roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }
        if (inventory.getAvailability(r.roomType) == -1) {
            throw new InvalidBookingException("Room type does not exist");
        }
    }
}

public class uc9 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        service.book(new Reservation("Sai", "Single Room"));
        service.book(new Reservation("", "Double Room"));
        service.book(new Reservation("Arun", "Luxury Room"));
        service.book(new Reservation("Priya", "Suite Room"));
        service.book(new Reservation("Kiran", "Suite Room"));
    }
}