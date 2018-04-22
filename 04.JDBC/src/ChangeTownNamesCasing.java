import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        String country = sc.nextLine();

        PreparedStatement stUpdateTownNames = connection.prepareStatement(
                    "UPDATE towns\n" +
                         "SET `name` = UPPER(`name`)\n" +
                         "WHERE country = ?;");
        stUpdateTownNames.setString(1, country);

        int rows = stUpdateTownNames.executeUpdate();
        System.out.printf("%s town names were affected.\n", rows <= 0 ? "No" : rows);
        stUpdateTownNames.closeOnCompletion();

        if(rows > 0) {
            PreparedStatement stGetNames = connection.prepareStatement(
                    "SELECT `name`\n" +
                         "FROM towns\n" +
                         "WHERE country = ?;"
            );
            stGetNames.setString(1, country);

            ResultSet rs = stGetNames.executeQuery();
            System.out.print("[");
            while(rs.next()) {
                System.out.print(rs.getString("name"));
                if(!rs.isLast())
                    System.out.print(", ");
                else
                    System.out.println("]");
            }
            rs.close();
            stGetNames.closeOnCompletion();
        }
    }
}
