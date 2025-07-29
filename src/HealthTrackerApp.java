import java.io.*;
import java.util.*;

class Patient implements Serializable {
    private String name;
    private int age;
    private String condition;
    private String lastCheckupDate;

    public Patient(String name, int age, String condition, String lastCheckupDate) {
        this.name = name;
        this.age = age;
        this.condition = condition;
        this.lastCheckupDate = lastCheckupDate;
    }

    public String getName() {
        return name;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setLastCheckupDate(String date) {
        this.lastCheckupDate = date;
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Age: %d | Condition: %s | Last Checkup: %s", 
                name, age, condition, lastCheckupDate);
    }
}

public class HealthTrackerApp {
    private static final String DATA_FILE = "patients.dat";
    private static List<Patient> patients = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadPatients();
        while (true) {
            System.out.println("\n\u001B[36m--- Health Tracker App ---\u001B[0m");
            System.out.println("1. View all patients");
            System.out.println("2. Add a new patient");
            System.out.println("3. Update a patient's record");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> viewPatients();
                case "2" -> addPatient();
                case "3" -> updatePatient();
                case "4" -> {
                    savePatients();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("\u001B[31mInvalid choice.\u001B[0m");
            }
        }
    }

    private static void viewPatients() {
        if (patients.isEmpty()) {
            System.out.println("\u001B[33mNo patients found.\u001B[0m");
            return;
        }
        System.out.println("\u001B[35m--- Patient List ---\u001B[0m");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i));
        }
    }

    private static void addPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter health condition: ");
        String condition = scanner.nextLine();
        System.out.print("Enter last checkup date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        patients.add(new Patient(name, age, condition, date));
        System.out.println("\u001B[32mPatient added successfully!\u001B[0m");
    }

    private static void updatePatient() {
        System.out.print("Enter patient name to update: ");
        String name = scanner.nextLine();
        for (Patient p : patients) {
            if (p.getName().equalsIgnoreCase(name)) {
                System.out.print("Enter new condition: ");
                String condition = scanner.nextLine();
                System.out.print("Enter new last checkup date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                p.setCondition(condition);
                p.setLastCheckupDate(date);
                System.out.println("\u001B[32mPatient record updated!\u001B[0m");
                return;
            }
        }
        System.out.println("\u001B[31mPatient not found.\u001B[0m");
    }

    @SuppressWarnings("unchecked")
    private static void loadPatients() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            patients = (List<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            patients = new ArrayList<>();
        }
    }

    private static void savePatients() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(patients);
        } catch (IOException e) {
            System.out.println("\u001B[31mError saving patient data.\u001B[0m");
        }
    }
}
