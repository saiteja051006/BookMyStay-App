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
    Queue<Reservation> queue = new LinkedList<>();

    synchronized void add(Reservation r) {
        queue.add(r);
    }

    synchronized Reservation get() {
        return queue.poll();
    }

    synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    Map<String, Integer> inventory = new HashMap<>();

    RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    synchronized boolean allocate(String type) {
        int count = inventory.getOrDefault(type, 0);
        if (count > 0) {
            inventory.put(type, count - 1);
            return true;
        }
        return false;
    }
}

class BookingProcessor extends Thread {
    BookingQueue queue;
    RoomInventory inventory;

    BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;

            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.get();
            }

            if (r != null) {
                boolean success;
                synchronized (inventory) {
                    success = inventory.allocate(r.roomType);
                }

                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " Confirmed: " + r.guestName + " -> " + r.roomType);
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " Failed: " + r.guestName + " -> " + r.roomType);
                }
            }
        }
    }
}

public class uc11 {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        queue.add(new Reservation("Sai", "Single Room"));
        queue.add(new Reservation("Arun", "Single Room"));
        queue.add(new Reservation("Priya", "Single Room"));
        queue.add(new Reservation("Kiran", "Double Room"));
        queue.add(new Reservation("Rahul", "Double Room"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
    }
}