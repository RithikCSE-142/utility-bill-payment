import java.sql.*;
import java.util.Scanner;

public class LandRecordSystem {

    static final String URL = "jdbc:mysql://localhost:3306/landdb";
    static final String USER = "root";
    static final String PASSWORD = "Mysql@2110"; // change if needed

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            while (true) {
                System.out.println("\n===== LAND RECORD SYSTEM =====");
                System.out.println("1. Add Record");
                System.out.println("2. View All Records");
                System.out.println("3. Search by Owner");
                System.out.println("4. Update Record");
                System.out.println("5. Delete Record");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Owner Name: ");
                        String name = sc.nextLine();

                        System.out.print("Location: ");
                        String location = sc.nextLine();

                        System.out.print("Area: ");
                        double area = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Land Type: ");
                        String type = sc.nextLine();

                        String insert = "INSERT INTO land_records(owner_name, location, area, land_type) VALUES (?, ?, ?, ?)";
                        PreparedStatement ps1 = con.prepareStatement(insert);
                        ps1.setString(1, name);
                        ps1.setString(2, location);
                        ps1.setDouble(3, area);
                        ps1.setString(4, type);

                        ps1.executeUpdate();
                        System.out.println("Record Added Successfully!");
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM land_records");

                        System.out.println("\n--- LAND RECORDS ---");
                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("id") + " | " +
                                rs.getString("owner_name") + " | " +
                                rs.getString("location") + " | " +
                                rs.getDouble("area") + " | " +
                                rs.getString("land_type")
                            );
                        }
                        break;

                    case 3:
                        System.out.print("Enter Owner Name: ");
                        String searchName = sc.nextLine();

                        String search = "SELECT * FROM land_records WHERE owner_name=?";
                        PreparedStatement ps2 = con.prepareStatement(search);
                        ps2.setString(1, searchName);

                        ResultSet rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            System.out.println(
                                rs2.getInt("id") + " | " +
                                rs2.getString("owner_name") + " | " +
                                rs2.getString("location") + " | " +
                                rs2.getDouble("area") + " | " +
                                rs2.getString("land_type")
                            );
                        }
                        break;

                    case 4:
                        System.out.print("Enter Record ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Location: ");
                        String newLocation = sc.nextLine();

                        String update = "UPDATE land_records SET location=? WHERE id=?";
                        PreparedStatement ps3 = con.prepareStatement(update);
                        ps3.setString(1, newLocation);
                        ps3.setInt(2, updateId);

                        ps3.executeUpdate();
                        System.out.println("Record Updated!");
                        break;

                    case 5:
                        System.out.print("Enter Record ID to Delete: ");
                        int deleteId = sc.nextInt();

                        String delete = "DELETE FROM land_records WHERE id=?";
                        PreparedStatement ps4 = con.prepareStatement(delete);
                        ps4.setInt(1, deleteId);

                        ps4.executeUpdate();
                        System.out.println("Record Deleted!");
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        con.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}