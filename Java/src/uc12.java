import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class RoomInventory implements Serializable {
    Map<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }
}

class DataStore implements Serializable {
    RoomInventory inventory;
    List<Reservation> history;

    DataStore(RoomInventory inventory, List<Reservation> history) {
        this.inventory = inventory;
        this.history = history;
    }
}

class PersistenceService {

    void save(DataStore data, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(data);
            out.close();
            System.out.println("Data saved successfully");
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    DataStore load(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            DataStore data = (DataStore) in.readObject();
            in.close();
            System.out.println("Data loaded successfully");
            return data;
        } catch (Exception e) {
            System.out.println("No previous data found, starting fresh");
            return new DataStore(new RoomInventory(), new ArrayList<>());
        }
    }
}

public class uc12 {
    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        DataStore data = service.load("data.ser");

        data.history.add(new Reservation("Sai", "Single Room", "S1"));

        data.inventory.inventory.put("Single Room",
                data.inventory.inventory.get("Single Room") - 1);

        service.save(data, "data.ser");

        System.out.println("Current Bookings: " + data.history.size());
        System.out.println("Single Room Available: " +
                data.inventory.inventory.get("Single Room"));
    }
}