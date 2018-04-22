import java.sql.*;
import java.util.Properties;

public class GetVillainsNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);
        PreparedStatement st = connection.prepareStatement("SELECT v.`name` AS villain_name, COUNT(m_v.minions_id) AS minion_count\n" +
                                                                "FROM villains AS v\n" +
                                                                "INNER JOIN minions_villains AS m_v ON v.id = m_v.villain_id\n" +
                                                                "HAVING minion_count > 3\n" +
                                                                "ORDER BY minion_count DESC;");

        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            System.out.printf("%s %d", rs.getString("villain_name"), rs.getInt("minion_count"));
        }

        rs.close();
        st.closeOnCompletion();
    }
}
