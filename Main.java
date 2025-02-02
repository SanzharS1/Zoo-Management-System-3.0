import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Habitat db = new Habitat();
        Connection conn = db.connect_to_db("postgres", "postgres", "Rakhmet2007G");

        Scanner scanner = new Scanner(System.in);
        boolean condition = true;

        while (condition) {
            System.out.println("Welcome to the Habitat Management System:");
            System.out.println("Select an option:");
            System.out.println("1. Insert habitat");
            System.out.println("2. Update habitat name and type");
            System.out.println("3. Read habitat by ID");
            System.out.println("4. Read all habitats");
            System.out.println("5. Delete habitat by ID");
            System.out.println("0. Exit");
            System.out.print("Enter option (0-5): ");
            int option = scanner.nextInt();
            scanner.nextLine();

            try {
                if (option == 1) {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter type: ");
                    String type = scanner.nextLine();
                    db.insert_habitat(conn, "Habitat", name, type);
                } else if (option == 2) {
                    System.out.print("Enter habitat ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new type: ");
                    String newType = scanner.nextLine();
                    db.update_habitat(conn, "Habitat", id, newName, newType);
                } else if (option == 3) {
                    System.out.print("Enter habitat ID: ");
                    int id = scanner.nextInt();
                    db.read_habitat_row_by_id(conn, "Habitat", id);
                } else if (option == 4) {
                    db.read_habitats(conn, "Habitat");
                } else if (option == 5) {
                    System.out.print("Enter habitat ID to delete: ");
                    int id = scanner.nextInt();
                    db.delete_habitat_row(conn, "Habitat", id);
                } else if (option == 0) {
                    condition = false;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
