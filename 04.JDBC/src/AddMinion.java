import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddMinion {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pandicacao");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3309/minionsdb", props);

        Scanner sc = new Scanner(System.in);
        String[] minionArgs = sc.nextLine().split("\\s+");
        String[] villainArgs = sc.nextLine().split("\\s+");

        String[] id = {"id"};

        //handle the town
        PreparedStatement stGetTownId = connection.prepareStatement("SELECT id FROM towns WHERE `name` = ?;");
        stGetTownId.setString(1, minionArgs[3]);


        int townId = 0;
        ResultSet rsTownId = stGetTownId.executeQuery();
        if(rsTownId.next()) {
            townId = rsTownId.getInt(1);
        }
        else {
            PreparedStatement stAddTown = connection.prepareStatement("INSERT INTO towns(`name`) VALUES(?);", id);
            stAddTown.setString(1, minionArgs[3]);
            connection.setAutoCommit(false);
            int rows = stAddTown.executeUpdate();
            if(rows <= 0) {
                connection.rollback();
                System.out.printf("Adding town %s failed!", minionArgs[3]);
                rsTownId.close();
                stAddTown.closeOnCompletion();
                return;
            }
            else {
                ResultSet rsNewTownId = stAddTown.getGeneratedKeys();
                if(rsNewTownId.next()) {
                    townId = rsNewTownId.getInt(1);
                }
                System.out.println("Town " + minionArgs[3] + " was added to the database.");
            }
            stAddTown.closeOnCompletion();
        }
        rsTownId.close();

        //handle the villain
        PreparedStatement stGetVillainId = connection.prepareStatement("SELECT id FROM villains WHERE `name` = ?;");
        stGetVillainId.setString(1, villainArgs[1]);

        int villainId = 0;
        ResultSet rsVillainId = stGetVillainId.executeQuery();
        if(rsVillainId.next()) {
            villainId = rsVillainId.getInt("id");
        }
        else {
            PreparedStatement stAddVillain = connection.prepareStatement("INSERT INTO villains(`name`, evilness_factor) VALUES(?, 3);", id);
            stAddVillain.setString(1, villainArgs[1]);
            connection.setAutoCommit(false);
            int rows = stAddVillain.executeUpdate();
            if(rows <= 0) {
                connection.rollback();
                System.out.printf("Adding villain %s failed!", villainArgs[1]);
                rsVillainId.close();
                stAddVillain.closeOnCompletion();
                return;
            }
            else {
                ResultSet rsNewVillainId = stAddVillain.getGeneratedKeys();
                if(rsNewVillainId.next()) {
                    villainId = rsNewVillainId.getInt(1);
                }
                System.out.println("Villain " + villainArgs[1] + " was added to the database.");
            }
            stAddVillain.closeOnCompletion();
        }
        rsVillainId.close();

        //handle the minion
        PreparedStatement stAddMinion = connection.prepareStatement("INSERT INTO minions(`name`, age, town_id) VALUES(?, ?, ?);", id);
        stAddMinion.setString(1, minionArgs[1]);
        stAddMinion.setInt(2, Integer.parseInt(minionArgs[2]));
        stAddMinion.setInt(3, townId);
        connection.setAutoCommit(false);
        int rows = stAddMinion.executeUpdate();
        if(rows <= 0) {
            connection.rollback();
            System.out.printf("Adding minion %s at age of %s in town %d failed!", minionArgs[1], minionArgs[2], minionArgs[3]);
            stAddMinion.closeOnCompletion();
            return;
        }
        else {
            PreparedStatement stMakeServant = connection.prepareStatement("INSERT INTO minions_villains VALUES(?, ?);");
            ResultSet rsId = stAddMinion.getGeneratedKeys();
            if(rsId.next()){
                stMakeServant.setLong(1, rsId.getLong(1));
                stMakeServant.setInt(2, villainId);
                int rows1 = stMakeServant.executeUpdate();
                if(rows1 <= 0) {
                    connection.rollback();
                    System.out.printf("Adding minion %s at age of %s in town %d failed!", minionArgs[1], minionArgs[2], minionArgs[3]);
                    stAddMinion.closeOnCompletion();
                    rsId.close();
                    stMakeServant.closeOnCompletion();
                    return;
                }
                else {
                    System.out.println("Successfully added " + minionArgs[1] + " to be minion of " + villainArgs[1]);
                }
            }
            stAddMinion.closeOnCompletion();
            rsId.close();
            stMakeServant.closeOnCompletion();
        }
        connection.commit();
        connection.setAutoCommit(true);
    }
}
