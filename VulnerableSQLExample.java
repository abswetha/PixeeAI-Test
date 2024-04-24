import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class VulnerableSQLExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "user", "pass");
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Login failed!");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
