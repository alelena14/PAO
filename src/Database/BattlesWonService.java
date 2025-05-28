package Database;

import java.sql.*;
import java.util.*;

public class BattlesWonService {
    private static BattlesWonService instance;
    private final Connection connection;

    private BattlesWonService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized BattlesWonService getInstance() throws SQLException {
        if (instance == null) {
            instance = new BattlesWonService();
        }
        return instance;
    }

    // ---------------- CREATE ----------------
    public void addBattleWinEntry(int characterId, String difficulty, int wins) {
        String sql = "INSERT INTO BattlesWon (character_id, difficulty, wins) VALUES (?, ?, ?) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            stmt.setString(2, difficulty);
            stmt.setInt(3, wins);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("addBattleWinEntry");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- READ ----------------
    public Map<String, Integer> getWinsForCharacter(int characterId) {
        String sql = "SELECT difficulty, wins FROM BattlesWon WHERE character_id = ?";
        Map<String, Integer> results = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.put(rs.getString("difficulty"), rs.getInt("wins"));
            }
            AuditService.getInstance().logAction("getWinsForCharacter");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    // ---------------- READ DIFFICULTY WINS--------------
    public int getWinsForDifficulty(int characterId, String difficulty) {
        String sql = "SELECT wins FROM BattlesWon WHERE character_id = ? AND difficulty = ?";
        int wins = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            stmt.setString(2, difficulty);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                wins = rs.getInt("wins");
            }

            AuditService.getInstance().logAction("getWinsForDifficulty");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wins;
    }


    // ---------------- UPDATE ----------------
    public void updateWins(int characterId, String difficulty, int newWin) {
        String sql = "UPDATE BattlesWon SET wins = wins + ? WHERE character_id = ? AND difficulty = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, 1);
            stmt.setInt(2, characterId);
            stmt.setString(3, difficulty);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("updateWins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- DELETE ONE ENTRY ----------------
    public void deleteBattleWinEntry(int characterId, String difficulty) {
        String sql = "DELETE FROM BattlesWon WHERE character_id = ? AND difficulty = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            stmt.setString(2, difficulty);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("deleteBattleWinEntry");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
