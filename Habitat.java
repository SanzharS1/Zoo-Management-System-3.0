import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class Habitat {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
        return conn;
    }

    public void createTable(Connection conn, String table_name) {
        Statement statement;
        try {
            String query = "CREATE TABLE " + table_name + " (id SERIAL PRIMARY KEY, name VARCHAR(200), type VARCHAR(200));";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table Created");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void insert_habitat(Connection conn, String table_name, String name, String type) {
        Statement statement;
        ResultSet rs;
        try {
            String findMinIdQuery = String.format(
                    "SELECT COALESCE(MIN(id) + 1, 1) AS new_id FROM %s WHERE id + 1 NOT IN (SELECT id FROM %s);",
                    table_name, table_name
            );
            statement = conn.createStatement();
            rs = statement.executeQuery(findMinIdQuery);

            int newId = 1;
            if (rs.next()) {
                newId = rs.getInt("new_id");
            }

            String insertQuery = String.format(
                    "INSERT INTO %s (id, name, type) VALUES (%d, '%s', '%s');",
                    table_name, newId, name, type
            );
            statement.executeUpdate(insertQuery);
            System.out.println("Habitat Inserted with ID: " + newId);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }


    public void read_habitats(Connection conn, String table_name) {
        Statement statement;
        ResultSet rs;
        try {
            String query = String.format("SELECT * FROM %s ORDER BY id ASC;", table_name);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            System.out.println(" ");
            System.out.println("Habitats:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void read_habitat_row_by_id(Connection conn, String table_name, int id) {
        Statement statement;
        ResultSet rs;
        try {
            String query = String.format("SELECT * FROM %s WHERE id = %d;", table_name, id);
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Type: " + rs.getString("type"));
                System.out.println("--------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void update_habitat(Connection conn, String table_name, int id, String newName, String newType) {
        Statement statement;
        try {
            String query = String.format("UPDATE %s SET name = '%s', type = '%s' WHERE id = %d;", table_name, newName, newType, id);
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Habitat Updated");
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void delete_habitat_row(Connection conn, String table_name, int id) {
        Statement statement;
        try {
            String deleteQuery = String.format("DELETE FROM %s WHERE id = %d;", table_name, id);
            statement = conn.createStatement();
            statement.executeUpdate(deleteQuery);
            System.out.println("Habitat Deleted with ID: " + id);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}