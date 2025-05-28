package Database;

import Items.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private static ItemService instance;
    private final Connection connection;

    private ItemService() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized ItemService getInstance() throws SQLException {
        if (instance == null) {
            instance = new ItemService();
        }
        return instance;
    }

    // ---------------- CREATE ----------------
    public void createItem(Item item) {
        String sql = "INSERT INTO Item (name, value, type, damage, weapon_type, level, healing) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, item.getName());
            switch(item) {
                case Weapon w -> {
                    stmt.setInt(2, w.getValue());
                    stmt.setString(3, "weapon");
                    stmt.setInt(4, w.getDamage());
                    stmt.setString(5, w.getType());
                    stmt.setInt(6, w.getLevel());
                    stmt.setNull(7, Types.INTEGER);
                }
                case HealthPotion hp -> {
                    stmt.setInt(2, hp.getValue());
                    stmt.setString(3, "potion");
                    stmt.setNull(4, Types.INTEGER);
                    stmt.setNull(5, Types.VARCHAR);
                    stmt.setNull(6, Types.INTEGER);
                    stmt.setInt(7, hp.getHealAmount());
                }
                default -> {
                    stmt.setInt(2, item.getValue());
                    stmt.setNull(3,Types.VARCHAR);
                    stmt.setNull(4,Types.INTEGER);
                    stmt.setNull(5,Types.VARCHAR);
                    stmt.setNull(6,Types.INTEGER);
                    stmt.setNull(7,Types.INTEGER);
                }
            }

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getInt(1));
            }
            AuditService.getInstance().logAction("createItem");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- READ ----------------
    public Item readItem(int id) {
        String sql = "SELECT * FROM Item WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String type = rs.getString("type");
                if ("weapon".equalsIgnoreCase(type)) {
                    return new Weapon(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("damage"),
                            rs.getString("weapon_type"),
                            rs.getInt("value"),
                            rs.getInt("level")
                    );
                } else if ("potion".equalsIgnoreCase(type)) {
                    return new HealthPotion(
                            rs.getInt("id"),
                            rs.getInt("value"),
                            rs.getInt("healing")
                    );
                }
            }
            AuditService.getInstance().logAction("readItem");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ---------------- UPDATE ----------------
    public void updateItem(Item item) {
        String sql = "UPDATE Item SET name=?, value=?, type=?, damage=?, weapon_type=?, level=?, healing=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (item instanceof Weapon w) {
                stmt.setString(1, w.getName());
                stmt.setInt(2, w.getValue());
                stmt.setString(3, "weapon");
                stmt.setInt(4, w.getDamage());
                stmt.setString(5, w.getType());
                stmt.setInt(6, w.getLevel());
                stmt.setNull(7, Types.INTEGER);
            } else if (item instanceof HealthPotion hp) {
                stmt.setNull(1, Types.VARCHAR);
                stmt.setInt(2, hp.getValue());
                stmt.setString(3, "potion");
                stmt.setNull(4, Types.INTEGER);
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.INTEGER);
                stmt.setInt(7, hp.getHealAmount());
            }

            stmt.setInt(8, item.getId());
            stmt.executeUpdate();
            AuditService.getInstance().logAction("updateItem");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- DELETE ----------------
    public void deleteItem(int id) {
        String sql = "DELETE FROM Item WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("deleteItem");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ---------------- LIST ALL ITEMS ----------------
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM Item";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                if ("weapon".equalsIgnoreCase(type)) {
                    items.add(new Weapon(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("value"),
                            rs.getString("weapon_type"),
                            rs.getInt("damage"),
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
