import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        int villainId = Integer.parseInt(sc.nextLine());

        PreparedStatement stDeleteVillain = connection.prepareStatement(
                "DELETE FROM villains\n" +
                     "WHERE id = ?;"
        );
        stDeleteVillain.setInt(1, villainId);

        PreparedStatement stGetVillainName = connection.prepareStatement(
                "SELECT `name` FROM villains WHERE id = ?;"
        );
        stGetVillainName.setInt(1, villainId);

        ResultSet rsVillainName = stGetVillainName.executeQuery();
        String villainName = "";
        if(rsVillainName.next()) {
            villainName = rsVillainName.getString("name");

            PreparedStatement stReleaseMinions = connection.prepareStatement(
                    "DELETE FROM minions_villains\n" +
                            "WHERE villain_id = ?;"
            );
            stReleaseMinions.setInt(1, villainId);
            int rows = stReleaseMinions.executeUpdate();

            stDeleteVillain.executeUpdate();
            System.out.println(villainName + " was deleted");

            System.out.printf("%d minions released", rows);
            stReleaseMinions.closeOnCompletion();
        }
        else {
            System.out.println("No such villain was found");
        }
        rsVillainName.close();
        stDeleteVillain.closeOnCompletion();
        stGetVillainName.closeOnCompletion();
    }
}
