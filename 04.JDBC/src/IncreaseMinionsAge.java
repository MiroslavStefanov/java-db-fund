import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        List<Long> ids = Arrays.stream(sc.nextLine().split("\\s+")).map(Long::parseLong).collect(Collectors.toList());

        crateToTitleSqlFunction(connection);

        String sqlArgs = addSqlArguments(ids.size(), ", ");
        PreparedStatement stUpdate = connection.prepareStatement(
                "UPDATE minions\n" +
                     "SET `name` = sfn_toTitleCase(`name`), age = age+1\n" +
                     "WHERE id in (" + sqlArgs + ");"
        );
        for(int i = 0; i<ids.size(); i++)
            stUpdate.setLong(i+1, ids.get(i));
        stUpdate.executeUpdate();
        stUpdate.closeOnCompletion();

        PreparedStatement stGetMinions = connection.prepareStatement("SELECT `name`, age FROM minions");
        ResultSet rsMinions = stGetMinions.executeQuery();
        while(rsMinions.next()) {
            System.out.printf("%s %d\n", rsMinions.getString("name"), rsMinions.getInt("age"));
        }
        rsMinions.close();
        stGetMinions.closeOnCompletion();
    }

    static boolean crateToTitleSqlFunction(Connection connection) throws SQLException {
        PreparedStatement st1 = connection.prepareStatement("DROP FUNCTION IF EXISTS sfn_toTitleCase;\n");
        st1.execute();
        PreparedStatement st = connection.prepareStatement(
                        "CREATE FUNCTION `sfn_toTitleCase`(input VARCHAR(255)) RETURNS varchar(255) CHARSET utf8\n" +
                        "BEGIN   \n" +
                        "\tDECLARE i INT DEFAULT 1;\n" +
                        "    DECLARE c CHAR(1);\n" +
                        "    DECLARE bTitleCase BOOL DEFAULT TRUE;\n" +
                        "    DECLARE bLowerCase BOOL DEFAULT FALSE;\n" +
                        "    DECLARE sRet VARCHAR(255) DEFAULT '';\n" +
                        "    WHILE i <= char_length(input) DO\n" +
                        "\t\tSET c = SUBSTRING(input, i, 1);\n" +
                        "        IF c = ' ' THEN\n" +
                        "\t\t\tSET bTitleCase = TRUE;\n" +
                        "            SET bLowerCase = FALSE;\n" +
                        "            SET sRet = CONCAT(sRet, ' ');\n" +
                        "\t\tELSEIF bLowerCase THEN\n" +
                        "\t\t\tSET c = LOWER(c);\n" +
                        "\t\tELSEIF bTitleCase THEN\n" +
                        "\t\t\tSET c = UPPER(c);\n" +
                        "            SET bTitleCase = FALSE;\n" +
                        "            SET bLowerCase = TRUE;\n" +
                        "\t\tEND IF;\n" +
                        "        SET sRet = CONCAT(sRet, c);\n" +
                        "        SET i = i+1;\n" +
                        "    END WHILE;\n" +
                        "    RETURN sRet;\n" +
                        "END;\n"
        );
        return st.execute();
    }

    static String addSqlArguments(int count, String separator) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<count; i++) {
            sb.append("?");
            if(i < count-1)
                sb.append(separator);
        }

        return sb.toString();
    }
}
