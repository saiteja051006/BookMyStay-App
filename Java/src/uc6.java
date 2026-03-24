import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingQueue {
    Queue<Reservation> queue;

    BookingQueue() {
        queue = new LinkedList<>();
    }

    void addRequest(Reservation r) {
        queue.add(r);
    }

    Reservation getNextRequest() {
        return queue.poll();
    }

    boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingService {
    RoomInventory inventory;
    HashMap<String, Set<String>> allocatedRooms;
    Set<String> allRoomIds;
    int counter = 1;

    BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        allRoomIds = new HashSet<>();
    }

    void processBooking(BookingQueue queue) {
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();

            if (inventory.getAvailability(r.roomType) > 0) {
                String roomId = r.roomType.substring(0, 1).toUpperCase() + counter++;

                if (!allRoomIds.contains(roomId)) {
                    allRoomIds.add(roomId);

                    allocatedRooms.putIfAbsent(r.roomType, new HashSet<>());
                    allocatedRooms.get(r.roomType).add(roomId);

                    inventory.reduceAvailability(r.roomType);

                    System.out.println("Booking Confirmed for " + r.guestName + " | Room ID: " + roomId);
                }
            } else {
                System.out.println("Booking Failed for " + r.guestName + " | No rooms available");
            }
        }
    }

    void displayAllocations() {
        System.out.println("----- Allocated Rooms -----");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + ": " + allocatedRooms.get(type));
        }
    }
}

public class uc6 {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Sai", "Single Room"));
        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Priya", "Single Room"));
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        BookingService service = new BookingService(inventory);

        service.processBooking(queue);

        System.out.println();
        service.displayAllocations();
    }
}