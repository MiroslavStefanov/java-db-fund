import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class GetMinionNames {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        int villain_id = Integer.parseInt(sc.nextLine());

        PreparedStatement villainSt = connection.prepareStatement(
                "SELECT `name` FROM villains\n" +
                        "WHERE id = ?");
        villainSt.setInt(1, villain_id);

        ResultSet villainNameResult = villainSt.executeQuery();
        String villainName;
        if(villainNameResult.next()) {
            villainName = villainNameResult.getString("name");
        }
        else {
            villainName = "No villain with ID " + villain_id + " exists in the database.";
            System.out.println(villainName);
            villainNameResult.close();
            villainSt.closeOnCompletion();
            return;
        }
        villainNameResult.close();
        villainSt.closeOnCompletion();

        PreparedStatement st = connection.prepareStatement(
                "SELECT m.`name` AS minion_name, m.age AS age\n" +
                        "FROM minions AS m\n" +
                        "INNER JOIN minions_villains AS m_v ON m.id = m_v.minions_id\n" +
                        "INNER JOIN villains AS v ON v.id = m_v.villain_id\n" +
                        "WHERE m_v.villain_id = ?");
        st.setInt(1, villain_id);

        ResultSet rs = st.executeQuery();

        System.out.printf("Villain: %s\n", villainName);
        int num = 0;
        while(rs.next()) {
            num++;
            System.out.printf("%d. %s %d\n", num, rs.getString("minion_name"), rs.getInt("age"));
        }
        if(num == 0) {
            System.out.println("<no minions>");
        }
        rs.close();
        st.closeOnCompletion();
    }
}
