
import java.util.ArrayList;
import java.util.List;

// Enum for passenger types
enum PassengerType {
    STANDARD, GOLD, PREMIUM
}

// Class representing an activity
class Activity {
    private String name;
    private String description;
    private double cost;
    private int capacity;
    private int booked;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.booked = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBooked() {
        return booked;
    }

    // Book an activity for a passenger
    public boolean bookActivity() {
        if (booked < capacity) {
            booked++;
            return true;
        } else {
            return false;
        }
    }
}

// Class representing a destination
class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

// Class representing a passenger
class Passenger {
    private String name;
    private int passengerNumber;
    private double balance;
    private PassengerType type;
    private List<Activity> bookedActivities;

    public Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.balance = 0; // Initialize balance to 0
        this.bookedActivities = new ArrayList<>();
    }

    public void signUpForActivity(Activity activity) {
        if (activity.bookActivity()) {
            if (type == PassengerType.STANDARD) {
                if (balance >= activity.getCost()) {
                    balance -= activity.getCost();
                    bookedActivities.add(activity);
                } else {
                    System.out.println("Insufficient balance to sign up for activity: " + activity.getName());
                }
            } else if (type == PassengerType.GOLD) {
                double discountedCost = activity.getCost() * 0.9; // 10% discount for gold passengers
                if (balance >= discountedCost) {
                    balance -= discountedCost;
                    bookedActivities.add(activity);
                } else {
                    System.out.println("Insufficient balance to sign up for activity: " + activity.getName());
                }
            } else { // Premium passenger
                bookedActivities.add(activity);
            }
        } else {
            System.out.println("Activity " + activity.getName() + " at capacity. Cannot sign up.");
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getBalance() {
        return balance;
    }

    public List<Activity> getBookedActivities() {
        return bookedActivities;
    }
}

// Class representing a travel package
class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Destination> destinations;
    private List<Passenger> passengers;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.destinations = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public void addPassenger(Passenger passenger) {
        if (passengers.size() < passengerCapacity) {
            passengers.add(passenger);
        } else {
            System.out.println("Travel package is already full. Cannot add more passengers.");
        }
    }

    // Print itinerary of the travel package
    public void printItinerary() {
        System.out.println("Travel Package: " + name);
        for (Destination destination : destinations) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println("Activity: " + activity.getName() + ", Cost: " + activity.getCost() +
                        ", Capacity: " + activity.getCapacity() + ", Description: " + activity.getDescription());
            }
        }
    }

    // Print the passenger list of the travel package
    public void printPassengerList() {
        System.out.println("Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Passenger: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
    }

    // Print the details of an individual passenger
    public void printPassengerDetails(Passenger passenger) {
        System.out.println("Passenger Details:");
        System.out.println("Name: " + passenger.getName());
        System.out.println("Passenger Number: " + passenger.getPassengerNumber());
        if (passenger.getBalance() > 0) {
            System.out.println("Balance: " + passenger.getBalance());
        }
        System.out.println("Activities Booked:");
        for (Activity activity : passenger.getBookedActivities()) {
            System.out.println("Activity: " + activity.getName() + ", Cost: " + activity.getCost());
        }
    }

    // Print the details of all the activities that still have spaces available
    public void printAvailableActivities() {
        System.out.println("Available Activities:");
        for (Destination destination : destinations) {
            for (Activity activity : destination.getActivities()) {
                if (activity.getBooked() < activity.getCapacity()) {
                    System.out.println("Destination: " + destination.getName() + ", Activity: " + activity.getName() +
                            ", Available Spaces: " + (activity.getCapacity() - activity.getBooked()));
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Sample usage
        Activity snorkeling = new Activity("Snorkeling", "Enjoy snorkeling in clear waters", 50.0, 20);
        Destination beachDestination = new Destination("Beach");
        beachDestination.addActivity(snorkeling);

        Passenger goldPassenger = new Passenger("John", 1, PassengerType.GOLD);
        goldPassenger.signUpForActivity(snorkeling); // John signs up for snorkeling

        TravelPackage beachPackage = new TravelPackage("Beach Vacation", 50);
        beachPackage.addDestination(beachDestination);
        beachPackage.addPassenger(goldPassenger);

        // Print itinerary
        beachPackage.printItinerary();

        // Print passenger list
        beachPackage.printPassengerList();

        // Print details of a passenger
        beachPackage.printPassengerDetails(goldPassenger);

        // Print available activities
    }
}
