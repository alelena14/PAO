package Database;

import Items.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private static InventoryService instance;
    private final Connection connection;

    private InventoryService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized InventoryService getInstance() throws SQLException {
        if (instance == null) {
            instance = new InventoryService();
        }
        return instance;
    }

    // ---------------- CREATE ----------------
    public void addInventoryEntry(int characterId, int itemId) {
        String sql = "INSERT INTO Inventory (character_id, item_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("addInventoryEntry");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- READ ----------------
    public List<Item> getInventoryForCharacter(int characterId) {
        List<Item> items = new ArrayList<>();
        String sql = """
            SELECT i.*
            FROM Inventory inv
            JOIN Item i ON inv.item_id = i.id
            WHERE inv.character_id = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");

                if ("weapon".equalsIgnoreCase(type)) {
                    items.add(new Weapon(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("damage"),
                            rs.getString("weapon_type"),
                            rs.getInt("value"),
                            rs.getInt("level")
                    ));
                } else if ("potion".equalsIgnoreCase(type)) {
                    items.add(new HealthPotion(
                            rs.getInt("id"),
                            rs.getInt("value"),
                            rs.getInt("healing")
                    ));
                }
            }

            AuditService.getInstance().logAction("getInventoryForCharacter");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ---------------- UPDATE ----------------
    // cand am o arma si dau equip la alta
    public void updateInventoryEntry(int characterId, int oldItemId, int newItemId) {
        String sql = "UPDATE Inventory SET item_id = ? WHERE character_id = ? AND item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newItemId);
            stmt.setInt(2, characterId);
            stmt.setInt(3, oldItemId);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("updateInventoryEntry");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- DELETE ----------------
    public void deleteInventoryEntry(int characterId, int itemId) {
        String sql = "DELETE FROM Inventory WHERE character_id = ? AND item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, characterId);
            stmt.setInt(2, itemId);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("deleteInventoryEntry");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- LIST ALL ----------------
    /*public List<String> getAllInventoryEntries() {
        List<String> entries = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int characterId = rs.getInt("character_id");
                int itemId = rs.getInt("item_id");
                entries.add("Character " + characterId + " owns Item " + itemId);
            }
            AuditService.getInstance().logAction("getAllInventoryEntries");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entries;
    }*/
}
