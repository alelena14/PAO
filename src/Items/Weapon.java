package Items;

import Characters.Archer;
import Characters.Character;
import Characters.Mage;
import Characters.Warrior;
import Database.CharacterService;
import Database.InventoryService;
import Database.ItemService;

import java.sql.SQLException;

public class Weapon extends Item {
    private int damage;
    private final String type;
    private int level = 1;

    public Weapon(int id, String name, int damage, String type, int value, int level) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.level = level;
        this.value = value;
    }

    public Weapon(String name, int damage, String type, int value) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.value = value;
    }

    @Override
    public int use(Character character) throws SQLException {
        InventoryService inventoryService = InventoryService.getInstance();
        CharacterService characterService = CharacterService.getInstance();

        boolean canEquip = (character instanceof Mage && "Grimoire".equals(this.type)) ||
                (character instanceof Warrior && "Sword".equals(this.type)) ||
                (character instanceof Archer && "Bow".equals(this.type));

        if (!canEquip) {
            System.out.println("❌ Cannot equip this weapon type!");
            return -1;
        }

        Weapon currentWeapon = character.getWeapon();

        if (currentWeapon != null) {
            inventoryService.updateInventoryEntry(character.getId(), this.getId(), currentWeapon.getId());

            System.out.println("🔁 You already have a weapon equipped: " + currentWeapon.getName());
            System.out.println("It will be returned to your inventory.");

            character.setAttack(character.getAttack() - currentWeapon.getDamage());
        } else {
            inventoryService.deleteInventoryEntry(character.getId(), this.getId());
        }

        character.equipWeapon(this);
        characterService.updateCharacter(character);

        return 0;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }
}
