package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/miaudb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            createTables();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Item (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100),
                        value INT,
                        type VARCHAR(50),         -- "weapon" sau "potion"
                        damage INT,               -- NULL daca e potion
                        level INT,               -- NULL daca e potion
                        healing INT              -- NULL daca e weapon
                    );
                    """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS `Character` (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(100),
                    level INT,
                    exp INT,
                    gold INT,
                    health INT,
                    attack INT,
                    defense INT,
                    isBurned BOOLEAN,
                    weapon_id INT,
                    FOREIGN KEY (weapon_id) REFERENCES Item(id),
                    type VARCHAR(100),
                    mana INT,
                    energy INT,
                    speed INT
                );
            """);

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS BattlesWon (
                        character_id INT,
                        difficulty ENUM('SUPER_EASY', 'EASY', 'NORMAL', 'HARD', 'VERY_HARD', 'NIGHTMARE'),
                        wins INT,
                        PRIMARY KEY (character_id, difficulty),
                        FOREIGN KEY (character_id) REFERENCES `Character`(id) ON DELETE CASCADE
                    );
                    """);

            stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Inventory (
                        character_id INT,
                        item_id INT,
                        PRIMARY KEY (character_id, item_id),
                        FOREIGN KEY (character_id) REFERENCES `Character`(id) ON DELETE CASCADE,
                        FOREIGN KEY (item_id) REFERENCES Item(id) ON DELETE CASCADE
                    );
                    """);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException{
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
