import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class PrintAllMinionsNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        PreparedStatement stGetMinionNames = connection.prepareStatement("SELECT `name` FROM minions;");
        ResultSet rsMinionNames = stGetMinionNames.executeQuery();

        ArrayList<String> minionNames = new ArrayList<>();
        while (rsMinionNames.next()) {
            minionNames.add(rsMinionNames.getString("name"));
        }

        rsMinionNames.close();
        stGetMinionNames.closeOnCompletion();

        int size = minionNames.size();
        for(int i = 0; i<=size/2; i++) {
            System.out.println(minionNames.get(i));
            if(i != size - 1 - i)
                System.out.println(minionNames.get(size - 1 - i));
        }
    }
}
