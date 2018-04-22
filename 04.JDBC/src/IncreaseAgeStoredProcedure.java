import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        long minionId = Long.parseLong(sc.nextLine());

        CallableStatement stCallProc = connection.prepareCall("CALL usp_get_older(?)");
        stCallProc.setLong(1, minionId);
        int rows = stCallProc.executeUpdate();
        PreparedStatement stGetMinion = connection.prepareStatement("SELECT `name`, age FROM minions WHERE id = ?");
        stGetMinion.setLong(1, minionId);
        ResultSet rs = stGetMinion.executeQuery();
        if(rs.next())
            System.out.println(rs.getString("name") + " " + rs.getInt("age"));
        else
            System.out.println("No such minion found.");
        rs.close();
        stGetMinion.closeOnCompletion();
    }
}
