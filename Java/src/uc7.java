import java.util.*;

class Service {
    String name;
    double price;

    Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {
    Map<String, List<Service>> serviceMap;

    AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    void addService(String reservationId, Service service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    double calculateTotal(String reservationId) {
        double total = 0;
        List<Service> services = serviceMap.get(reservationId);
        if (services != null) {
            for (Service s : services) {
                total += s.price;
            }
        }
        return total;
    }

    void displayServices(String reservationId) {
        System.out.println("Services for Reservation " + reservationId + ":");
        List<Service> services = serviceMap.get(reservationId);
        if (services != null) {
            for (Service s : services) {
                System.out.println(s.name + " - " + s.price);
            }
        } else {
            System.out.println("No services selected");
        }
    }
}

public class uc7 {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId1 = "R1";
        String reservationId2 = "R2";

        manager.addService(reservationId1, new Service("Breakfast", 200));
        manager.addService(reservationId1, new Service("Spa", 500));

        manager.addService(reservationId2, new Service("Airport Pickup", 300));

        manager.displayServices(reservationId1);
        System.out.println("Total Cost: " + manager.calculateTotal(reservationId1));

        System.out.println();

        manager.displayServices(reservationId2);
        System.out.println("Total Cost: " + manager.calculateTotal(reservationId2));
    }
}