package Database;
import Characters.Archer;
import Characters.Character;
import Characters.Mage;
import Characters.Warrior;
import Items.Weapon;
import service.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterService {
    private static CharacterService instance;
    private final Connection connection;

    public CharacterService() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static synchronized CharacterService getInstance() throws SQLException {
        if (instance == null) {
            instance = new CharacterService();
        }
        return instance;
    }

    public void createCharacter(Characters.Character character) throws SQLException {
        String sql = "INSERT INTO `Character` (name, level, exp, gold, health, attack, defense, isBurned, weapon_id, type, mana, energy, speed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, character.getName());
            stmt.setInt(2, character.getLevel());
            stmt.setInt(3, character.getExp());
            stmt.setInt(4, character.getGold());
            stmt.setInt(5, character.getHealth());
            stmt.setInt(6, character.getAttack());
            stmt.setInt(7, character.getDefense());
            stmt.setInt(8, character.getIsBurned());
            stmt.setNull(9, Types.INTEGER);
            switch(character){
                case Mage _ -> {
                    stmt.setString(10, "mage");
                    stmt.setInt(11, 30);
                    stmt.setNull(12, Types.INTEGER);
                    stmt.setNull(13, Types.INTEGER);
                }
                case Archer _  -> {
                    stmt.setString(10, "archer");
                    stmt.setInt(12, 30);
                    stmt.setNull(11, Types.INTEGER);
                    stmt.setNull(13, Types.INTEGER);
                }
                case Warrior _ -> {
                    stmt.setString(10, "warrior");
                    stmt.setInt(13, 30);
                    stmt.setNull(12, Types.INTEGER);
                    stmt.setNull(11, Types.INTEGER);
                }
                default -> throw new IllegalStateException("Unexpected value: " + character);
            }
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                character.setId(generatedKeys.getInt(1));
            }
            AuditService.getInstance().logAction("createCharacter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Characters.Character readCharacter(int id) throws SQLException {
        String sql = "SELECT * FROM `Character` WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int weaponId = rs.getInt("weapon_id");
                Weapon weapon = null;

                if (weaponId > 0) {
                    weapon = (Weapon) ItemService.getInstance().readItem(weaponId);
                }

                String type = rs.getString("type");

                Characters.Character character;

                switch (type.toLowerCase()) {
                    case "mage" -> character = new Mage(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("health"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            weapon,
                            rs.getInt("level"),
                            rs.getInt("exp"),
                            rs.getInt("gold"),
                            rs.getInt("isBurned"),
                            rs.getInt("mana")
                    );
                    case "archer" -> character = new Archer(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("health"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            weapon,
                            rs.getInt("level"),
                            rs.getInt("exp"),
                            rs.getInt("gold"),
                            rs.getInt("isBurned"),
                            rs.getInt("energy")
                    );
                    case "warrior" -> character = new Warrior(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("health"),
                            rs.getInt("attack"),
                            rs.getInt("defense"),
                            weapon,
                            rs.getInt("level"),
                            rs.getInt("exp"),
                            rs.getInt("gold"),
                            rs.getInt("isBurned"),
                            rs.getInt("speed")
                    );
                    default -> throw new IllegalStateException("Unknown character type: " + type);
                }

                AuditService.getInstance().logAction("readCharacter");
                return character;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCharacter(Characters.Character character) throws SQLException {
        String sql = "UPDATE `Character` SET name = ?, level = ?, exp = ?, gold = ?, health = ?, attack = ?, defense = ?, isBurned = ?, weapon_id = ?, mana = ?, energy = ?, speed = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, character.getName());
            stmt.setInt(2, character.getLevel());
            stmt.setInt(3, character.getExp());
            stmt.setInt(4, character.getGold());
            stmt.setInt(5, character.getHealth());
            stmt.setInt(6, character.getAttack());
            stmt.setInt(7, character.getDefense());
            stmt.setInt(8, character.getIsBurned());
            if (character.getWeapon() != null) {
                stmt.setInt(9, character.getWeapon().getId());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }
            stmt.setInt(13, character.getId());

            switch(character){
                case Mage _ -> {
                    stmt.setInt(10, ((Mage) character).getMana());
                    stmt.setNull(11, Types.INTEGER);
                    stmt.setNull(12, Types.INTEGER);
                }
                case Archer _ -> {
                    stmt.setInt(11, ((Archer) character).getEnergy());
                    stmt.setNull(10, Types.INTEGER);
                    stmt.setNull(12, Types.INTEGER);
                }
                case Warrior _ -> {
                    stmt.setInt(12, ((Warrior) character).getSpeed());
                    stmt.setNull(11, Types.INTEGER);
                    stmt.setNull(10, Types.INTEGER);
                }
                default -> throw new IllegalStateException("Unknown character type");
            }

            stmt.executeUpdate();
            AuditService.getInstance().logAction("updateCharacter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCharacter(int id) throws SQLException {
        String sql = "DELETE FROM `Character` WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            AuditService.getInstance().logAction("deleteCharacter");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countCharacters() throws SQLException {
        String query = "SELECT COUNT(*) FROM `Character`";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1);
                AuditService.getInstance().logAction("countCharacters");
                return count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void showAllCharacters() throws SQLException {
        String query = "SELECT * FROM `Character`";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int weaponId = rs.getInt("weapon_id");
                Weapon weapon = null;

                if (weaponId > 0) {
                    weapon = (Weapon) ItemService.getInstance().readItem(weaponId);
                }

                Characters.Character character = new Characters.Character(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("health"),
                        rs.getInt("attack"),
                        rs.getInt("defense"),
                        weapon,
                        rs.getInt("level"),
                        rs.getInt("exp"),
                        rs.getInt("gold"),
                        rs.getInt("isBurned")
                );

                Service.getInfo(character);
            }

            AuditService.getInstance().logAction("showAllCharacters");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Character> getAllCharacters() throws SQLException {
        List<Characters.Character> characters = new ArrayList<>();
        String sql = "SELECT id FROM `Character`";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Characters.Character c = readCharacter(rs.getInt("id"));
                if (c != null) {
                    characters.add(c);
                }
            }
        }
        return characters;
    }



}
